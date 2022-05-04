package site.hornsandhooves.dixit.model.game;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Player {

    private UUID id;
    private String name;
    private boolean turnHolder;
    private int score;
    private List<Card> cards;

    public void addCard(Card card) {
        cards.add(card);
    }

}
