package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BinDTO {
    private String id;
    private float latitude;
    private float longitude;
    private int capacity;
    private int sortedWaste;
    private int unsortedWaste;
    private int alertLevel;
}
