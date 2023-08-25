package it.unisalento.pas.wastedisposalagencybe.services;

import org.springframework.amqp.core.Message;

public interface ISubscriberService {
    void receiveTrashNotification(byte[] payload);
    void receiveCapacityAlert(byte[] payload);
}
