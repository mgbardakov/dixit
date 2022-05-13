package site.hornsandhooves.dixit.service.lobby;

import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;

public interface LobbyStateService {
    Lobby addNewLobby(User user);
    Lobby joinLobby(String lobbyId, User user);
    Lobby leaveLobby(String lobbyId, User user);
    void removeLobby(String lobbyId);
    boolean isLobbyExist(String lobbyId);

}
