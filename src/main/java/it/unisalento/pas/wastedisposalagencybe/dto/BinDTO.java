package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BinDTO extends Container{
    private int capacity;
    private int sortedWaste;
    private int unsortedWaste;
}
