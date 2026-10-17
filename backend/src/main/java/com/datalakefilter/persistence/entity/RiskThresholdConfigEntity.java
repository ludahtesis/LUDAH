package com.datalakefilter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "risk_threshold_config")
public class RiskThresholdConfigEntity {

    @Id
    private Short id;

    @Column(name = "clean_max", nullable = false)
    private double cleanMax;

    @Column(name = "frontier_max", nullable = false)
    private double frontierMax;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected RiskThresholdConfigEntity() {
    }

    public RiskThresholdConfigEntity(short id, double cleanMax, double frontierMax) {
        this.id = id;
        this.cleanMax = cleanMax;
        this.frontierMax = frontierMax;
    }

    public double getCleanMax() {
        return cleanMax;
    }

    public double getFrontierMax() {
        return frontierMax;
    }

    public void update(double cleanMax, double frontierMax) {
        this.cleanMax = cleanMax;
        this.frontierMax = frontierMax;
    }

    @PrePersist
    @PreUpdate
    void updateTimestamp() {
        updatedAt = Instant.now();
    }
}
