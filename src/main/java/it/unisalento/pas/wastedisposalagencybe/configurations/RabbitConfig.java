package it.unisalento.pas.wastedisposalagencybe.configurations;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import it.unisalento.pas.wastedisposalagencybe.services.ISubscriberService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String TOPIC_EXCHANGE = "wasteDisposalAgencyTopic";
    private static final String TRASH_TOPIC = "trashNotificationTopic";
    private static final String ALERT_TOPIC = "capacityAlertTopic";
    private static final String CAPACITY_ALERT_ROUTING_KEY = "capacityAlertRK"; // Replace with your desired routing key
    private static final String TRASH_NOTIFICATION_ROUTING_KEY = "trashNotificationRK"; // Replace with your desired routing key

    @Bean
    public Queue trashNotificationQueue() {
        return new Queue(TRASH_TOPIC);
    }

    @Bean
    public Queue capacityAlertQueue() {
        return new Queue(ALERT_TOPIC);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, true);
    }

    @Bean
    public Binding bindingTrashNotification(Queue trashNotificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(trashNotificationQueue).to(topicExchange).with(TRASH_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCapacityAlert(Queue capacityAlertQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(capacityAlertQueue).to(topicExchange).with(CAPACITY_ALERT_ROUTING_KEY);
    }

    @Bean
    public MessageListenerAdapter trashAdapter(ISubscriberService subscriberService) {
        return new MessageListenerAdapter(subscriberService, "receiveTrashNotification");
    }

    @Bean
    public MessageListenerAdapter alertAdapter(ISubscriberService subscriberService) {
        return new MessageListenerAdapter(subscriberService, "receiveCapacityAlert");
    }

    @Bean
    public SimpleMessageListenerContainer trashContainer(ConnectionFactory connectionFactory, MessageListenerAdapter trashAdapter) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(TRASH_TOPIC);
        simpleMessageListenerContainer.setMessageListener(trashAdapter);

        return simpleMessageListenerContainer;
    }

    @Bean
    public SimpleMessageListenerContainer alertContainer(ConnectionFactory connectionFactory, MessageListenerAdapter alertAdapter) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(ALERT_TOPIC);
        simpleMessageListenerContainer.setMessageListener(alertAdapter);
        return simpleMessageListenerContainer;
    }
}
