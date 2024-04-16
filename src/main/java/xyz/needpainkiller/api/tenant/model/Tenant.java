package xyz.needpainkiller.api.tenant.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import xyz.needpainkiller.lib.jpa.BooleanConverter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(value = {"hibernate_lazy_initializer", "handler"}, ignoreUnknown = true)
// https://stackoverflow.com/questions/67353793/what-does-jsonignorepropertieshibernatelazyinitializer-handler-do
@Entity

@DynamicInsert
@Table(name = "TENANT")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tenant implements Serializable, TenantBase {
    @Serial
    private static final long serialVersionUID = 8790536518756589875L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    @ColumnDefault(value = "0")
    @Getter
    private boolean useYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "DEFAULT_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    @ColumnDefault(value = "0")
    @Getter
    private boolean defaultYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "VISIBLE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    @ColumnDefault("0")
    @Getter
    private boolean visibleYn;
    @Column(name = "TITLE", nullable = false, columnDefinition = "nvarchar(256)")
    @Getter
    private String title;
    @Column(name = "LABEL", nullable = false, columnDefinition = "nvarchar(256)")
    @Getter
    private String label;
    @Column(name = "URL_PATH", nullable = false, columnDefinition = "nvarchar(256)")
    @Getter
    private String url;
    @Column(name = "CREATED_BY", nullable = false, columnDefinition = "bigint default 0")
    @ColumnDefault("0")
    @Getter
    private Long createdBy;
    @Column(name = "CREATED_DATE", nullable = false, columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Getter
    private Timestamp createdDate;
    @Column(name = "UPDATED_BY", nullable = false, columnDefinition = "bigint default 0")
    @ColumnDefault("0")
    @Getter
    private Long updatedBy;
    @Column(name = "UPDATED_DATE", nullable = false, columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @Getter
    private Timestamp updatedDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant that = (Tenant) o;
        return defaultYn == that.defaultYn && useYn == that.useYn && visibleYn == that.visibleYn && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(label, that.label) && Objects.equals(url, that.url) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, defaultYn, useYn, visibleYn, title, label, url, createdBy, createdDate, updatedBy, updatedDate);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", defaultYn=" + defaultYn +
                ", useYn=" + useYn +
                ", visibleYn=" + visibleYn +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", updatedBy=" + updatedBy +
                ", updatedDate=" + updatedDate +
                '}';
    }

    public Long getTenantPk() {
        return id;
    }

    public void setTenantPk(Long tenantPk) {
        // do nothing
        // autoincrement id
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
