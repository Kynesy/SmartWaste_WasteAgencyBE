package it.unisalento.pas.wastedisposalagencybe.repositories;

import it.unisalento.pas.wastedisposalagencybe.domains.TrashNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrashNotificationRepository extends MongoRepository<TrashNotification, String> {
    List<TrashNotification> findByUserId(String userID);
}
