package site.hornsandhooves.dixit;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
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

    public static Lobby getEmptyLobby() {
        var lobby = new Lobby();
        lobby.setLobbyId(LOBBY_ID);
        return lobby;
    }

    public static Lobby getLobbyWithUser() {
        var lobby = getEmptyLobby();
        lobby.addUser(getUser());
        return lobby;
    }

    public static SessionSubscribeEvent getSubscribeEvent() {
        var headers = new MessageHeaders(Map.of("simpMessageType", SimpMessageType.SUBSCRIBE,
                "simpDestination", "/topic/lobby/abc123",
                "simpUser", (Principal) () -> TestDataFactory.getUser().getId().toString(),
                "simpSubscriptionId", "sub-0"));
        var message = new GenericMessage<>(new byte[]{}, headers);
        return new SessionSubscribeEvent(new Object(), message);
    }
    public static SessionUnsubscribeEvent getUnsubscribeEvent() {
        var headers = new MessageHeaders(Map.of("simpMessageType", SimpMessageType.UNSUBSCRIBE,
                "simpDestination", "/topic/lobby/abc123",
                "simpUser", (Principal) () -> TestDataFactory.getUser().getId().toString(),
                "simpSubscriptionId", "sub-0"));
        var message = new GenericMessage<>(new byte[]{}, headers);
        return new SessionUnsubscribeEvent(new Object(), message);
    }


}
