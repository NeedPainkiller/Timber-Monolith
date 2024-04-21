package xyz.needpainkiller.api.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.needpainkiller.common.model.HttpMethod;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Setter
@Getter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditLogMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -1384789622264912736L;

    private Long id;
    private boolean visibleYn;
    private Long tenantPk;
    private Integer httpStatus;
    private HttpMethod httpMethod;
    private String agentOs;
    private String agentOsVersion;
    private String agentBrowser;
    private String agentBrowserVersion;
    private String agentDevice;
    private String requestIp;
    private String requestUri;
    private String requestContentType;
    private String requestPayLoad;
    private String responseContentType;
    private String responsePayLoad;
    private Timestamp createdDate;
    private Long userPk;
    private String userId;
    private String userEmail;
    private String userName;
    private Long teamPk;
    private String teamName;
    private Long menuUid;
    private String menuName;
    private Long apiUid;
    private String apiName;
    private Map<String, Serializable> errorData;

}
