package site.hornsandhooves.dixit.service.listeners.aspects;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;


import java.util.Objects;


@Aspect
@Component
@AllArgsConstructor
public class MessageExceptionHandlingAspect {

    private SimpMessagingTemplate simp;

    @Around("@annotation(site.hornsandhooves.dixit.service.listeners.annotations.EnableMessageExceptionHandler)")
    @SneakyThrows
    public Object verifyTopic(ProceedingJoinPoint pjp) {
        var arg = (AbstractSubProtocolEvent) pjp.getArgs()[0];
        var rsl = new Object();
        try {
            rsl = pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            simp.convertAndSendToUser(Objects.requireNonNull(arg.getUser()).getName(),
                    "/queue/private-messages", new GenericMessage<>(String.format("{\"errorMessage\": %s}", e.getMessage())));
        }
        return rsl;
    }
}
