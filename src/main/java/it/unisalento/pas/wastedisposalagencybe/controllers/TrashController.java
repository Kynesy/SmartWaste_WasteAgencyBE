package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.TrashNotification;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;
import it.unisalento.pas.wastedisposalagencybe.dto.TrashNotificationDTO;
import it.unisalento.pas.wastedisposalagencybe.dto.WasteStatisticsDTO;
import it.unisalento.pas.wastedisposalagencybe.services.ITrashNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/trash")
public class TrashController {

    private final ITrashNotificationService trashService;

    @Autowired
    public TrashController(ITrashNotificationService trashService) {
        this.trashService = trashService;
    }

    @GetMapping("/notifications/user/{userID}")
    public ResponseEntity<ArrayList<TrashNotificationDTO>> getTrashByUserId(@PathVariable String userID){
        ArrayList<TrashNotification> trashList = new ArrayList<>(trashService.getTrashNotificationByUserID(userID));
        ArrayList<TrashNotificationDTO> trashListDTO = new ArrayList<>();

        for(TrashNotification trash: trashList){
            TrashNotificationDTO trashDTO = fromTrashToTrashDTO(trash);
            trashListDTO.add(trashDTO);
        }

        return ResponseEntity.ok(trashListDTO);
    }

    @GetMapping("/statistics/user/{userID}")
    public ResponseEntity<WasteStatisticsDTO> getStatisticsByUserId(@PathVariable String userID){
        int actualYear = LocalDate.now().getYear();
        WasteStatistics statistics = trashService.getUserStatistics(userID, actualYear);

        WasteStatisticsDTO statisticsDTO = fromStatisticsToStatisticsDTO(statistics);

        return ResponseEntity.ok(statisticsDTO);
    }

    @GetMapping("/statistics/city")
    public ResponseEntity<WasteStatisticsDTO> getCityStatistics(){
        int actualYear = LocalDate.now().getYear();
        WasteStatistics statistics = trashService.getCityStatistics(actualYear);

        WasteStatisticsDTO statisticsDTO = fromStatisticsToStatisticsDTO(statistics);

        return ResponseEntity.ok(statisticsDTO);
    }

    private WasteStatisticsDTO fromStatisticsToStatisticsDTO(WasteStatistics statistics) {
        WasteStatisticsDTO statisticsDTO = new WasteStatisticsDTO();

        statisticsDTO.setTotalSortedWaste(statistics.getTotalSortedWaste());
        statisticsDTO.setTotalUnsortedWaste(statistics.getTotalUnsortedWaste());

        return statisticsDTO;
    }

    private TrashNotificationDTO fromTrashToTrashDTO(TrashNotification trash) {
        TrashNotificationDTO trashDTO = new TrashNotificationDTO();

        trashDTO.setId(trash.getId());
        trashDTO.setTimestamp(trash.getTimestamp());
        trashDTO.setBinId(trash.getBinId());
        trashDTO.setUserId(trashDTO.getUserId());
        trashDTO.setSortedWaste(trash.getSortedWaste());
        trashDTO.setUnsortedWaste(trash.getUnsortedWaste());

        return trashDTO;
    }
}
