package xyz.needpainkiller.base.team.model;

import lombok.Getter;
import lombok.Setter;
import xyz.needpainkiller.base.tenant.model.TenantBase;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Team implements Serializable, TenantBase {
    @Serial
    private static final long serialVersionUID = -6908071168953003619L;
    private Long id;
    private Long tenantPk;
    private boolean useYn;
    private Boolean visibleYn;
    private String teamName;
    private Long createdBy;
    private Timestamp createdDate;
    private Long updatedBy;
    private Timestamp updatedDate;
    private Integer order;
    private TeamLevel teamLevel;
    private String teamPath;
    private Long parentTeamPk;
    private List<Team> childrenTeam = new ArrayList<>();

    public boolean hasParentTeam() {
        return parentTeamPk != null;
    }
}
