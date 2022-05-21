package site.hornsandhooves.dixit.service.lobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.hornsandhooves.dixit.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

class InmemoryLobbyStateServiceTest {

    private InmemoryLobbyStateService service;

    @BeforeEach
    void setUp() {
        this.service = new InmemoryLobbyStateService();
    }

    @Test
    public void whenNewLobbyAddedThanGathered() {
        //when
        var expectedLobby = service.addNewLobby();
        //then
        var actualLobby = service.getLobbyByKey(expectedLobby.getLobbyId());
        //asserts
        assertEquals(expectedLobby, actualLobby);
    }

    @Test
    public void whenLobbyAddedThenCheckedIfItExists() {
        //when
        var lobby = service.addNewLobby();
        //asserts
        assertTrue(service.isLobbyExist(lobby.getLobbyId()));
    }

    @Test
    public void whenNewLobbyAddedAndUserJoinedThenCheckIfUserExistInLobby() {
        //when
        var lobby = service.addNewLobby();
        var expectedUser = TestDataFactory.getUser();
        //then
        var actualUser = service.joinLobby(lobby.getLobbyId(), expectedUser)
                .getUsers().iterator().next();
        //assertions
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void whenNewLobbyAddedAndUserJoinedThenLeaved() {
        //when
        var lobby = service.addNewLobby();
        var expectedUser = TestDataFactory.getUser();
        var joinedUser = service.joinLobby(lobby.getLobbyId(), expectedUser).getUsers().iterator().next();
        //then
        service.leaveLobby(lobby.getLobbyId(), joinedUser);
        //asserts
        assertTrue(service.getLobbyByKey(lobby.getLobbyId()).getUsers().isEmpty());
    }

}
