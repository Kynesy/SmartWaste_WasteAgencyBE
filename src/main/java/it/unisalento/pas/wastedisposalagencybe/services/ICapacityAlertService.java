package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.CapacityAlert;

import java.util.ArrayList;

public interface ICapacityAlertService {
    ArrayList<CapacityAlert> getAllAlerts();
}
