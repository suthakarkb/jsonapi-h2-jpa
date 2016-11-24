package com.sc.csl.retail.crm.audit.dao;

import com.sc.csl.retail.crm.audit.entity.AuditTrailEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuditTrailEntityRepository  extends CrudRepository<AuditTrailEntity, Long> {
}
