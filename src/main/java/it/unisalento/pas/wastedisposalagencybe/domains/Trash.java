package it.unisalento.pas.wastedisposalagencybe.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("trashNotifications")
@NoArgsConstructor
@Getter
@Setter
public class Trash {
    @Id
    private String id;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("binId")
    private String binId;
    @JsonProperty("sortedWaste")
    private int sortedWaste;
    @JsonProperty("unsortedWaste")
    private int unsortedWaste;
}
