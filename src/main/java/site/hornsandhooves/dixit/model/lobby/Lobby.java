package site.hornsandhooves.dixit.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {

    private static final int CAPACITY = 6;

    private String lobbyId;
    private final Set<User> users = new HashSet<>();

    public void addUser(User user) {
        if (users.size() <= CAPACITY) {
            users.add(user);
        } else {
            throw new RuntimeException("Too may users for this room!");
        }
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

}
