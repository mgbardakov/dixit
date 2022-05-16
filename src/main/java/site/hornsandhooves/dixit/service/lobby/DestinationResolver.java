package site.hornsandhooves.dixit.service.lobby;

import org.springframework.messaging.Message;

public interface DestinationResolver {
    void addDestination(Message<?> message);
    String getDestination(Message<?> message);
    String removeDestination(Message<?> message);
}
