package site.hornsandhooves.dixit.service.lobby;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import site.hornsandhooves.dixit.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LobbyEventServiceImplTest {

    @Mock
    private LobbyStateService lobbyStateService;
    private AutoCloseable closeable;

    private LobbyEventService lobbyEventService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        lobbyEventService = new LobbyEventServiceImpl(lobbyStateService);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }


    @Test
    void whenProcessCreateLobbyEvent() {
        //when
        var event = TestDataFactory.getCreateLobbyEvent();
        when(lobbyStateService.addNewLobby()).thenReturn(TestDataFactory.getEmptyLobby());
        //then
        var lobby = lobbyEventService.processEvent(event);
        //assertions
        assertNotNull(lobby);
        Mockito.verify(lobbyStateService).addNewLobby();
    }


}
