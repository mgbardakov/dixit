package site.hornsandhooves.dixit.service.listeners.aspects;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener;
import site.hornsandhooves.dixit.service.lobby.DestinationResolver;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Aspect
@Component
@AllArgsConstructor
public class TopicVerifyingAspect {

    private DestinationResolver destinationResolver;

    @Around("@annotation(site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener)")
    @SneakyThrows
    public Object verifyTopic(ProceedingJoinPoint pjp) {
        var methodSignature = (MethodSignature) pjp.getSignature();
        var method = methodSignature.getMethod();
        var arg = (AbstractSubProtocolEvent) pjp.getArgs()[0];
        var pattern = Pattern.compile(method.getAnnotation(TopicEventListener.class).pattern());
        var dest = destinationResolver.getDestination(arg.getMessage());
        if (dest != null && pattern.matcher(dest).matches()) {
            if (Objects.equals(arg.getMessage().getHeaders().get("simpMessageType"), SimpMessageType.SUBSCRIBE)) {
                destinationResolver.addDestination(arg.getMessage());
            }
            return pjp.proceed(pjp.getArgs());
        } else {
            return new Object();
        }
    }
}
