package xyz.needpainkiller.base.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public abstract class UserRoleMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 6462611611726190386L;

    private Long userPk;
    private Long rolePk;

}

