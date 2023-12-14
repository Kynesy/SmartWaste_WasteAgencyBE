package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.dto.BinDTO;
import it.unisalento.pas.wastedisposalagencybe.dto.ContainerFactory;
import it.unisalento.pas.wastedisposalagencybe.dto.IContainerFactory;
import it.unisalento.pas.wastedisposalagencybe.dto.WasteType;
import it.unisalento.pas.wastedisposalagencybe.services.IBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller che gestisce le operazioni relative ai contenitori (bin).
 */
@RestController
@RequestMapping("/api/bin")
@PreAuthorize("hasRole('OPERATOR')")
public class BinController {
    private final IBinService binService;

    @Autowired
    public BinController(IBinService binService) {
        this.binService = binService;
    }

    /**
     * Crea un nuovo contenitore (bin) con i dati forniti.
     *
     * @param binDTO Dati del contenitore (bin) da creare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se il bin è stato creato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @PostMapping("/create")
    public ResponseEntity<String> createBin(@RequestBody BinDTO binDTO){
        Bin bin = fromBinDTOtoBin(binDTO);

        int result = binService.createBin(bin);
        if(result == 0){
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Bin created successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Bin creation failed\"}");
        }
    }

    /**
     * Aggiorna un contenitore (bin) esistente con i dati forniti.
     *
     * @param binDTO Dati del contenitore (bin) da aggiornare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se il bin è stato aggiornato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateBin(@RequestBody BinDTO binDTO) {
        Bin bin = fromBinDTOtoBin(binDTO);

        int result = binService.updateBin(bin);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Bin updated successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Bin update failed\"}");
        }
    }

    /**
     * Elimina un contenitore (bin) con l'ID specificato.
     *
     * @param binID ID del contenitore (bin) da eliminare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se il bin è stato eliminato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @DeleteMapping("/delete/{binID}")
    public ResponseEntity<String> deleteBin(@PathVariable String binID) {
        int result = binService.deleteBinByID(binID);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Bin deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Bin deletion failed\"}");
        }
    }

    /**
     * Ottiene un contenitore (bin) con l'ID specificato.
     *
     * @param binID ID del contenitore (bin) da ottenere
     * @return ResponseEntity con stato HTTP OK e il corpo JSON del bin se trovato,
     * altrimenti NOT_FOUND
     */
    @GetMapping("/get/{binID}")
    public ResponseEntity<BinDTO> getBin(@PathVariable String binID) {
        Bin bin = binService.getBinbyID(binID);

        if (bin != null) {
            BinDTO binDTO = fromBinToBinDTO(bin);
            return ResponseEntity.ok(binDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Ottiene tutti i contenitori (bin) presenti nel sistema.
     *
     * @return ResponseEntity con stato HTTP OK e un elenco di bin in formato JSON se trovati,
     * altrimenti NOT_FOUND
     */
    @GetMapping("/get/all")
    public ResponseEntity<ArrayList<BinDTO>> getAllBins() {
        ArrayList<Bin> binList = binService.getAllBins();
        ArrayList<BinDTO> binDTOList = new ArrayList<>();
        if (binList != null) {
            for(Bin bin : binList){
                BinDTO binDTO = fromBinToBinDTO(bin);
                binDTOList.add(binDTO);
            }
            return ResponseEntity.ok(binDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Converte un oggetto Bin in un oggetto BinDTO.
     *
     * @param bin Oggetto Bin da convertire
     * @return Oggetto BinDTO convertito
     */
    private BinDTO fromBinToBinDTO(Bin bin) {
        IContainerFactory containerFactory = new ContainerFactory();
        BinDTO binDTO = (BinDTO) containerFactory.getContainerType(WasteType.SORTED_UNSORTED);

        binDTO.setId(bin.getId());
        binDTO.setCapacity(bin.getCapacity());
        binDTO.setLongitude(bin.getLongitude());
        binDTO.setLatitude(bin.getLatitude());
        binDTO.setSortedWaste(bin.getSortedWaste());
        binDTO.setUnsortedWaste(bin.getUnsortedWaste());
        binDTO.setAlertLevel(bin.getAlertLevel());

        return binDTO;
    }

    /**
     * Converte un oggetto BinDTO in un oggetto Bin.
     *
     * @param binDTO Oggetto BinDTO da convertire
     * @return Oggetto Bin convertito
     */
    private Bin fromBinDTOtoBin(BinDTO binDTO) {
        Bin bin = new Bin();

        bin.setId(binDTO.getId());
        bin.setLongitude(binDTO.getLongitude());
        bin.setLatitude(binDTO.getLatitude());
        bin.setCapacity(binDTO.getCapacity());
        bin.setSortedWaste(binDTO.getSortedWaste());
        bin.setUnsortedWaste(binDTO.getUnsortedWaste());
        bin.setAlertLevel(binDTO.getAlertLevel());

        return bin;
    }
}
