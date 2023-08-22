package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IBinRepository extends MongoRepository<Bin, String> {
}
