package xyz.needpainkiller.schedule.job;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import xyz.needpainkiller.api.file.model.FileEntity;
import xyz.needpainkiller.base.file.FileService;
import xyz.needpainkiller.base.file.model.File;
import xyz.needpainkiller.helper.TimeHelper;
import xyz.needpainkiller.lib.storage.LocalStorageService;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
public final class FileStorageCleanJob extends QuartzJobBean implements InterruptableJob {


    private volatile boolean isJobInterrupted = false;

    private volatile Thread currThread;

    private static final int SQL_WHERE_IN_LIMIT = 2000;
    @Autowired
    private FileService<FileEntity> fileService;
    @Autowired
    private LocalStorageService<FileEntity> localStorageService;


    @Value("${file.delete-expired-file-limit}")
    private Integer fileExpiredLimitDay;

    private static final String fileServiceNameJobCapture = "JOB.CAPTURE";


    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isJobInterrupted = true;
        if (currThread != null) {
            log.debug("interrupting - {}", currThread.getName());
            currThread.interrupt();
        }
    }

    /**
     * 1.저장소 내 존재하는 파일만 DB 에 exist 처리
     * 2.불필요&&존재하는 파일 삭제
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        if (isJobInterrupted) {
            log.debug("isJobInterrupted - {}", currThread.getName());
            return;
        }
        JobKey jobKey = context.getJobDetail().getKey();
        currThread = Thread.currentThread();
        try {

// 1. 저장소 내 존재하는 파일만 DB 에 exist 처리
            List<String> allExistFileUuid = localStorageService.findAllExistFileUuid();
            if (allExistFileUuid == null || allExistFileUuid.isEmpty()) {
                return;
            }
            int allExistFileUuidSize = allExistFileUuid.size();
            if (allExistFileUuidSize > SQL_WHERE_IN_LIMIT) {
                List<List<String>> subList = Lists.partition(allExistFileUuid, SQL_WHERE_IN_LIMIT);
                for (List<String> list : subList) {
                    fileService.upsertFileExists(list);
                }
            } else {
                fileService.upsertFileExists(allExistFileUuid);
            }

            List<FileEntity> removedFileInfoList;
// 2. (불필요 && 스토리지 내 존재)하는 파일 삭제
            List<FileEntity> notUsedFileInfoList = fileService.selectFileNotUsed();
            removedFileInfoList = localStorageService.remove(notUsedFileInfoList);
            fileService.deleteServiceFile(removedFileInfoList);
            try (Stream<FileEntity> fileInfoStream = removedFileInfoList.stream()) {
                fileInfoStream.parallel().forEach(fileInfo -> log.info("NotUsedFile : {}", fileInfo.getChangedFileName()));
            }

// 3. RPA JOB 스크린샷 정기 삭제
            Timestamp now = TimeHelper.now();
            long nowMillisecond = now.getTime();
            long fileExpiredLimitMillisecond = fileExpiredLimitDay * 24 * 60 * 60 * 1000;

            Predicate<FileEntity> predicateExpiredFile = file -> {
                Timestamp createdDate = file.getCreatedDate();
                long createdDateMillisecond = createdDate.getTime();
                return nowMillisecond - createdDateMillisecond >= fileExpiredLimitMillisecond;
            };

            List<FileEntity> jobCaptureFileList = fileService.selectServiceFileList(fileServiceNameJobCapture)
                    .stream()
                    .filter(predicateExpiredFile).toList();
            fileService.deleteServiceFile(jobCaptureFileList);

            removedFileInfoList = localStorageService.remove(jobCaptureFileList);
            fileService.deleteServiceFile(removedFileInfoList);

            List<Long> jobIdList = jobCaptureFileList.stream().map(File::getFileServiceId).toList();
            try (Stream<FileEntity> fileInfoStream = removedFileInfoList.stream()) {
                fileInfoStream.parallel().forEach(fileInfo -> log.info("ExpiredFile : {}", fileInfo.getChangedFileName()));
            }

        } catch (RuntimeException e) {
            log.error("deleteFileInfo Failed : {} | {}", e.getClass().getName(), e.getMessage());
        }

    }
}
