package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrashRepository extends MongoRepository<Trash, String> {
    List<Trash> findByUserId(String userID);
}
