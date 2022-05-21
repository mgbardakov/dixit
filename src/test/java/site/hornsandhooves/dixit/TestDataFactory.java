package site.hornsandhooves.dixit;

import site.hornsandhooves.dixit.model.lobby.Lobby;
import site.hornsandhooves.dixit.model.lobby.User;

import java.util.UUID;

public class TestDataFactory {

    private static final String user1Id = "f12184fd-2071-48b6-861c-659bef1a4385";
    private static final String user2Id = "555f07ac-a7d7-4c92-9873-4807fe83915c";

    public static User getUser() {
        return User.builder().id(UUID.fromString(user1Id)).name("Biba").host(true).build();
    }
}
