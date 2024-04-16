package xyz.needpainkiller.api.audit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.needpainkiller.api.audit.model.AuditLogEntity;

import java.util.List;


public interface AuditLogRepo extends JpaRepository<AuditLogEntity, Long>, JpaSpecificationExecutor<AuditLogEntity>{

    List<AuditLogEntity> findAll();

    long count();
}
