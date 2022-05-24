package site.hornsandhooves.dixit.service.listeners.aspects;

import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageExceptionHandlingAspectTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @Mock
    private ProceedingJoinPoint pjp;
    @Mock
    private SessionSubscribeEvent subscribeEvent;
    @Captor
    ArgumentCaptor<Message<String>> messageCaptor;
    private MessageExceptionHandlingAspect aspect;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        aspect = new MessageExceptionHandlingAspect(simpMessagingTemplate);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    @SneakyThrows
    void processExceptions() {
        //when
        when(pjp.proceed(any())).thenThrow(new RuntimeException("message"));
        when(pjp.getArgs()).thenReturn(new Object[] {subscribeEvent});
        when(subscribeEvent.getUser()).thenReturn(() -> "name");
        //then
        aspect.processExceptions(pjp);
        //asserts
        verify(simpMessagingTemplate).convertAndSendToUser(eq("name"),
                eq("/queue/private-messages"), messageCaptor.capture());
        assertEquals(messageCaptor.getValue().getPayload(), "{errorMessage: message}");
    }
}
