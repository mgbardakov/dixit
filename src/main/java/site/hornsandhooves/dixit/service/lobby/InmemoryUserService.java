package site.hornsandhooves.dixit.service.lobby;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import site.hornsandhooves.dixit.model.lobby.User;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InmemoryUserService implements UserService {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User registerUser(String name) {
        var rslUser = User.builder().id(UUID.randomUUID()).name(name).build();
        users.put(rslUser.getId(), rslUser);
        return rslUser;
    }

    @Override
    public User getUser(Principal principal) {
        return users.get(UUID.fromString(principal.getName()));
    }
}
