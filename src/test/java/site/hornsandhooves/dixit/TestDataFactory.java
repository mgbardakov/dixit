package site.hornsandhooves.dixit;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEventType;
import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class TestDataFactory {

    private static final String USER_1_ID = "f12184fd-2071-48b6-861c-659bef1a4385";
    private static final String SUBSCRIPTION_ID = "sub-0";
    private static final String LOBBY_ID = "abc123";

    public static User getUser() {
        return User.builder().id(UUID.fromString(USER_1_ID)).name("Biba").host(true).build();
    }

    public static Message<?> getSubscribeMessage() {
        Map<String, Object> headers = Map.of("simpMessageType", SimpMessageType.SUBSCRIBE,
                "simpDestination", "Expected destination", "simpUser", (Principal) () -> USER_1_ID,
                "simpSubscriptionId", SUBSCRIPTION_ID);
        return new GenericMessage<Object>(new byte[0], headers);
    }

    public static Message<?> getUnsubscribeMessage() {
        Map<String, Object> headers = Map.of("simpMessageType", SimpMessageType.UNSUBSCRIBE,
                "simpUser", (Principal) () -> USER_1_ID, "simpSubscriptionId", SUBSCRIPTION_ID);
        return new GenericMessage<Object>(new byte[0], headers);
    }

    public static LobbyEvent getCreateLobbyEvent() {
        return new LobbyEvent(LobbyEventType.CREATE_LOBBY, null);
    }

    public static Lobby getLobby() {
        var lobby = new Lobby();
        lobby.setLobbyId(LOBBY_ID);
        return lobby;
    }

}
