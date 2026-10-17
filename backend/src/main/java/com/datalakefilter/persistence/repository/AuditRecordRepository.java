package com.datalakefilter.persistence.repository;

import com.datalakefilter.persistence.entity.AuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRecordRepository extends JpaRepository<AuditRecord, Long> {
}
