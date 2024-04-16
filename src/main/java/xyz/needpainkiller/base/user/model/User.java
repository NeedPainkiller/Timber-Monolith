package xyz.needpainkiller.base.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import xyz.needpainkiller.base.tenant.model.TenantBase;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@Setter
public abstract class User implements TenantBase, Serializable {
    @Serial
    private static final long serialVersionUID = 3184452991602290422L;

    @Getter
    private Long id;

    @Getter
    private Long tenantPk;

    @Getter
    private boolean useYn;

    @Getter
    private String userId;

    @Getter
    private String userEmail;

    @Getter
    private String userName;

    private String userPwd;

    @Getter
    private UserStatusType userStatus;

    @Getter
    private Long teamPk;

    @Getter
    private Long createdBy;

    @Getter
    private Timestamp createdDate;

    @Getter
    private Long updatedBy;

    @Getter
    private Timestamp updatedDate;

    @Getter
    private Integer loginFailedCnt;

    @Getter
    private Timestamp lastLoginDate;

    @Getter
    private Map<String, Serializable> data;

    @JsonIgnore
    public boolean isLoginEnabled() {
        return this.useYn && this.userStatus.isLoginable();
    }

    @JsonIgnore
    public boolean isAvailable() {
        return this.useYn;
    }

    @Transient
    @JsonIgnore
    // password is not exposed to the client
    public String getUserPwd() {
        return null;
    }

    @Transient
    @JsonIgnore
    // password for SecurityUser
    public String pwd() {
        return this.userPwd;
    }

}
