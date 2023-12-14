package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Waste {
    private String id;
    private String timestamp;
    private String userId;
    private String binId;
}
