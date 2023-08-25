package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.TrashNotification;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;

import java.util.ArrayList;

public interface ITrashNotificationService {
    ArrayList<TrashNotification> getTrashNotificationByUserID(String userID);
    WasteStatistics getUserStatistics(String userID, int year);
    WasteStatistics getCityStatistics(int year);

}
