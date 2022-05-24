package site.hornsandhooves.dixit.service.lobby;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEventType;
import site.hornsandhooves.dixit.model.lobby.Lobby;

@Service
@AllArgsConstructor
public class LobbyEventServiceImpl implements LobbyEventService {

    private final LobbyStateService lobbyStateService;

    @Override
    public Lobby processEvent(LobbyEvent event) {
        Lobby lobby = null;
        if (event.getType() == LobbyEventType.CREATE_LOBBY) {
            lobby = lobbyStateService.addNewLobby();
        }
        return lobby;
    }
}
