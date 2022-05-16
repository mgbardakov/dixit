package site.hornsandhooves.dixit.service.lobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpDestinationResolver implements DestinationResolver {

    private final Map<TopicId, String> subs = new ConcurrentHashMap<>();


    @Override
    public void addDestination(Message<?> message) {
        var topicId = getTopicIdFromMessage(message);
        subs.put(topicId, (String) message.getHeaders().get("simpDestination"));
    }

    @Override
    public String getDestination(Message<?> message) {
        var rsl = "";
        if(Objects.equals(message.getHeaders().get("simpMessageType"), SimpMessageType.SUBSCRIBE)) {
            rsl = (String) message.getHeaders().get("simpDestination");
        } else if(Objects.equals(message.getHeaders().get("simpMessageType"), SimpMessageType.UNSUBSCRIBE)) {
            var topicId = getTopicIdFromMessage(message);
            rsl = subs.get(topicId);
        }
        return rsl;
    }

    @Override
    public String removeDestination(Message<?> message) {
        return subs.remove(getTopicIdFromMessage(message));
    }

    private TopicId getTopicIdFromMessage(Message<?> message) {
        var userId = ((Principal) Objects.requireNonNull(message.getHeaders().get("simpUser"))).getName();
        return new TopicId((String) message.getHeaders().get("simpSubscriptionId"),
                userId);
    }
    @Data
    @AllArgsConstructor
    private static class TopicId {
        private String userId;
        private String subId;
    }
}
