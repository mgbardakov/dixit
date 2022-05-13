package site.hornsandhooves.dixit.service.listeners.aspects;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener;

import java.util.regex.Pattern;

@Aspect
@Component
public class TopicVerifyingAspect {

    @Around("@annotation(site.hornsandhooves.dixit.service.listeners.annotations.TopicEventListener)")
    @SneakyThrows
    public Object verifyTopic(ProceedingJoinPoint pjp) {
        var methodSignature = (MethodSignature) pjp.getSignature();
        var method = methodSignature.getMethod();
        var arg = (AbstractSubProtocolEvent) pjp.getArgs()[0];
        var pattern = Pattern.compile(method.getAnnotation(TopicEventListener.class).pattern());
        var dest = (String) arg.getMessage().getHeaders().get("simpDestination");
        if (dest != null && pattern.matcher(dest).matches()) {
            return pjp.proceed(pjp.getArgs());
        } else {
            return new Object();
        }
    }
}
