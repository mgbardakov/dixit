package site.hornsandhooves.dixit.service.lobby;

import org.springframework.stereotype.Service;
import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;
import site.hornsandhooves.dixit.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InmemoryLobbyStateService implements LobbyStateService {

    private static final int LOBBY_ID_LENGTH = 10;
    private final Map<String, Lobby> lobbyMap = new HashMap<>();

    @Override
    public Lobby getLobbyByKey(String lobbyKey) {
        return lobbyMap.get(lobbyKey);
    }

    @Override
    public Lobby addNewLobby() {
        var lobbyId = StringUtils.getRandomString(LOBBY_ID_LENGTH);
        while(lobbyMap.containsKey(lobbyId)) {
            lobbyId = StringUtils.getRandomString(LOBBY_ID_LENGTH);
        }
        var lobby = new Lobby();
        lobby.setLobbyId(lobbyId);
        lobbyMap.put(lobbyId, lobby);
        return lobby;
    }

    @Override
    public Lobby joinLobby(String lobbyId, User user) {
        return Optional.ofNullable(lobbyMap.get(lobbyId)).map(lobby -> {
            lobby.addUser(user);
            return lobby;
        }).orElseThrow(() -> new RuntimeException("No such lobby"));
    }

    @Override
    public Lobby leaveLobby(String lobbyId, User user) {
        if (!lobbyMap.containsKey(lobbyId)) {
            throw new RuntimeException("No such lobby");
        }
        if (lobbyMap.get(lobbyId).getUsers().stream().noneMatch(us -> us.getId().equals(user.getId()))) {
            throw new RuntimeException("No such user");
        }
        var lobby = lobbyMap.get(lobbyId);
        lobby.getUsers().removeIf(us -> us.getId().equals(user.getId()));
        if(lobby.getUsers().stream().noneMatch(User::isHost) && lobby.getUsers().size() > 0) {
            lobby.getUsers().iterator().next().setHost(true);
        }
        return lobby;
    }

    @Override
    public void removeLobby(String lobbyId) {
        if (!lobbyMap.containsKey(lobbyId)) {
            throw new RuntimeException("No such lobby");
        }
        lobbyMap.remove(lobbyId);
    }

    @Override
    public boolean isLobbyExist(String lobbyId) {
        return lobbyMap.containsKey(lobbyId);
    }

}
