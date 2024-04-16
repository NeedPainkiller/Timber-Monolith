package xyz.needpainkiller.api.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRoleMapId implements Serializable {
    @Serial
    private static final long serialVersionUID = -2694894058079960435L;
    @Serial
    private Long apiPk;
    private Long rolePk;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiRoleMapId that = (ApiRoleMapId) o;
        return Objects.equals(apiPk, that.apiPk) && Objects.equals(rolePk, that.rolePk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiPk, rolePk);
    }
}

