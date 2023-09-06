package it.unisalento.pas.wastedisposalagencybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import it.unisalento.pas.wastedisposalagencybe.repositories.IBinRepository;
import it.unisalento.pas.wastedisposalagencybe.repositories.IAlertRepository;
import it.unisalento.pas.wastedisposalagencybe.repositories.ITrashRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Questa classe gestisce la ricezione dei messaggi dai topic RabbitMQ.
 */
@Service
public class SubscriberService implements ISubscriberService {
    private final IAlertRepository capacityAlertRepository;
    private final ITrashRepository trashNotificationRepository;
    private final IBinRepository binRepository;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization/deserialization

    @Autowired
    public SubscriberService(
            IAlertRepository capacityAlertRepository,
            ITrashRepository trashNotificationRepository,
            IBinRepository binRepository, ObjectMapper objectMapper
    ) {
        this.capacityAlertRepository = capacityAlertRepository;
        this.trashNotificationRepository = trashNotificationRepository;
        this.binRepository = binRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Gestisce la ricezione di notifiche di rifiuti dai topic RabbitMQ.
     *
     * @param payload Il messaggio di notifica di rifiuti in formato byte
     * @throws JsonProcessingException Eccezione generata in caso di errori di parsing JSON
     */
    @Override
    @RabbitListener(queues = "trashNotificationTopic")
    public void receiveTrash(byte[] payload) throws JsonProcessingException {
        String payloadString = new String(payload);
        System.out.println("Received trash payload: " + payloadString);

        Trash trash = objectMapper.readValue(payloadString, Trash.class);

        addWaste(trash.getBinId(), trash.getSortedWaste(), trash.getUnsortedWaste());
        trashNotificationRepository.save(trash);
    }

    /**
     * Gestisce la ricezione di alert di capacità dai topic RabbitMQ.
     *
     * @param payload Il messaggio di alert di capacità in formato byte
     * @throws IOException Eccezione generata in caso di errori di parsing JSON
     */
    @Override
    @RabbitListener(queues = "capacityAlertTopic")
    public void receiveAlert(byte[] payload) throws IOException {
        String payloadString = new String(payload);
        System.out.println("Received alert payload: " + payloadString);

        Alert alert = objectMapper.readValue(payloadString, Alert.class);
        setAlertToBin(alert.getBinId(), alert.getAlertLevel());

        capacityAlertRepository.save(alert);
    }

    /**
     * Aggiunge quantità di rifiuti a un cestino specifico.
     *
     * @param binID         L'ID del cestino a cui aggiungere i rifiuti
     * @param sortedWaste   La quantità di rifiuti separati da aggiungere
     * @param unsortedWaste La quantità di rifiuti non separati da aggiungere
     */
    private void addWaste(String binID, int sortedWaste, int unsortedWaste) {
        Bin bin = binRepository.findById(binID).orElse(null);
        if (bin != null) {
            bin.setSortedWaste(bin.getSortedWaste() + sortedWaste);
            bin.setUnsortedWaste(bin.getUnsortedWaste() + unsortedWaste);
            binRepository.save(bin);
            System.out.println("SUB_SERVICE: Trash added to Bin");
        }else{
            System.out.println("SUB_SERVICE: Error bin not found");
        }
    }

    /**
     * Imposta un alert di capacità su un cestino specifico.
     *
     * @param binID      L'ID del cestino su cui impostare l'alert
     * @param alertLevel Il livello di alert da impostare
     */
    private void setAlertToBin(String binID, int alertLevel) {
        Bin bin = binRepository.findById(binID).orElse(null);
        if (bin != null) {
            bin.setAlertLevel(alertLevel);
            if (alertLevel == 0) { // Svuota il cestino
                bin.setUnsortedWaste(0);
                bin.setSortedWaste(0);
            }
            binRepository.save(bin);
            System.out.println("SUB_SERVICE: Alert set to bin");
        }else{
            System.out.println("SUB_SERVICE: Error bin not found");
        }
    }
}
