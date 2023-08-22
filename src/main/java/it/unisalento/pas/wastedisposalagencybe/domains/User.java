package it.unisalento.pas.wastedisposalagencybe.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id private String id;
    private String name;
    private String surname;
    private String email;
    private String bdate;
}
