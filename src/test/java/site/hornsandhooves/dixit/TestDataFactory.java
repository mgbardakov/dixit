package site.hornsandhooves.dixit;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import site.hornsandhooves.dixit.model.lobby.User;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class TestDataFactory {

    private static final String user1Id = "f12184fd-2071-48b6-861c-659bef1a4385";
    private static final String subscriptionId = "sub-0";

    public static User getUser() {
        return User.builder().id(UUID.fromString(user1Id)).name("Biba").host(true).build();
    }

    public static Message<?> getSubscribeMessage() {
        Map<String, Object> headers = Map.of("simpMessageType", SimpMessageType.SUBSCRIBE,
                "simpDestination", "Expected destination", "simpUser", (Principal) () -> user1Id,
                "simpSubscriptionId", subscriptionId);
        return new GenericMessage<Object>(new byte[0], headers);
    }

    public static Message<?> getUnsubscribeMessage() {
        Map<String, Object> headers = Map.of("simpMessageType", SimpMessageType.UNSUBSCRIBE,
                "simpUser", (Principal) () -> user1Id, "simpSubscriptionId", subscriptionId);
        return new GenericMessage<Object>(new byte[0], headers);
    }
}
