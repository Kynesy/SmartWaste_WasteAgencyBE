package it.unisalento.pas.wastedisposalagencybe.configurations;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import it.unisalento.pas.wastedisposalagencybe.services.ISubscriberService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione per la gestione dei messaggi RabbitMQ.
 */
@Configuration
public class RabbitConfig {

    private static final String TOPIC_EXCHANGE = "wasteDisposalAgencyTopic";
    private static final String TRASH_QUEUE = "trashNotificationTopic";
    private static final String ALERT_QUEUE = "capacityAlertTopic";
    private static final String CAPACITY_ALERT_ROUTING_KEY = "capacityAlertRK";
    private static final String TRASH_NOTIFICATION_ROUTING_KEY = "trashNotificationRK";

    /**
     * Crea una coda per i messaggi riguardanti la spazzatura.
     *
     * @return Oggetto Queue istanziato per la coda di notifiche sulla spazzatura
     */
    @Bean
    public Queue trashNotificationQueue() {
        return new Queue(TRASH_QUEUE);
    }

    /**
     * Crea una coda per i messaggi riguardanti gli alert di capacità.
     *
     * @return Oggetto Queue istanziato per la coda degli alert di capacità
     */
    @Bean
    public Queue capacityAlertQueue() {
        return new Queue(ALERT_QUEUE);
    }

    /**
     * Crea un topic exchange utilizzato per instradare i messaggi. Il
     * topic exchange è configurato come durable e autoDelete false.
     *
     * @return Un oggetto TopicExchange istanziato con il proprio nome
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, true);
    }

    /**
     * Configura il binding tra la coda di notifiche sulla spazzatura e il topic exchange.
     *
     * @param trashNotificationQueue Oggetto Queue per la coda di notifiche sulla spazzatura
     * @param topicExchange Oggetto TopicExchange
     * @return Oggetto Binding configurato per la coda di notifiche sulla spazzatura
     */
    @Bean
    public Binding bindingTrashNotification(Queue trashNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(trashNotificationQueue).to(topicExchange).with(TRASH_NOTIFICATION_ROUTING_KEY);
    }

    /**
     * Configura il binding tra la coda degli alert di capacità e il topic exchange.
     *
     * @param capacityAlertQueue Oggetto Queue per la coda degli alert di capacità
     * @param topicExchange Oggetto TopicExchange
     * @return Oggetto Binding configurato per la coda degli alert di capacità
     */
    @Bean
    public Binding bindingCapacityAlert(Queue capacityAlertQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(capacityAlertQueue).to(topicExchange).with(CAPACITY_ALERT_ROUTING_KEY);
    }

    /**
     * Dichiara il metodo da utilizzare per gestire i messaggi nella coda di notifiche sulla spazzatura.
     *
     * @param subscriberService Servizio contenente il metodo da assegnare
     * @return Oggetto MessageListenerAdapter configurato per la coda di notifiche sulla spazzatura
     */
    @Bean
    public MessageListenerAdapter trashListenerAdapter(ISubscriberService subscriberService) {
        return new MessageListenerAdapter(subscriberService, "receiveTrash");
    }

    /**
     * Dichiara il metodo da utilizzare per gestire i messaggi nella coda degli alert di capacità.
     *
     * @param subscriberService Servizio contenente il metodo da assegnare
     * @return Oggetto MessageListenerAdapter configurato per la coda degli alert di capacità
     */
    @Bean
    public MessageListenerAdapter alertListenerAdapter(ISubscriberService subscriberService) {
        return new MessageListenerAdapter(subscriberService, "receiveAlert");
    }

    /**
     * Configura il container per la gestione dei messaggi nella coda di notifiche sulla spazzatura.
     *
     * @param connectionFactory Factory di connessione RabbitMQ
     * @param trashListenerAdapter Oggetto MessageListenerAdapter per la coda di notifiche sulla spazzatura
     * @return Oggetto SimpleMessageListenerContainer configurato per la coda di notifiche sulla spazzatura
     */
    @Bean
    public SimpleMessageListenerContainer trashContainer(ConnectionFactory connectionFactory, MessageListenerAdapter trashListenerAdapter) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(TRASH_QUEUE);
        simpleMessageListenerContainer.setMessageListener(trashListenerAdapter);

        return simpleMessageListenerContainer;
    }

    /**
     * Configura il container per la gestione dei messaggi nella coda degli alert di capacità.
     *
     * @param connectionFactory Factory di connessione RabbitMQ
     * @param alertListenerAdapter Oggetto MessageListenerAdapter per la coda degli alert di capacità
     * @return Oggetto SimpleMessageListenerContainer configurato per la coda degli alert di capacità
     */
    @Bean
    public SimpleMessageListenerContainer alertContainer(ConnectionFactory connectionFactory, MessageListenerAdapter alertListenerAdapter) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(ALERT_QUEUE);
        simpleMessageListenerContainer.setMessageListener(alertListenerAdapter);
        return simpleMessageListenerContainer;
    }
}
