package it.unisalento.pas.wastedisposalagencybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.Message;

import java.io.IOException;

public interface ISubscriberService {
    void receiveTrashNotification(byte[] payload) throws JsonProcessingException;
    void receiveCapacityAlert(byte[] payload) throws IOException;
}
