package site.hornsandhooves.dixit.service.lobby;

import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;

public interface LobbyStateService {
    Lobby addNewLobby();
    Lobby joinLobby(String lobbyId, User user);
    Lobby getLobbyByKey(String lobbyKey);
    Lobby leaveLobby(String lobbyId, User user);
    void removeLobby(String lobbyId);
    boolean isLobbyExist(String lobbyId);

}
