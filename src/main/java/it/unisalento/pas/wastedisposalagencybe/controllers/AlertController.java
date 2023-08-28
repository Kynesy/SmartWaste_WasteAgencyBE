package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import it.unisalento.pas.wastedisposalagencybe.dto.CapacityAlertDTO;
import it.unisalento.pas.wastedisposalagencybe.services.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/alert")
public class AlertController {
    private final IAlertService alertService;

    @Autowired
    public AlertController(IAlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ArrayList<CapacityAlertDTO>> getAllCapacityAlerts(){
        ArrayList<Alert> alertList = alertService.getAllAlerts();
        ArrayList<CapacityAlertDTO> alertListDTO = new ArrayList<>();

        for(Alert alert: alertList){
            CapacityAlertDTO alertDTO = fromAlertToAlertDTO(alert);
            alertListDTO.add(alertDTO);
        }

        return ResponseEntity.ok(alertListDTO);
    }

    private CapacityAlertDTO fromAlertToAlertDTO(Alert alert) {
        CapacityAlertDTO alertDTO = new CapacityAlertDTO();

        alertDTO.setId(alert.getId());
        alertDTO.setTimestamp(alert.getTimestamp());
        alertDTO.setBinId(alert.getBinId());
        alertDTO.setAlertLevel(alert.getAlertLevel());

        return alertDTO;
    }

}


