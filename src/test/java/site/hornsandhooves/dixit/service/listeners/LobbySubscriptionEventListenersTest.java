package site.hornsandhooves.dixit.service.listeners;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import site.hornsandhooves.dixit.TestDataFactory;
import site.hornsandhooves.dixit.service.lobby.DestinationResolver;
import site.hornsandhooves.dixit.service.lobby.LobbyStateService;
import site.hornsandhooves.dixit.service.lobby.UserService;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class LobbySubscriptionEventListenersTest {

    @SpyBean
    private UserService userService;
    @SpyBean
    private LobbyStateService lobbyStateService;
    @SpyBean
    private DestinationResolver resolver;
    @Autowired
    private LobbySubscriptionEventListeners listeners;

    @Test
    void whenTopicVerificationFails() {
        //when
        doReturn(TestDataFactory.getUser()).when(userService).getUser(any());
        doReturn(TestDataFactory.getEmptyLobby()).when(lobbyStateService).getLobbyByKey(any());
        var headers = new MessageHeaders(Map.of("simpMessageType", SimpMessageType.SUBSCRIBE, "simpDestination", "topic/game/abc"));
        var message = new GenericMessage<>(new byte[]{}, headers);
        var event = new SessionSubscribeEvent(new Object(), message);
        //then
        listeners.handleSessionSubscribeEvent(event);
        //assertions
        verify(userService, times(0)).getUser(any());
    }
    @Test
    void whenTopicVerificationWorksAndUserJoinsLobby() {
        //when
        var user = TestDataFactory.getUser();
        var lobby = lobbyStateService.addNewLobby();
        doReturn(user).when(userService).getUser(any());
        doReturn(lobby).when(lobbyStateService).getLobbyByKey(any());
        doReturn("/topic/lobby/" + lobby.getLobbyId()).when(resolver).getDestination(any());
        var event = TestDataFactory.getSubscribeEvent();
        //then
        listeners.handleSessionSubscribeEvent(event);
        //assertions
        verify(userService, times(1)).getUser(any());
        verify(lobbyStateService, times(1)).joinLobby(lobby.getLobbyId(), user);
    }

    @Test
    void whenUnsubscribeEventHappensThanUserLeavesLobby() {
        //when
        var user = TestDataFactory.getUser();
        var lobby = lobbyStateService.addNewLobby();
        doReturn(user).when(userService).getUser(any());
        doReturn(lobby).when(lobbyStateService).getLobbyByKey(any());
        doReturn("/topic/lobby/" + lobby.getLobbyId()).when(resolver).getDestination(any());
        doReturn("/topic/lobby/" + lobby.getLobbyId()).when(resolver).removeDestination(any());
        var subEvent = TestDataFactory.getSubscribeEvent();
        listeners.handleSessionSubscribeEvent(subEvent);
        //then
        listeners.handleSessionUnsubscribeEvent(TestDataFactory.getUnsubscribeEvent());
        //assertions
        verify(lobbyStateService, times(1)).leaveLobby(lobby.getLobbyId(), user);
    }



}
