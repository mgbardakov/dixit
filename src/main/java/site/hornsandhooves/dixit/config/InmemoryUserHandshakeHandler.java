package site.hornsandhooves.dixit.config;

import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import site.hornsandhooves.dixit.service.lobby.UserService;

import java.security.Principal;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class InmemoryUserHandshakeHandler extends DefaultHandshakeHandler {

    private UserService userService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        var userName  = (String) attributes.get("userName");
        var user = userService.registerUser(userName);
        return new UserPrincipal(user.getName());
    }
}
