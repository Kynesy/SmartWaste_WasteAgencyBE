package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
}
