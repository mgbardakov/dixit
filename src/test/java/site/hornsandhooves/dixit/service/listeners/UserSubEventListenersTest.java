package site.hornsandhooves.dixit.service.listeners;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserSubEventListenersTest {

    @Mock
    private SimpMessagingTemplate simp;
    @Mock
    private SessionSubscribeEvent subEvent;
    @InjectMocks
    private UserSubEventListeners userSubEventListeners;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void handleSessionSubscribeEvent() {
        //when
        when(subEvent.getUser()).thenReturn(() -> "name");
        //then
        userSubEventListeners.handleSessionSubscribeEvent(subEvent);
        //assert
        verify(simp).convertAndSendToUser(any(), any(), any());
    }
}
