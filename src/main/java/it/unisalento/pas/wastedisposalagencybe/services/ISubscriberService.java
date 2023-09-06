package it.unisalento.pas.wastedisposalagencybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

public interface ISubscriberService {
    void receiveTrash(byte[] payload) throws JsonProcessingException;
    void receiveAlert(byte[] payload) throws IOException;
}
