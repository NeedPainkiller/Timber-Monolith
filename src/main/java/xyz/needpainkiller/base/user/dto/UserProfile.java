package xyz.needpainkiller.base.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.needpainkiller.api.user.dto.UserCsv;
import xyz.needpainkiller.base.team.model.Team;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile<U extends User, R extends Role, T extends Team> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3025308819403131315L;
    private Long tenantPk;
    private Long userPk;
    private U user;
    private T team;
    private List<R> roleList;

    public UserProfile(U user, T team, List<R> roleList) {
        this.user = user;
        this.tenantPk = user.getTenantPk();
        this.userPk = user.getId();
        this.team = team;
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile<?, ?, ?> that = (UserProfile<?, ?, ?>) o;
        return Objects.equals(tenantPk, that.tenantPk) && Objects.equals(userPk, that.userPk) && Objects.equals(user, that.user) && Objects.equals(team, that.team) && Objects.equals(roleList, that.roleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantPk, userPk, user, team, roleList);
    }
}