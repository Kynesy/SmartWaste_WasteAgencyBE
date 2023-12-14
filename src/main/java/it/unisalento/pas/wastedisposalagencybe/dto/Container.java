package it.unisalento.pas.wastedisposalagencybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Container {
    private String id;
    private float latitude;
    private float longitude;
    private int alertLevel;

}
