package xyz.needpainkiller.base.file.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
public class File implements Serializable {

    @Serial
    private static final long serialVersionUID = -723869256440595857L;

    protected Long id;
    protected String uuid;
    protected boolean useYn;

    protected boolean fileExists;
    protected String fileType;
    protected Long fileSize;

    protected String originalFileName;
    protected String changedFileName;

    protected Integer downloadCnt;

    protected String fileService;
    protected Long fileServiceId;
    protected FileServiceType fileServiceType;
    protected FileAuthorityType accessAuthority;

    protected Long createdBy;
    protected Timestamp createdDate;


}