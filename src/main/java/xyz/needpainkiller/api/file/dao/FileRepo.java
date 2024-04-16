package xyz.needpainkiller.api.file.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import xyz.needpainkiller.api.file.model.FileEntity;
import xyz.needpainkiller.base.file.model.FileServiceType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FileRepo extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findAllBy();

    Optional<FileEntity> findByUuid(String uuid);

    List<FileEntity> findByUuidIn(List<String> uuid);

    Stream<FileEntity> streamByUuidIn(List<String> uuid);

    List<FileEntity> findByUseYnFalse();

    List<FileEntity> findByFileService(String fileService);

    List<FileEntity> findByFileServiceAndFileServiceId(String fileService, Long fileServiceId);

    List<FileEntity> findByFileServiceAndFileServiceIdAndFileServiceType(String fileService, Long fileServiceId, FileServiceType fileServiceType);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE FILES SET FILE_EXISTS = 0", nativeQuery = true)
    int clearFileExists();
}