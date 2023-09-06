package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;

import java.util.ArrayList;

public interface ITrashService {
    ArrayList<Trash> getTrashNotificationByUserID(String userID);
    WasteStatistics getUserStatistics(String userID, int year);
    WasteStatistics getCityStatistics(int year);

}
