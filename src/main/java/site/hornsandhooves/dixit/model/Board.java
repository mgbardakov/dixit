package site.hornsandhooves.dixit.model;

import lombok.Data;

import java.util.List;

@Data
public class Board {
    private List<Player> players;
    private VoteField voteField;
    private Turn turn;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addCardToVoteField(Card card) {
        var voteItem = new VoteItem();
        voteItem.setCard(card);
        voteField.getVoteItems().add(voteItem);
    }

    public void vote(Player player, VoteItem voteItem) {
        voteField.getVoteItems().stream().filter(x -> x.getId()
                .equals(voteItem.getId()))
                .peek(x -> x.addPlayerName(player.getName()));
    }


}
