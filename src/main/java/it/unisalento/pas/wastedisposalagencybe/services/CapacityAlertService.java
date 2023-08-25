package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.CapacityAlert;
import it.unisalento.pas.wastedisposalagencybe.repositories.ICapacityAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapacityAlertService implements ICapacityAlertService {
    private final ICapacityAlertRepository capacityAlertRepository;

    @Autowired
    public CapacityAlertService(ICapacityAlertRepository capacityAlertRepository) {
        this.capacityAlertRepository = capacityAlertRepository;
    }

    @Override
    public ArrayList<CapacityAlert> getAllAlerts() {
        List<CapacityAlert> capacityAlerts = capacityAlertRepository.findAll();
        return new ArrayList<>(capacityAlerts);
    }
}
