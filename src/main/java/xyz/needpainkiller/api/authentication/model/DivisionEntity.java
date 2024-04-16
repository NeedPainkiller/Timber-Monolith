package xyz.needpainkiller.api.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import xyz.needpainkiller.base.authentication.model.Division;
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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "AUTHORITY_DIVISION")
public class DivisionEntity extends Division implements Serializable {
    @Serial
    private static final long serialVersionUID = -2552891563217528753L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean useYn;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "VISIBLE_YN", nullable = false, columnDefinition = "tinyint unsigned default 0")
    private boolean visibleYn;
    @Column(name = "DIVISION_ORDER", nullable = false, columnDefinition = "int unsigned default 9999")
    private Integer order;
    @Column(name = "NAME", nullable = false, columnDefinition = "nvarchar(256)")
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, columnDefinition = "nvarchar(1024)")
    private String description;

//    @Where(clause = "USE_YN=1")
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "DIVISION_PK", insertable = false, updatable = false)
//    private List<Menu> childMenu = new ArrayList<>();

    @Transient
    private List<? extends Menu> menuEntityList = new ArrayList<>();


    public DivisionEntity setMenuList(List<Menu> menuEntityList) {
        this.menuEntityList = menuEntityList;
        return this;
    }
    public boolean isAvailableDivision() {
        return this.useYn;
    }

    public boolean isVisibleDivision() {
        return this.useYn && this.visibleYn;
    }
}