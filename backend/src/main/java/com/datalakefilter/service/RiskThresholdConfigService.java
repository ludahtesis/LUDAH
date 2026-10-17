package com.datalakefilter.service;

import com.datalakefilter.dto.RiskThresholdConfigRequest;
import com.datalakefilter.dto.RiskThresholdConfigResponse;
import com.datalakefilter.persistence.entity.RiskThresholdConfigEntity;
import com.datalakefilter.persistence.repository.RiskThresholdConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class RiskThresholdConfigService {

    private static final short CONFIG_ID = 1;
    private final RiskThresholdConfigRepository repository;

    public RiskThresholdConfigService(RiskThresholdConfigRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public RiskThresholdConfigResponse getConfig() {
        RiskThresholdConfigEntity config = getStoredConfig();
        return buildResponse(config.getCleanMax(), config.getFrontierMax());
    }

    @Transactional
    public RiskThresholdConfigResponse updateConfig(RiskThresholdConfigRequest request) {
        if (request == null) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "La configuración de rangos es obligatoria."
            );
        }

        validate(request.cleanMax(), request.frontierMax());

        RiskThresholdConfigEntity config = getStoredConfig();
        config.update(request.cleanMax(), request.frontierMax());
        repository.save(config);

        return buildResponse(config.getCleanMax(), config.getFrontierMax());
    }

    @Transactional(readOnly = true)
    public double getCleanMax() {
        return getStoredConfig().getCleanMax();
    }

    @Transactional(readOnly = true)
    public double getFrontierMax() {
        return getStoredConfig().getFrontierMax();
    }

    private void validate(double cleanMax, double frontierMax) {
        if (cleanMax < 0 || cleanMax >= 100 || frontierMax <= 0 || frontierMax >= 100) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "Los umbrales deben cumplir: 0 <= saludable < frontera < 100."
            );
        }

        if (cleanMax >= frontierMax) {
            throw new ResponseStatusException(
                    BAD_REQUEST,
                    "El límite saludable debe ser menor que el límite frontera."
            );
        }
    }

    private RiskThresholdConfigEntity getStoredConfig() {
        return repository.findById(CONFIG_ID)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró la configuración inicial de umbrales."
                ));
    }

    private RiskThresholdConfigResponse buildResponse(double cleanMax, double frontierMax) {
        return new RiskThresholdConfigResponse(
                cleanMax,
                frontierMax,
                "0% - " + cleanMax + "%",
                (cleanMax + 0.01) + "% - " + frontierMax + "%",
                (frontierMax + 0.01) + "% - 100%"
        );
    }
}
