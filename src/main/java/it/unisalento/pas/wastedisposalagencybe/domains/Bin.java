package it.unisalento.pas.wastedisposalagencybe.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("bins")
@NoArgsConstructor
@Getter
@Setter
public class Bin {
    @Id private String id;
    private float latitude;
    private float longitude;
    private int capacity;
    private int sortedWaste;
    private int unsortedWaste;
    private int alertLevel;
}
