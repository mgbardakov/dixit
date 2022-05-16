package site.hornsandhooves.dixit.service.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import site.hornsandhooves.dixit.model.lobby.Lobby;
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
    @SneakyThrows
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var message = event.getMessage();
        var dest = destinationResolver.getDestination(message);
        var lobbyKey = resolveLobbyKey(dest);
        if (lobbyStateService.isLobbyExist(lobbyKey)) {
           var user = userService.getUser(event.getUser());
           var lobby = lobbyStateService.getLobbyByKey(lobbyKey);
           if (lobby.getUsers().isEmpty()) {
               user.setHost(true);
           }
           lobbyStateService.joinLobby(lobbyKey, user);
           simp.convertAndSend(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby));
        }
    }

    @TopicEventListener(pattern = "\\/topic\\/lobby\\/.*")
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        var message = event.getMessage();
        var dest = destinationResolver.removeDestination(message);
        var lobbyKey = resolveLobbyKey(dest);
        if (lobbyStateService.isLobbyExist(lobbyKey)) {
            var user = userService.getUser(event.getUser());
            var lobby = lobbyStateService.leaveLobby(lobbyKey, user);
            simp.convertAndSend(LOBBY_TOPIC_PREFIX + "/" + lobbyKey, new GenericMessage<>(lobby));
        }
    }

    private String resolveLobbyKey(String dest) {
        var arr = PATTERN.split(dest);
        return arr[arr.length - 1];
    }
}
