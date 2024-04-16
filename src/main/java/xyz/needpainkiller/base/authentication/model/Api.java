package xyz.needpainkiller.base.authentication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.needpainkiller.common.model.HttpMethod;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public abstract class Api implements Serializable {
    @Serial
    private static final long serialVersionUID = 3564521170754422902L;

    private Long id;
    private String code;
    private Long menuPk;
    private String service;
    private boolean useYn;
    private boolean visibleYn;
    private boolean primaryYn;
    private boolean publicYn;
    private boolean recordYn;
    private HttpMethod httpMethod;
    private String url;
    private String description;

    public boolean isAvailableApi() {
        return this.useYn;
    }

    public boolean isVisibleApi() {
        return this.useYn && this.visibleYn;
    }

    public boolean isPrimaryApi() {
        return this.useYn && this.visibleYn && this.primaryYn;
    }

    public boolean isPublicApi() {
        return this.useYn && this.publicYn;
    }

    public boolean isNonPublicApi() {
        return this.useYn && !this.publicYn;
    }
}