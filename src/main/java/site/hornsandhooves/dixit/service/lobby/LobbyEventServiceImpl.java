package site.hornsandhooves.dixit.service.lobby;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import site.hornsandhooves.dixit.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.model.lobby.Lobby;

@Component
@AllArgsConstructor
public class LobbyEventServiceImpl implements LobbyEventService {

    private final LobbyStateService lobbyStateService;

    @Override
    public Lobby processEvent(LobbyEvent event) {
        Lobby lobby = null;
        switch (event.getType()) {
            case CREATE_LOBBY:
                lobby = lobbyStateService.addNewLobby(event.getUser());
                break;
            case ENTER_LOBBY:
                lobby = lobbyStateService.joinLobby(event.getLobbyId(), event.getUser());
                break;
            case LEAVE_LOBBY:
                lobby = lobbyStateService.leaveLobby(event.getLobbyId(), event.getUser());
                break;
        }
        return lobby;
    }
}
