package site.hornsandhooves.dixit.model.events.lobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.hornsandhooves.dixit.model.lobby.User;

@Data
@AllArgsConstructor
public class LobbyEvent {
    private final LobbyEventType type;
    private final String lobbyId;
    private final User user;
}
