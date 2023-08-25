package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.TrashNotification;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;
import it.unisalento.pas.wastedisposalagencybe.repositories.ITrashNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrashNotificationService implements ITrashNotificationService {
    private final ITrashNotificationRepository trashNotificationRepository;

    @Autowired
    public TrashNotificationService(ITrashNotificationRepository trashNotificationRepository) {
        this.trashNotificationRepository = trashNotificationRepository;
    }

    @Override
    public ArrayList<TrashNotification> getTrashNotificationByUserID(String userID) {
        List<TrashNotification> trashNotifications = trashNotificationRepository.findByUserId(userID);
        return new ArrayList<>(trashNotifications);
    }

    @Override
    public WasteStatistics getUserStatistics(String userID, int year) {
        List<TrashNotification> trashNotifications = trashNotificationRepository.findByUserId(userID);

        return sumWastes(trashNotifications);
    }

    @Override
    public WasteStatistics getCityStatistics(int year) {
        List<TrashNotification> trashNotifications = trashNotificationRepository.findAll();

        return sumWastes(trashNotifications);
    }

    private WasteStatistics sumWastes(List<TrashNotification> trashNotifications){
        WasteStatistics wasteStatistics = new WasteStatistics();
        wasteStatistics.setTotalSortedWaste(0);
        wasteStatistics.setTotalUnsortedWaste(0);
        for(TrashNotification trashNotification: trashNotifications){
            wasteStatistics.setTotalUnsortedWaste(wasteStatistics.getTotalUnsortedWaste() + trashNotification.getUnsortedWaste() );
            wasteStatistics.setTotalSortedWaste(wasteStatistics.getTotalSortedWaste() + trashNotification.getSortedWaste() );
        }

        return wasteStatistics;
    }
}
