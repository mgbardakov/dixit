package site.hornsandhooves.dixit.service.lobby;

import site.hornsandhooves.dixit.model.lobby.User;

import java.security.Principal;

public interface UserService {
    User registerUser(Principal principal, String name);
    User getUser(Principal principal);
}
