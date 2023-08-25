package it.unisalento.pas.wastedisposalagencybe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrashNotificationDTO {
    private String id;
    private String timestamp;
    private String userId;
    private String binId;
    private int sortedWaste;
    private int unsortedWaste;
}
