package xyz.needpainkiller.base.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import xyz.needpainkiller.base.tenant.model.TenantBase;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public abstract class Role implements GrantedAuthority, Serializable, TenantBase {
    @Serial
    private static final long serialVersionUID = -8756037981122580688L;

    private Long id;
    private Long tenantPk;
    private boolean useYn;
    private String roleName;
    private String roleDescription;
    private boolean isSystemAdmin;
    private boolean isAdmin;
    private boolean isEditable;
    private Long createdBy;
    private Timestamp createdDate;
    private Long updatedBy;
    private Timestamp updatedDate;

    @Override
    public String getAuthority() {
        return roleName;
    }

    public boolean isAvailable() {
        return this.useYn;
    }

    public boolean isSystemAdmin() {
        return this.isSystemAdmin;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public boolean isEditable() {
        return this.isEditable;
    }
}
