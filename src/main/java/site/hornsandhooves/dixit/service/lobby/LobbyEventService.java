package site.hornsandhooves.dixit.service.lobby;

import site.hornsandhooves.dixit.events.lobby.LobbyEvent;
import site.hornsandhooves.dixit.model.lobby.Lobby;

public interface LobbyEventService {
    Lobby processEvent(LobbyEvent event);
}
