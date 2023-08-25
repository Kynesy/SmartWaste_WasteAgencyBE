package it.unisalento.pas.wastedisposalagencybe.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WasteStatistics {
    int totalSortedWaste;
    int totalUnsortedWaste;
}
