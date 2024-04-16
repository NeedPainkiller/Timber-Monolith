package xyz.needpainkiller.api.audit.dao;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import xyz.needpainkiller.api.audit.dto.AuditRequests;
import xyz.needpainkiller.api.audit.model.AuditLog;
import xyz.needpainkiller.common.model.HttpMethod;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuditLogSpecification {
    public static Specification<AuditLog> search(AuditRequests.SearchAuditLogRequest params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.equal(root.get("visibleYn"), true));

            Long tenantPk = params.getTenantPk();
            if (tenantPk != null) {
                predicateList.add(criteriaBuilder.equal(root.get("tenantPk"), tenantPk));
            }
            Timestamp startDate = params.getStartDate();
            Timestamp endDate = params.getEndDate();
            if (startDate != null && endDate != null) {
                predicateList.add(criteriaBuilder.between(root.get("createdDate"), startDate, endDate));
            }
            Integer httpStatus = params.getHttpStatus();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.equal(root.get("httpStatus"), httpStatus));
            }
            HttpMethod httpMethod = params.getHttpMethod();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.equal(root.get("httpMethod"), httpMethod));
            }
            String requestUri = params.getRequestUri();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.equal(root.get("requestUri"), requestUri));
            }
            Long requestIpNum = params.getRequestIpNum();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.equal(root.get("requestIp"), requestIpNum));
            }
            String agentBrowser = params.getAgentBrowser();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("agentBrowser"), agentBrowser + "%"));
            }
            String userId = params.getUserId();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("userId"), userId + "%"));
            }
            String userEmail = params.getUserEmail();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("userEmail"), userEmail + "%"));
            }
            String userName = params.getUserName();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("userName"), userName + "%"));
            }
            String teamName = params.getTeamName();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("teamName"), teamName + "%"));
            }
            String menuName = params.getMenuName();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("menuName"), menuName + "%"));
            }
            String apiName = params.getApiName();
            if (httpStatus != null) {
                predicateList.add(criteriaBuilder.like(root.get("apiName"), apiName + "%"));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}