package xyz.needpainkiller.base.tenant.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
public abstract class Tenant implements Serializable, TenantBase {
    @Serial
    private static final long serialVersionUID = 2026900397048257268L;

    private Long id;
    private boolean defaultYn;
    private boolean useYn;
    private boolean visibleYn;
    private String title;
    private String label;
    private String url;
    private Long createdBy;
    private Timestamp createdDate;
    private Long updatedBy;
    private Timestamp updatedDate;

    @Override
    public Long getTenantPk() {
        return id;
    }

    @Override
    public void setTenantPk(Long tenantPk) {
        // do nothing
        // update tenantPk of Tenant is not allowed
    }

    public boolean isActive() {
        return useYn;
    }

    public boolean isPublic() {
        return useYn && visibleYn;
    }


    public boolean isDefault() {
        return useYn && defaultYn;
    }

}
