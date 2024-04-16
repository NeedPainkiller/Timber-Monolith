package xyz.needpainkiller.api.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import xyz.needpainkiller.base.authentication.model.Api;
import xyz.needpainkiller.common.model.HttpMethod;
import xyz.needpainkiller.lib.jpa.BooleanConverter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity

@Slf4j
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "AUTHORITY_API", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"URL", "HTTP_METHOD"})
})
public class ApiEntity extends Api implements Serializable {
    @Serial
    private static final long serialVersionUID = 6256338336130750570L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;
    @Column(name = "CODE", unique = true, nullable = false, columnDefinition = "nvarchar(256)")
    private String code;
    @Column(name = "MENU_PK", nullable = false, columnDefinition = "bigint")
    private Long menuPk;
    @Column(name = "SERVICE", nullable = false, columnDefinition = "nvarchar(32)")
    private String service;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean useYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "VISIBLE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean visibleYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "PRIMARY_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean primaryYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "PUBLIC_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean publicYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "RECORD_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean recordYn;

    @Convert(converter = HttpMethod.Converter.class)
    @Column(name = "HTTP_METHOD", nullable = false, columnDefinition = "int unsigned default 0")
    private HttpMethod httpMethod;
    @Column(name = "URL", nullable = false, columnDefinition = "nvarchar(256)")
    private String url;
    @Column(name = "DESCRIPTION", nullable = true, columnDefinition = "nvarchar(256) default null")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiEntity apiEntity = (ApiEntity) o;
        return useYn == apiEntity.useYn && visibleYn == apiEntity.visibleYn && primaryYn == apiEntity.primaryYn && publicYn == apiEntity.publicYn && recordYn == apiEntity.recordYn && Objects.equals(id, apiEntity.id) && Objects.equals(code, apiEntity.code) && Objects.equals(menuPk, apiEntity.menuPk) && Objects.equals(service, apiEntity.service) && httpMethod == apiEntity.httpMethod && Objects.equals(url, apiEntity.url) && Objects.equals(description, apiEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, menuPk, service, useYn, visibleYn, primaryYn, publicYn, recordYn, httpMethod, url, description);
    }

    @Override
    public String toString() {
        return "ApiEntity {" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", menuPk=" + menuPk +
                ", service='" + service + '\'' +
                ", useYn=" + useYn +
                ", visibleYn=" + visibleYn +
                ", primaryYn=" + primaryYn +
                ", publicYn=" + publicYn +
                ", recordYn=" + recordYn +
                ", httpMethod=" + httpMethod +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean isAvailableApi() {
        return this.useYn;
    }
    @Override
    public boolean isVisibleApi() {
        return this.useYn && this.visibleYn;
    }
    @Override
    public boolean isPrimaryApi() {
        return this.useYn && this.visibleYn && this.primaryYn;
    }
    @Override
    public boolean isPublicApi() {
        return this.useYn && this.publicYn;
    }
    @Override
    public boolean isNonPublicApi() {
        return this.useYn && !this.publicYn;
    }
}