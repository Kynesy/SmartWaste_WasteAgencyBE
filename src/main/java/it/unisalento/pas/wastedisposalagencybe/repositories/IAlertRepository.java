package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAlertRepository extends MongoRepository<Alert, String> {
}
