package xyz.needpainkiller.api.file.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import xyz.needpainkiller.api.file.model.FileServiceType;
import xyz.needpainkiller.api.file.model.Files;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FileRepo extends JpaRepository<Files, Long> {

    List<Files> findAllBy();

    Optional<Files> findByUuid(String uuid);

    List<Files> findByUuidIn(List<String> uuid);

    Stream<Files> streamByUuidIn(List<String> uuid);

    List<Files> findByUseYnFalse();

    List<Files> findByFileService(String fileService);

    List<Files> findByFileServiceAndFileServiceId(String fileService, Long fileServiceId);

    List<Files> findByFileServiceAndFileServiceIdAndFileServiceType(String fileService, Long fileServiceId, FileServiceType fileServiceType);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE FILES SET FILE_EXISTS = 0", nativeQuery = true)
    int clearFileExists();
}