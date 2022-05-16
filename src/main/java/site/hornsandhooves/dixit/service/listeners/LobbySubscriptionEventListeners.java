package site.hornsandhooves.dixit.service.listeners;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import site.hornsandhooves.dixit.service.listeners.annotations.EnableMessageExceptionHandler;
import site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener;
import site.hornsandhooves.dixit.service.lobby.DestinationResolver;
import site.hornsandhooves.dixit.service.lobby.LobbyStateService;
import site.hornsandhooves.dixit.service.lobby.UserService;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class LobbySubscriptionEventListeners {

    private SimpMessagingTemplate simp;
    private LobbyStateService lobbyStateService;
    private UserService userService;
    private DestinationResolver destinationResolver;
    private static final String LOBBY_TOPIC_PREFIX = "/topic/lobby";
    private static final Pattern PATTERN = Pattern.compile("/");


    @TopicEventListener(pattern = "\\/topic\\/lobby\\/.*")
    @EnableMessageExceptionHandler
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var message = event.getMessage();
        var dest = destinationResolver.getDestination(message);
        var lobbyKey = resolveLobbyKey(dest);
        var user = userService.getUser(event.getUser());
        var lobby = lobbyStateService.getLobbyByKey(lobbyKey);
        if (lobby != null && lobby.getUsers().isEmpty()) {
            user.setHost(true);
        }
        lobbyStateService.joinLobby(lobbyKey, user);
        simp.convertAndSend(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby == null ? "" : lobby));

    }

    @TopicEventListener(pattern = "\\/topic\\/lobby\\/.*")
    @EnableMessageExceptionHandler
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        var message = event.getMessage();
        var dest = destinationResolver.removeDestination(message);
        var lobbyKey = resolveLobbyKey(dest);
        var user = userService.getUser(event.getUser());
        var lobby = lobbyStateService.leaveLobby(lobbyKey, user);
        simp.convertAndSend(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby));
    }

    private String resolveLobbyKey(String dest) {
        var arr = PATTERN.split(dest);
        return arr[arr.length - 1];
    }
}
