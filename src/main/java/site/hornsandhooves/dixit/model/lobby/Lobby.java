package site.hornsandhooves.dixit.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {

    private static final int CAPACITY = 6;

    private String lobbyId;
    private final List<User> users = new ArrayList<>();

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
