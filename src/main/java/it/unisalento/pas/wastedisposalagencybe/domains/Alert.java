package it.unisalento.pas.wastedisposalagencybe.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("capacityAlerts")
@NoArgsConstructor
@Getter
@Setter
public class Alert {
    @Id private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("binId")
    private String binId;
    @JsonProperty("alertLevel")
    private int alertLevel;
}
