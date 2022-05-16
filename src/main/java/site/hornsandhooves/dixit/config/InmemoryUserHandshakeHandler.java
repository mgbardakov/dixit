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
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class InmemoryUserHandshakeHandler extends DefaultHandshakeHandler {

    private UserService userService;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        var userName  = getQueryParameters(request.getURI().getQuery()).get("userName");
        var user = userService.registerUser(userName);
        return new UserPrincipal(user.getId().toString());
    }

    private Map<String, String> getQueryParameters(String query){
        Map<String, String> rslMap = new HashMap<>();
        for (String pairString : query.split("&")) {
            var pairArray = pairString.split("=");
            rslMap.put(pairArray[0], pairArray[1]);
        }
        return rslMap;
    }
}
