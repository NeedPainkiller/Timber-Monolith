package xyz.needpainkiller.api.audit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.needpainkiller.api.audit.model.AuditLog;

import java.util.List;


public interface AuditLogRepo extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog>{

    List<AuditLog> findAll();

    long count();
}
