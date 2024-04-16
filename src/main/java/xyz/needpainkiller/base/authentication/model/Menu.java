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
public abstract class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 2043050433915127872L;

    private Long id;
    private Long divisionPk;
    private boolean useYn;
    private boolean visibleYn;
    private Integer order;
    private String code;
    private String name;
    private String description;

    @Transient
    private List<Api> apiList = new ArrayList<>();

    public Menu setApiList(List<Api> apiList) {
        this.apiList = apiList;
        return this;
    }
    public boolean isAvailableMenu() {
        return this.useYn;
    }

    public boolean isVisibleMenu() {
        return this.useYn && this.visibleYn;
    }

}