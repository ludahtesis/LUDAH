package com.datalakefilter.persistence.repository;

import com.datalakefilter.persistence.entity.DatasetRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetRecordRepository extends JpaRepository<DatasetRecord, Long> {
}
