package xyz.needpainkiller.base.workingday.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public abstract class Holiday implements Serializable {

    private static final long serialVersionUID = 2130988804897891742L;

    private Long id;
    private Long tenantPk;
    private String uuid;
    private String title;
    private Timestamp start;
    private Timestamp end;
    private Map<String, Serializable> data;

}
