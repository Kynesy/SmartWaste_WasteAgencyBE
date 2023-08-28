package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WasteStatisticsDTO {
    String  userId;
    int year;
    int totalSortedWaste;
    int totalUnsortedWaste;
}
