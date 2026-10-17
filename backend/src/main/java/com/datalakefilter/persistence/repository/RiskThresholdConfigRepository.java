package com.datalakefilter.persistence.repository;

import com.datalakefilter.persistence.entity.RiskThresholdConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskThresholdConfigRepository extends JpaRepository<RiskThresholdConfigEntity, Short> {
}
