package site.hornsandhooves.dixit.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.service.lobby.LobbyEventService;

@Controller
@AllArgsConstructor
public class WebsocketController {

    private LobbyEventService lobbyEventService;

    @MessageMapping("/lobby")
    @SendToUser("/queue/private-messages")
    public Message<Object> sendEvent(LobbyEvent event) {
        return new GenericMessage<>(lobbyEventService.processEvent(event));
    }
}
