package site.hornsandhooves.dixit.model;

import lombok.Data;

import java.util.Map;

@Data
public class Turn {

    private int number = 1;
    private Phase phase = Phase.RIDDLING;
    private Map<Phase, Phase> stateMachine = Map.of(Phase.RIDDLING, Phase.CARD_PLAYING,
            Phase.CARD_PLAYING, Phase.VOTING, Phase.VOTING, Phase.SCORE_COUNTING,
            Phase.SCORE_COUNTING, Phase.RIDDLING);

    public void nextTurn() {
        number++;
    }

    public void nextPhase() {
        phase = stateMachine.get(phase);
    }

    public void finish() {
        phase = Phase.FINISH;
    }

}
