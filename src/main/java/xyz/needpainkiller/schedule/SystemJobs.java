package xyz.needpainkiller.schedule;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xyz.needpainkiller.schedule.dto.JobRequest;
import xyz.needpainkiller.schedule.job.FileStorageCleanJob;
import xyz.needpainkiller.schedule.job.UpdateTeamPathJob;

import java.util.Arrays;
import java.util.List;

import static xyz.needpainkiller.schedule.model.ScheduleTriggerType.CRON;
@Slf4j
@Component
public class SystemJobs implements ApplicationListener<ContextRefreshedEvent> {

    private static final String SYSTEM_GROUP = "SYSTEM";
    private static final String CRON_EXPRESSION_EVERY_DAY_0000 = "0 0 0 * * ?";
    private static final String CRON_EXPRESSION_EVERY_DAY_0100 = "0 0 1 * * ?";
    private static final String CRON_EXPRESSION_EVERY_HOUR = "0 0 * * * ?";
    private static final String CRON_EXPRESSION_EACH_30_MIN = "0 0/30 * * * ?";
    private static final String CRON_EXPRESSION_EACH_5_MIN = "0 0/5 * * * ?";
    private static final String CRON_EXPRESSION_EACH_1_MIN = "0 0/1 * * * ?";
    private static final int PRIORITY_PRIME = 9999;
    private static final int PRIORITY_VERY_HIGH = 1000;
    private static final int PRIORITY_HIGH = 900;
    private static final int PRIORITY_MEDIUM = 500;
    private static final int PRIORITY_LOW = 100;
    private static final int PRIORITY_VERY_LOW = 1;
    private static final int PRIORITY_LESS = 0;
    @Autowired
    private QuartzScheduleService quartzScheduleService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        List<JobRequest> systemJobList = createSystemJobRequestList();
        try {
            quartzScheduleService.clearAllJobByGroup(SYSTEM_GROUP);
            for (JobRequest jobRequest : systemJobList) {
                quartzScheduleService.upsertJob(jobRequest);
            }
        } catch (SchedulerException e) {
            log.error("Failed to upsert system job : {}", e.getMessage());
        }
    }

    private static List<JobRequest> createSystemJobRequestList() {
        JobRequest updateTeamPathJob = createCronJob(UpdateTeamPathJob.class, "updateTeamPathJob", CRON_EXPRESSION_EACH_30_MIN, PRIORITY_LOW);
//        JobRequest updateTodayStatistics = createCronJob(RecordStatisticsJob.class, "updateTodayStatistics", CRON_EXPRESSION_EACH_5_MIN, PRIORITY_PRIME, jobDataMapTest());
        JobRequest cleanFileStorage = createCronJob(FileStorageCleanJob.class, "cleanFileStorage", CRON_EXPRESSION_EACH_5_MIN, PRIORITY_VERY_HIGH);

        return Arrays.asList(updateTeamPathJob, cleanFileStorage);
    }


    private static JobRequest createCronJob(Class<? extends Job> jobClass, String jobName, String cronExpression, int priority) {
        JobRequest jobRequest = new JobRequest(jobClass, CRON);
        jobRequest.setJobGroup(SYSTEM_GROUP);
        jobRequest.setJobName(jobName);
        jobRequest.setCronExpression(cronExpression);
        jobRequest.setPriority(priority);
        JobDataMap jobDataMap = new JobDataMap();
        jobRequest.setJobDataMap(jobDataMap);
        return jobRequest;
    }

    private static JobRequest createCronJob(Class<? extends Job> jobClass, String jobName, String cronExpression, int priority, JobDataMap jobDataMap) {
        JobRequest jobRequest = new JobRequest(jobClass, CRON);
        jobRequest.setJobGroup(SYSTEM_GROUP);
        jobRequest.setJobName(jobName);
        jobRequest.setCronExpression(cronExpression);
        jobRequest.setPriority(priority);
        jobRequest.setJobDataMap(jobDataMap);
        return jobRequest;
    }



    public  static  JobDataMap jobDataMapTest() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("KEY_UPDATE_ALL", true);
        return jobDataMap;
    }
}
