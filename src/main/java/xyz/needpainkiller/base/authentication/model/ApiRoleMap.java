package xyz.needpainkiller.base.authentication.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public abstract class ApiRoleMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 7338133863926562299L;

    private Long apiPk;
    private Long rolePk;

}

