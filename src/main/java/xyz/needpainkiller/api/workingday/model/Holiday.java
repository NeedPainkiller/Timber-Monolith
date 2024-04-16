package xyz.needpainkiller.api.workingday.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.needpainkiller.lib.jpa.JsonToMapConverter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(value = {"hibernate_lazy_initializer", "handler"}, ignoreUnknown = true)
@Entity

@Table(name = "HOLIDAY")
public class Holiday implements Serializable {
    private static final long serialVersionUID = 3566472127899675681L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "TENANT_PK", nullable = false, columnDefinition = "bigint default 0")
    private Long tenantPk;
    @Column(name = "UUID", nullable = false, columnDefinition = "char(36)")
    private String uuid;
    @Column(name = "TITLE", nullable = false, columnDefinition = "nvarchar(255)")
    private String title;
    @Column(name = "DATE_START", nullable = false, columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    private Timestamp start;
    @Column(name = "DATE_END", nullable = false, columnDefinition = "datetime2(0) default CURRENT_TIMESTAMP")
    private Timestamp end;
    @Convert(converter = JsonToMapConverter.class)
    @Column(name = "EXTRA_DATA", nullable = true, columnDefinition = "longtext default NULL")
    @Getter
    private Map<String, Serializable> data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return Objects.equals(id, holiday.id) && Objects.equals(tenantPk, holiday.tenantPk) && Objects.equals(uuid, holiday.uuid) && Objects.equals(title, holiday.title) && Objects.equals(start, holiday.start) && Objects.equals(end, holiday.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantPk, uuid, title, start, end);
    }

}
