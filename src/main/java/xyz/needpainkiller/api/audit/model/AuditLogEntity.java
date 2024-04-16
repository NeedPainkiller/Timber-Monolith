package xyz.needpainkiller.api.audit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import xyz.needpainkiller.common.model.HttpMethod;
import xyz.needpainkiller.lib.jpa.BooleanConverter;
import xyz.needpainkiller.lib.jpa.InetConverter;
import xyz.needpainkiller.lib.jpa.JsonToMapConverter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "AUDIT_LOG")
public class AuditLogEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2716245406503579988L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false, columnDefinition = "bigint")
    @Getter
    private Long id;
    @Column(name = "TENANT_PK")
    @Getter
    private Long tenantPk;
    @Column(name = "HTTP_STATUS", columnDefinition = "default 0")
    @Getter
    private Integer httpStatus;
    @Convert(converter = HttpMethod.Converter.class)
    @Column(name = "HTTP_METHOD", columnDefinition = "tinyint default 0")
    @Getter
    private HttpMethod httpMethod;
    @Column(name = "AGENT_OS", columnDefinition = "nvarchar(64)")
    @Getter
    private String agentOs;
    @Column(name = "AGENT_OS_VERSION", columnDefinition = "nvarchar(16)")
    @Getter
    private String agentOsVersion;
    @Column(name = "AGENT_BROWSER", columnDefinition = "nvarchar(64)")
    @Getter
    private String agentBrowser;
    @Column(name = "AGENT_BROWSER_VERSION", columnDefinition = "nvarchar(16)")
    @Getter
    private String agentBrowserVersion;
    @Column(name = "AGENT_DEVICE", columnDefinition = "nvarchar(128)")
    @Getter
    private String agentDevice;
    @Convert(converter = InetConverter.class)
    @Column(name = "REQUEST_IP", columnDefinition = "numeric(20,0)")
    @Getter
    private String requestIp;
    @Column(name = "REQUEST_URI", columnDefinition = "nvarchar(256)")
    @Getter
    private String requestUri;
    @Column(name = "REQUEST_CONTENT_TYPE", columnDefinition = "nvarchar(32)")
    @Getter
    private String requestContentType;
    @Column(name = "REQUEST_PAYLOAD", columnDefinition = "nvarchar")
    private String requestPayLoad;
    @Column(name = "RESPONSE_CONTENT_TYPE", columnDefinition = "nvarchar(32)")
    @Getter
    private String responseContentType;
    @Column(name = "RESPONSE_PAYLOAD", columnDefinition = "nvarchar")
    private String responsePayLoad;
    @Column(name = "CREATED_DATE", columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Getter
    private Timestamp createdDate;
    @Column(name = "USER_PK", columnDefinition = "bigint")
    @Getter
    private Long userPk;
    @Column(name = "USER_ID", columnDefinition = "nvarchar(128)")
    @Getter
    private String userId;
    @Column(name = "USER_EMAIL", columnDefinition = "nvarchar(128)")
    @Getter
    private String userEmail;
    @Column(name = "USER_NAME", columnDefinition = "nvarchar(64)")
    @Getter
    private String userName;
    @Column(name = "TEAM_PK", columnDefinition = "bigint")
    @Getter
    private Long teamPk;
    @Column(name = "TEAM_NAME", columnDefinition = "nvarchar(128)")
    @Getter
    private String teamName;
    @Column(name = "MENU_UID", columnDefinition = "bigint")
    @Getter
    private Long menuUid;
    @Column(name = "MENU_NAME", columnDefinition = "nvarchar(128)")
    @Getter
    private String menuName;
    @Column(name = "API_UID", columnDefinition = "bigint")
    @Getter
    private Long apiUid;
    @Column(name = "API_NAME", columnDefinition = "nvarchar(256)")
    @Getter
    private String apiName;
    @Column(name = "ERROR_DATA", columnDefinition = "nvarchar")
    @Getter
    @Convert(converter = JsonToMapConverter.class)
    private Map<String, Serializable> errorData;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "VISIBLE_YN", columnDefinition = "tinyint default 0")
    @Getter
    private boolean visibleYn;

}
