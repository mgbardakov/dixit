package site.hornsandhooves.dixit.service.listeners;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener;
import site.hornsandhooves.dixit.service.lobby.LobbyStateService;
import site.hornsandhooves.dixit.service.lobby.UserService;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class LobbySubscriptionEventListeners {

    private SimpMessagingTemplate simp;
    private LobbyStateService lobbyStateService;
    private UserService userService;
    private static final String LOBBY_TOPIC_PREFIX = "/topic/lobby";
    private static final String PRIVATE_MESSAGES = "/queue/private-messages";
    private static final Pattern PATTERN = Pattern.compile("/");


    @TopicEventListener(pattern = "\\/topic\\/lobby\\/.*")
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var lobbyKey = resolveLobbyKey(event);
        if (lobbyStateService.isLobbyExist(lobbyKey)) {
           var user = userService.getUser(event.getUser());
           var lobby = lobbyStateService.joinLobby(lobbyKey, user);
           simp.send(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby));
        }
    }

    @TopicEventListener(pattern = "\\/topic\\/lobby\\/.*")
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        var lobbyKey = resolveLobbyKey(event);
        if (lobbyStateService.isLobbyExist(lobbyKey)) {
            var user = userService.getUser(event.getUser());
            var lobby = lobbyStateService.leaveLobby(lobbyKey, user);
            simp.send(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby));
        }
    }

    private String resolveLobbyKey(AbstractSubProtocolEvent event) {
        var message = event.getMessage();
        var dest = (String) message.getHeaders().get("simpDestination");
        var arr = PATTERN.split(dest);
        return arr[arr.length - 1];
    }
}
