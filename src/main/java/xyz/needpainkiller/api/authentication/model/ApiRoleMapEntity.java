package xyz.needpainkiller.api.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.needpainkiller.base.authentication.model.ApiRoleMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity

@Table(name = "AUTHORITY_API_ROLE_MAP")
@IdClass(ApiRoleMapId.class)
public class ApiRoleMapEntity extends ApiRoleMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1988301575311263951L;
    @Serial
    @Id
    @Column(name = "API_PK", nullable = false, columnDefinition = "bigint default 0")
    private Long apiPk;
    @Id
    @Column(name = "ROLE_PK", nullable = false, columnDefinition = "bigint default 0")
    private Long rolePk;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiRoleMapEntity that = (ApiRoleMapEntity) o;
        return Objects.equals(apiPk, that.apiPk) && Objects.equals(rolePk, that.rolePk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiPk, rolePk);
    }
}

