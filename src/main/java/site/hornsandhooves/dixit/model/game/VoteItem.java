package site.hornsandhooves.dixit.model.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class VoteItem {

    private UUID id;
    private Card card;
    private final List<String> votedPlayersNames = new ArrayList<>();

    public void addPlayerName(String name) {
        votedPlayersNames.add(name);
    }

}
