package site.hornsandhooves.dixit.service.listeners.annotations;

import org.springframework.context.event.EventListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@EventListener
public @interface TopicEventListener {
    String pattern();
}
