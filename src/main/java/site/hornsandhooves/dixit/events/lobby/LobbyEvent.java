package site.hornsandhooves.dixit.events.lobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.hornsandhooves.dixit.model.lobby.User;

@Data
@AllArgsConstructor
public class LobbyEvent {
    private final LobbyEventType type;
    private final String LobbyId;
    private final User user;
}
