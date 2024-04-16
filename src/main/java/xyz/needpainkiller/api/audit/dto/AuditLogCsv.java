package xyz.needpainkiller.api.audit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import xyz.needpainkiller.api.audit.model.AuditLogEntity;
import xyz.needpainkiller.helper.TimeHelper;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditLogCsv implements Serializable {


    @Serial
    private static final long serialVersionUID = 6947003162513272107L;

    @JsonProperty(value = "ID", index = 1)
    private long id;
    @JsonProperty(value = "HTTP 상태 코드", index = 20)
    private int httpStatus;
    @JsonProperty(value = "HTTP 메서드", index = 21)
    private String httpMethod;

    @JsonProperty(value = "요청장비 OS", index = 30)
    private String agentOs;
    @JsonProperty(value = "요청장비 OS VERSION", index = 31)
    private String agentOsVersion;
    @JsonProperty(value = "요청장비 브라우저", index = 32)
    private String agentBrowser;
    @JsonProperty(value = "요청장비 브라우저 VERSION", index = 33)
    private String agentBrowserVersion;
    @JsonProperty(value = "요청장비 장비", index = 34)
    private String agentDevice;

    @JsonProperty(value = "원본 IP", index = 40)
    private String requestIp;
    @JsonProperty(value = "접근 URI", index = 41)
    private String requestUri;
    @JsonProperty(value = "요청 CONTENT TYPE", index = 42)
    private String requestContentType;

    @JsonProperty(value = "응답 CONTENT TYPE", index = 44)
    private String responseContentType;
    @JsonProperty(value = "발생 일시", index = 46)
    @CreationTimestamp
    private String createdDate;

    @JsonProperty(value = "계정 PK", index = 50)
    private Long userPk;
    @JsonProperty(value = "계정 ID", index = 51)
    private String userId;
    @JsonProperty(value = "계정 이메일", index = 52)
    private String userEmail;
    @JsonProperty(value = "계정 이름", index = 53)
    private String userName;

    @JsonProperty(value = "팀 PK", index = 55)
    private Long teamPk;
    @JsonProperty(value = "팀 이름", index = 56)
    private String teamName;

    @JsonProperty(value = "메뉴 UID", index = 60)
    private Long menuUid;
    @JsonProperty(value = "메뉴 이름", index = 61)
    private String menuName;

    @JsonProperty(value = "API UID", index = 90)
    private Long apiUid;
    @JsonProperty(value = "API 이름", index = 92)
    private String apiName;

    public AuditLogCsv(AuditLogEntity log) {
        this.id = log.getId();
        this.httpStatus = log.getHttpStatus();
        this.httpMethod = log.getHttpMethod().name();
        this.agentOs = log.getAgentOs();
        this.agentOsVersion = log.getAgentOsVersion();
        this.agentBrowser = log.getAgentBrowser();
        this.agentBrowserVersion = log.getAgentBrowserVersion();
        this.agentDevice = log.getAgentDevice();
        this.requestIp = log.getRequestIp();
        this.requestUri = log.getRequestUri();
        this.requestContentType = log.getRequestContentType();
        this.responseContentType = log.getResponseContentType();
        this.createdDate = TimeHelper.fromTimestampToString(log.getCreatedDate());
        this.userPk = log.getUserPk();
        this.userId = log.getUserId();
        this.userEmail = log.getUserEmail();
        this.userName = log.getUserName();
        this.teamPk = log.getTeamPk();
        this.teamName = log.getTeamName();
        this.menuUid = log.getMenuUid();
        this.menuName = log.getMenuName();
        this.apiUid = log.getApiUid();
        this.apiName = log.getApiName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLogCsv that = (AuditLogCsv) o;
        return id == that.id && httpStatus == that.httpStatus && Objects.equals(httpMethod, that.httpMethod) && Objects.equals(agentOs, that.agentOs) && Objects.equals(agentOsVersion, that.agentOsVersion) && Objects.equals(agentBrowser, that.agentBrowser) && Objects.equals(agentBrowserVersion, that.agentBrowserVersion) && Objects.equals(agentDevice, that.agentDevice) && Objects.equals(requestIp, that.requestIp) && Objects.equals(requestUri, that.requestUri) && Objects.equals(requestContentType, that.requestContentType) && Objects.equals(responseContentType, that.responseContentType) && Objects.equals(createdDate, that.createdDate) && Objects.equals(userPk, that.userPk) && Objects.equals(userId, that.userId) && Objects.equals(userEmail, that.userEmail) && Objects.equals(userName, that.userName) && Objects.equals(teamPk, that.teamPk) && Objects.equals(teamName, that.teamName) && Objects.equals(menuUid, that.menuUid) && Objects.equals(menuName, that.menuName) && Objects.equals(apiUid, that.apiUid) && Objects.equals(apiName, that.apiName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, httpStatus, httpMethod, agentOs, agentOsVersion, agentBrowser, agentBrowserVersion, agentDevice, requestIp, requestUri, requestContentType, responseContentType, createdDate, userPk, userId, userEmail, userName, teamPk, teamName, menuUid, menuName, apiUid, apiName);
    }
}
