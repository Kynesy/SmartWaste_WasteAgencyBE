package it.unisalento.pas.wastedisposalagencybe.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WasteStatistics {
    String userId;
    int year;
    int totalSortedWaste;
    int totalUnsortedWaste;
}
