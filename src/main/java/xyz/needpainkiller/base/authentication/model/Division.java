package xyz.needpainkiller.base.authentication.model;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public abstract class Division implements Serializable {
    @Serial
    private static final long serialVersionUID = 8067823135117509114L;

    private Long id;
    private boolean useYn;
    private boolean visibleYn;
    private Integer order;
    private String name;
    private String description;

    @Transient
    private List<Menu> menuList = new ArrayList<>();


    public Division setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
        return this;
    }

    public boolean isAvailableDivision() {
        return this.useYn;
    }

    public boolean isVisibleDivision() {
        return this.useYn && this.visibleYn;
    }

}