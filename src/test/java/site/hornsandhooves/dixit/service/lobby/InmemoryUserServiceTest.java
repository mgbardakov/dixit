package site.hornsandhooves.dixit.service.lobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InmemoryUserServiceTest {

    private InmemoryUserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new InmemoryUserService();
    }

    @Test
    void whenRegisterAndThenGetTheUser() {
        //when
        var expectedUser = userService.registerUser("Biba");
        //then
        var actualUser = userService.getUser(() -> expectedUser.getId().toString());
        //assertions
        assertEquals(expectedUser, actualUser);
    }
}
