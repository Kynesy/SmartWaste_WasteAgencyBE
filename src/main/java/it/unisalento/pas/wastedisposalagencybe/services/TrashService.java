package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;
import it.unisalento.pas.wastedisposalagencybe.repositories.ITrashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce le operazioni relative ai rifiuti e alle statistiche.
 */
@Service
public class TrashService implements ITrashService {
    private final ITrashRepository trashRepository;

    @Autowired
    public TrashService(ITrashRepository trashRepository) {
        this.trashRepository = trashRepository;
    }

    /**
     * Ottiene la lista delle notifiche di rifiuti per un determinato utente.
     *
     * @param userID L'ID dell'utente per cui si vogliono ottenere le notifiche dei rifiuti
     * @return Una lista di oggetti Trash rappresentanti le notifiche dei rifiuti
     */
    @Override
    public ArrayList<Trash> getTrashNotificationByUserID(String userID) {
        List<Trash> trashList = trashRepository.findByUserId(userID);
        return new ArrayList<>(trashList);
    }

    /**
     * Calcola le statistiche sui rifiuti di un utente in un determinato anno.
     *
     * @param userID L'ID dell'utente per cui si vogliono calcolare le statistiche
     * @param year   L'anno per cui si vogliono calcolare le statistiche
     * @return Un oggetto WasteStatistics rappresentante le statistiche dei rifiuti dell'utente
     */
    @Override
    public WasteStatistics getUserStatistics(String userID, int year) {
        List<Trash> trashNotifications = trashRepository.findByUserId(userID);
        WasteStatistics statistics = sumWastes(trashNotifications);
        statistics.setUserId(userID);
        statistics.setYear(year);
        return statistics;
    }

    /**
     * Calcola le statistiche sulla città per un determinato anno.
     *
     * @param year L'anno per cui si vogliono calcolare le statistiche
     * @return Un oggetto WasteStatistics rappresentante le statistiche dei rifiuti della città
     */
    @Override
    public WasteStatistics getCityStatistics(int year) {
        List<Trash> trashNotifications = trashRepository.findAll();
        WasteStatistics statistics = sumWastes(trashNotifications);
        statistics.setYear(year);

        return statistics;
    }

    /**
     * Somma le quantità di rifiuti per calcolare le statistiche totali.
     *
     * @param trashList Una lista di notifiche di rifiuti
     * @return Un oggetto WasteStatistics rappresentante le statistiche dei rifiuti sommati
     */
    private WasteStatistics sumWastes(List<Trash> trashList) {
        WasteStatistics wasteStatistics = new WasteStatistics();
        wasteStatistics.setTotalSortedWaste(0);
        wasteStatistics.setTotalUnsortedWaste(0);
        for (Trash trash : trashList) {
            wasteStatistics.setTotalUnsortedWaste(wasteStatistics.getTotalUnsortedWaste() + trash.getUnsortedWaste());
            wasteStatistics.setTotalSortedWaste(wasteStatistics.getTotalSortedWaste() + trash.getSortedWaste());
        }

        return wasteStatistics;
    }
}
