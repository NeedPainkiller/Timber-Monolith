package xyz.needpainkiller.base.audit.model;

import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;
import xyz.needpainkiller.common.model.HttpMethod;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;


@Setter
public abstract class AuditLog implements Serializable {
    @Serial
    private static final long serialVersionUID = -2542065141371832977L;
    @Getter
    private Long id;
    @Getter
    private Long tenantPk;
    @Getter
    private Integer httpStatus;
    @Convert(converter = HttpMethod.Converter.class)
    @Getter
    private HttpMethod httpMethod;
    @Getter
    private String agentOs;
    @Getter
    private String agentOsVersion;
    @Getter
    private String agentBrowser;
    @Getter
    private String agentBrowserVersion;
    @Getter
    private String agentDevice;
    @Getter
    private String requestIp;
    @Getter
    private String requestUri;
    @Getter
    private String requestContentType;
    private String requestPayLoad;
    @Getter
    private String responseContentType;
    private String responsePayLoad;
    @Getter
    private Timestamp createdDate;
    @Getter
    private Long userPk;
    @Getter
    private String userId;
    @Getter
    private String userEmail;
    @Getter
    private String userName;
    @Getter
    private Long teamPk;
    @Getter
    private String teamName;
    @Getter
    private Long menuUid;
    @Getter
    private String menuName;
    @Getter
    private Long apiUid;
    @Getter
    private String apiName;
    @Getter
    private Map<String, Serializable> errorData;
    @Getter
    private boolean visibleYn;
}
