package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.dto.BinDTO;
import it.unisalento.pas.wastedisposalagencybe.services.IBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/bin")
public class BinController {
    private final IBinService binService;

    @Autowired
    public BinController(IBinService binService) {
        this.binService = binService;
    }

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

    @DeleteMapping("/delete/{binID}")
    public ResponseEntity<String> deleteBin(@PathVariable String binID) {
        int result = binService.deleteBinByID(binID);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Bin deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Bin deletion failed\"}");
        }
    }

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

    @GetMapping("/getAll")
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

    private BinDTO fromBinToBinDTO(Bin bin) {
        BinDTO binDTO = new BinDTO();

        binDTO.setId(bin.getId());
        binDTO.setCapacity(bin.getCapacity());
        binDTO.setLongitude(bin.getLongitude());
        binDTO.setLatitude(bin.getLatitude());
        binDTO.setSortedWaste(bin.getSortedWaste());
        binDTO.setUnsortedWaste(bin.getUnsortedWaste());
        binDTO.setAlertLevel(bin.getAlertLevel());

        return binDTO;
    }


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
