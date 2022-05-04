package site.hornsandhooves.dixit.service.lobby;

import org.springframework.stereotype.Service;
import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;
import site.hornsandhooves.dixit.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class InmemoryLobbyStateService implements LobbyStateService {

    private static final int LOBBY_ID_LENGTH = 10;
    private final Map<String, Lobby> lobbyMap = new HashMap<>();

    @Override
    public Lobby addNewLobby(User user) {
        registerUser(user);
        var lobbyId = "";
        while(lobbyMap.containsKey(lobbyId)) {
            lobbyId = StringUtils.getRandomString(LOBBY_ID_LENGTH);
        }
        var lobby = new Lobby();
        lobby.setLobbyId(lobbyId);
        lobby.addUser(user);
        lobbyMap.put(lobbyId, lobby);
        return lobby;
    }

    @Override
    public Lobby joinLobby(String lobbyId, User user) {
        registerUser(user);
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
        lobbyMap.get(lobbyId).getUsers().removeIf(us -> us.getId().equals(user.getId()));
        return lobbyMap.get(lobbyId);
    }

    @Override
    public void removeLobby(String lobbyId) {
        if (!lobbyMap.containsKey(lobbyId)) {
            throw new RuntimeException("No such lobby");
        }
        lobbyMap.remove(lobbyId);
    }

    private void registerUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
    }

}
