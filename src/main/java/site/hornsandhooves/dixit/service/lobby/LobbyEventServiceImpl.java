package site.hornsandhooves.dixit.service.lobby;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.model.events.lobby.LobbyEventType;
import site.hornsandhooves.dixit.model.lobby.Lobby;

@Component
@AllArgsConstructor
public class LobbyEventServiceImpl implements LobbyEventService {

    private final LobbyStateService lobbyStateService;

    @Override
    public Lobby processEvent(LobbyEvent event) {
        Lobby lobby = null;
        if (event.getType() == LobbyEventType.CREATE_LOBBY) {
            lobby = lobbyStateService.addNewLobby(event.getUser());
        }
        return lobby;
    }
}
