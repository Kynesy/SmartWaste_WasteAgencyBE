package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import it.unisalento.pas.wastedisposalagencybe.repositories.IAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce le operazioni relative alle notifiche di allarme.
 */
@Service
public class AlertService implements IAlertService {

    private final IAlertRepository capacityAlertRepository;

    @Autowired
    public AlertService(IAlertRepository capacityAlertRepository) {
        this.capacityAlertRepository = capacityAlertRepository;
    }

    /**
     * Restituisce una lista di tutte le notifiche di allarme.
     *
     * @return Una lista di notifiche di allarme o null in caso di errore
     */
    @Override
    public ArrayList<Alert> getAllAlerts() {
        List<Alert> alerts = capacityAlertRepository.findAll();
        return new ArrayList<>(alerts);
    }
}
