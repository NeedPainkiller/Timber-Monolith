package xyz.needpainkiller.api.file.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import xyz.needpainkiller.base.file.model.File;
import xyz.needpainkiller.base.file.model.FileAuthorityType;
import xyz.needpainkiller.base.file.model.FileServiceType;
import xyz.needpainkiller.lib.jpa.BooleanConverter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity

@DynamicUpdate
@Table(name = "FILES")
public class FileEntity extends File implements Serializable {

    @Serial
    private static final long serialVersionUID = -4364063142587875170L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID")
    protected Long id;
    @Column(name = "UUID")
    protected String uuid;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "USE_YN")
    protected boolean useYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "FILE_EXISTS")
    protected boolean fileExists;
    @Column(name = "FILE_TYPE")
    protected String fileType;
    @Column(name = "FILE_SIZE")
    protected Long fileSize;
    @Column(name = "FILE_NM_ORIGINAL")
    protected String originalFileName;
    @Column(name = "FILE_NM_CHANGE")
    protected String changedFileName;
    @Column(name = "DOWNLOAD_CNT")
    protected Integer downloadCnt;

    @Column(name = "FILE_SERVICE")
    protected String fileService;
    @Column(name = "FILE_SERVICE_ID")
    protected Long fileServiceId;
    @Convert(converter = FileServiceType.Converter.class)
    @Column(name = "FILE_SERVICE_TYPE")
    protected FileServiceType fileServiceType;
    @Convert(converter = FileAuthorityType.Converter.class)
    @Column(name = "ACCESS_AUTHORITY")
    protected FileAuthorityType accessAuthority;

    @Column(name = "CREATED_BY")
    protected Long createdBy;
    @Column(name = "CREATED_DATE", columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    @CreationTimestamp
    protected Timestamp createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity that = (FileEntity) o;
        return useYn == that.useYn && fileExists == that.fileExists && Objects.equals(id, that.id) && Objects.equals(uuid, that.uuid) && Objects.equals(fileType, that.fileType) && Objects.equals(fileSize, that.fileSize) && Objects.equals(originalFileName, that.originalFileName) && Objects.equals(changedFileName, that.changedFileName) && Objects.equals(downloadCnt, that.downloadCnt) && Objects.equals(fileService, that.fileService) && Objects.equals(fileServiceId, that.fileServiceId) && fileServiceType == that.fileServiceType && accessAuthority == that.accessAuthority && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, useYn, fileExists, fileType, fileSize, originalFileName, changedFileName, downloadCnt, fileService, fileServiceId, fileServiceType, accessAuthority, createdBy, createdDate);
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", useYn=" + useYn +
                ", fileExists=" + fileExists +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                ", originalFileName='" + originalFileName + '\'' +
                ", changedFileName='" + changedFileName + '\'' +
                ", downloadCnt=" + downloadCnt +
                ", fileService='" + fileService + '\'' +
                ", fileServiceId=" + fileServiceId +
                ", fileServiceType=" + fileServiceType +
                ", accessAuthority=" + accessAuthority +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                '}';
    }
}