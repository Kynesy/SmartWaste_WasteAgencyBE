package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AlertDTO {
    private String id;
    private String timestamp;
    private String binId;
    private int alertLevel;
}
