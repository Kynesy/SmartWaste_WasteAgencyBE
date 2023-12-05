package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import it.unisalento.pas.wastedisposalagencybe.dto.AlertDTO;
import it.unisalento.pas.wastedisposalagencybe.services.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Controller che gestisce le operazioni relative alle notifiche di allarme (alert).
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController {
    private final IAlertService alertService;

    @Autowired
    public AlertController(IAlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Ottiene tutte le notifiche di allarme di capacità.
     *
     * @return ResponseEntity con lo stato HTTP OK e una lista di notifiche di allarme in formato JSON
     */
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<ArrayList<AlertDTO>> getAllCapacityAlerts() {
        ArrayList<Alert> alertList = alertService.getAllAlerts();
        ArrayList<AlertDTO> alertListDTO = new ArrayList<>();

        for (Alert alert : alertList) {
            AlertDTO alertDTO = fromAlertToAlertDTO(alert);
            alertListDTO.add(alertDTO);
        }

        return ResponseEntity.ok(alertListDTO);
    }

    /**
     * Converte un oggetto Alert in un oggetto AlertDTO.
     *
     * @param alert Oggetto Alert da convertire
     * @return Oggetto AlertDTO convertito
     */
    private AlertDTO fromAlertToAlertDTO(Alert alert) {
        AlertDTO alertDTO = new AlertDTO();

        alertDTO.setId(alert.getId());
        alertDTO.setTimestamp(alert.getTimestamp());
        alertDTO.setBinId(alert.getBinId());
        alertDTO.setAlertLevel(alert.getAlertLevel());

        return alertDTO;
    }
}