package site.hornsandhooves.dixit.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import site.hornsandhooves.dixit.TestDataFactory;
import site.hornsandhooves.dixit.service.lobby.UserService;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InmemoryUserHandshakeHandlerTest {

    @Mock
    private UserService userService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ServerHttpRequest request;

    @Mock
    private WebSocketHandler handler;

    @InjectMocks
    private InmemoryUserHandshakeHandler handshakeHandler;

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
    void whenHandshakeHappensThenUserGotRegistered() {
        //when
        var user = TestDataFactory.getUser();
        when(request.getURI().getQuery()).thenReturn("userName=biba");
        when(userService.registerUser(any())).thenReturn(user);
        //then
        handshakeHandler.determineUser(request, handler, new HashMap<>());
        //assertions
        verify(userService, times(1)).registerUser("biba");
    }
}
