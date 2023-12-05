package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;
import it.unisalento.pas.wastedisposalagencybe.dto.TrashDTO;
import it.unisalento.pas.wastedisposalagencybe.dto.WasteStatisticsDTO;
import it.unisalento.pas.wastedisposalagencybe.services.ITrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller che gestisce le operazioni relative ai rifiuti (trash) e alle statistiche dei rifiuti.
 */
@RestController
@RequestMapping("/api/trash")
public class TrashController {

    private final ITrashService trashService;

    @Autowired
    public TrashController(ITrashService trashService) {
        this.trashService = trashService;
    }

    /**
     * Ottiene le notifiche dei rifiuti per un utente specifico.
     *
     * @param userID ID dell'utente per cui ottenere le notifiche dei rifiuti
     * @return ResponseEntity con lo stato HTTP OK e una lista di notifiche dei rifiuti in formato JSON
     */
    @GetMapping("/notifications/user/{userID}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArrayList<TrashDTO>> getTrashByUserId(@PathVariable String userID) {
        ArrayList<Trash> trashList = new ArrayList<>(trashService.getTrashNotificationByUserID(userID));
        ArrayList<TrashDTO> trashListDTO = new ArrayList<>();

        for (Trash trash : trashList) {
            TrashDTO trashDTO = fromTrashToTrashDTO(trash);
            trashListDTO.add(trashDTO);
        }

        return ResponseEntity.ok(trashListDTO);
    }

    /**
     * Ottiene le statistiche dei rifiuti per un utente specifico in un dato anno.
     *
     * @param userID ID dell'utente per cui ottenere le statistiche
     * @param year   Anno delle statistiche
     * @return ResponseEntity con lo stato HTTP OK e le statistiche dei rifiuti in formato JSON
     */
    @GetMapping("/statistics/user/{userID}/{year}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WasteStatisticsDTO> getStatisticsByUserId(@PathVariable String userID, @PathVariable int year) {
        WasteStatistics statistics = trashService.getUserStatistics(userID, year);
        WasteStatisticsDTO statisticsDTO = fromStatisticsToStatisticsDTO(statistics);

        return ResponseEntity.ok(statisticsDTO);
    }

    /**
     * Ottiene le statistiche dei rifiuti per una lista di utenti in un dato anno.
     *
     * @param idList Lista di ID degli utenti per cui ottenere le statistiche
     * @param year   Anno delle statistiche
     * @return ResponseEntity con lo stato HTTP OK e una lista di statistiche dei rifiuti in formato JSON
     */
    @PostMapping("/statistics/user/all/{year}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<WasteStatisticsDTO>> getStatisticsByUserId(@RequestBody ArrayList<String> idList, @PathVariable int year) {
        ArrayList<WasteStatisticsDTO> statListDTO = new ArrayList<>();
        for (String id : idList) {
            WasteStatistics statistics = trashService.getUserStatistics(id, year);
            WasteStatisticsDTO statisticsDTO = fromStatisticsToStatisticsDTO(statistics);
            statListDTO.add(statisticsDTO);
        }

        return ResponseEntity.ok(statListDTO);
    }

    /**
     * Ottiene le statistiche dei rifiuti per l'intera città in un dato anno.
     *
     * @param year Anno delle statistiche
     * @return ResponseEntity con lo stato HTTP OK e le statistiche dei rifiuti della città in formato JSON
     */
    @GetMapping("/statistics/city/{year}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WasteStatisticsDTO> getCityStatistics(@PathVariable int year) {
        WasteStatistics statistics = trashService.getCityStatistics(year);
        WasteStatisticsDTO statisticsDTO = fromStatisticsToStatisticsDTO(statistics);

        return ResponseEntity.ok(statisticsDTO);
    }

    /**
     * Converte un oggetto WasteStatistics in un oggetto WasteStatisticsDTO.
     *
     * @param statistics Oggetto WasteStatistics da convertire
     * @return Oggetto WasteStatisticsDTO convertito
     */
    private WasteStatisticsDTO fromStatisticsToStatisticsDTO(WasteStatistics statistics) {
        WasteStatisticsDTO statisticsDTO = new WasteStatisticsDTO();

        statisticsDTO.setUserId(statistics.getUserId());
        statisticsDTO.setYear(statistics.getYear());
        statisticsDTO.setTotalSortedWaste(statistics.getTotalSortedWaste());
        statisticsDTO.setTotalUnsortedWaste(statistics.getTotalUnsortedWaste());

        return statisticsDTO;
    }

    /**
     * Converte un oggetto Trash in un oggetto TrashDTO.
     *
     * @param trash Oggetto Trash da convertire
     * @return Oggetto TrashDTO convertito
     */
    private TrashDTO fromTrashToTrashDTO(Trash trash) {
        TrashDTO trashDTO = new TrashDTO();

        trashDTO.setId(trash.getId());
        trashDTO.setTimestamp(trash.getTimestamp());
        trashDTO.setBinId(trash.getBinId());
        trashDTO.setUserId(trashDTO.getUserId());
        trashDTO.setSortedWaste(trash.getSortedWaste());
        trashDTO.setUnsortedWaste(trash.getUnsortedWaste());

        return trashDTO;
    }
}
