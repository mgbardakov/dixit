package site.hornsandhooves.dixit.service.listeners;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener;


import java.util.Optional;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserSubEventListeners {

    private SimpMessagingTemplate simp;
    private static final String PRIVATE_MESSAGES = "/queue/private-messages";

    @TopicEventListener(pattern = PRIVATE_MESSAGES)
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var principal = Optional.ofNullable(event.getUser())
                .orElseThrow(() -> new RuntimeException("User is not registered!"));
        simp.convertAndSendToUser(principal.getName(), PRIVATE_MESSAGES,
                new GenericMessage<>("{\"message\" : \"SUCCESS\"}"));
    }


}
