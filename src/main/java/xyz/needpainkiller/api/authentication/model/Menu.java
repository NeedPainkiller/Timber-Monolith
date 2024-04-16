package xyz.needpainkiller.api.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
public class Menu implements Serializable {

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
    private List<Api> apiEntityList = new ArrayList<>();

    public Menu setApiList(List<Api> apiEntityList) {
        this.apiEntityList = apiEntityList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return useYn == menu.useYn && visibleYn == menu.visibleYn && Objects.equals(id, menu.id) && Objects.equals(divisionPk, menu.divisionPk) && Objects.equals(order, menu.order) && Objects.equals(code, menu.code) && Objects.equals(name, menu.name) && Objects.equals(description, menu.description) && Objects.equals(apiEntityList, menu.apiEntityList);
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