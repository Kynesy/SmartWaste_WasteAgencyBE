package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.CapacityAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICapacityAlertRepository extends MongoRepository<CapacityAlert, String> {
}
