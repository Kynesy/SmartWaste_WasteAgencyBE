package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrashDTO {
    private String id;
    private String timestamp;
    private String userId;
    private String binId;
    private int sortedWaste;
    private int unsortedWaste;
}
