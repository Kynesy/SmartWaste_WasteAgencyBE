package it.unisalento.pas.wastedisposalagencybe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.domains.CapacityAlert;
import it.unisalento.pas.wastedisposalagencybe.domains.TrashNotification;
import it.unisalento.pas.wastedisposalagencybe.repositories.IBinRepository;
import it.unisalento.pas.wastedisposalagencybe.repositories.ICapacityAlertRepository;
import it.unisalento.pas.wastedisposalagencybe.repositories.ITrashNotificationRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService implements ISubscriberService{
    private final ICapacityAlertRepository capacityAlertRepository;
    private final ITrashNotificationRepository trashNotificationRepository;
    private final IBinRepository binRepository;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization/deserialization

    @Autowired
    public SubscriberService(
            ICapacityAlertRepository capacityAlertRepository,
            ITrashNotificationRepository trashNotificationRepository,
            IBinRepository binRepository, ObjectMapper objectMapper
    ) {
        this.capacityAlertRepository = capacityAlertRepository;
        this.trashNotificationRepository = trashNotificationRepository;
        this.binRepository = binRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @RabbitListener(queues="trashNotificationTopic")
    public void receiveTrashNotification(byte[] payload) {
        String payloadString = new String(payload);
        System.out.println("Received payload: " + payloadString);
        System.out.println("Payload type: " + payload.getClass());

        // Add your deserialization and processing logic here
    }


    @Override
    @RabbitListener(queues="capacityAlertTopic")
    public void receiveCapacityAlert(byte[] payload) {
        String payloadString = new String(payload);
        System.out.println("Received payload: " + payloadString);
        System.out.println("Payload type: " + payload.getClass());
    }

    private void addWaste(String binID, int sortedWaste, int unsortedWaste) {
        try {
            Bin bin = binRepository.findById(binID).orElseThrow(Exception::new);
            if (bin != null) {
                bin.setSortedWaste(bin.getSortedWaste() + sortedWaste);
                bin.setUnsortedWaste(bin.getUnsortedWaste() + unsortedWaste);
                binRepository.save(bin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAlertToBin(String binID, int alertLevel){
        try {
            Bin bin = binRepository.findById(binID).orElseThrow(Exception::new);
            if (bin != null) {
                bin.setAlertLevel(alertLevel);
                if(alertLevel == 0){ //svuota il cestino
                    bin.setUnsortedWaste(0);
                    bin.setSortedWaste(0);
                }
                binRepository.save(bin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
