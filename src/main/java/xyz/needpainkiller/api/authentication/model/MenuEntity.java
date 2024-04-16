package xyz.needpainkiller.api.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import xyz.needpainkiller.base.authentication.model.Api;
import xyz.needpainkiller.base.authentication.model.Menu;
import xyz.needpainkiller.lib.jpa.BooleanConverter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString
@Table(name = "AUTHORITY_MENU")
public class MenuEntity extends Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = -4706827836999226903L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;
    @Column(name = "DIVISION_PK", nullable = false, columnDefinition = "bigint")
    private Long divisionPk;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean useYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "VISIBLE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean visibleYn;
    @Column(name = "MENU_ORDER", nullable = false, columnDefinition = "int unsigned default 9999")
    private Integer order;
    @Column(name = "CODE", nullable = false, columnDefinition = "nvarchar(256)")
    private String code;
    @Column(name = "NAME", nullable = false, columnDefinition = "nvarchar(256)")
    private String name;
    @Column(name = "DESCRIPTION", nullable = false, columnDefinition = "nvarchar(1024)")
    private String description;

    @Transient
    private List<? extends Api> apiEntityList = new ArrayList<>();

    @Override
    public MenuEntity setApiList(List<Api> apiEntityList) {
        this.apiEntityList = apiEntityList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity menuEntity = (MenuEntity) o;
        return useYn == menuEntity.useYn && visibleYn == menuEntity.visibleYn && Objects.equals(id, menuEntity.id) && Objects.equals(divisionPk, menuEntity.divisionPk) && Objects.equals(order, menuEntity.order) && Objects.equals(code, menuEntity.code) && Objects.equals(name, menuEntity.name) && Objects.equals(description, menuEntity.description) && Objects.equals(apiEntityList, menuEntity.apiEntityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, divisionPk, useYn, visibleYn, order, code, name, description, apiEntityList);
    }

    public boolean isAvailableMenu() {
        return this.useYn;
    }

    public boolean isVisibleMenu() {
        return this.useYn && this.visibleYn;
    }

}