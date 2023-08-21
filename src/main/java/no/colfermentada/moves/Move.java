package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.game.Game;
import no.colfermentada.utils.CardDisplayer;
import no.colfermentada.utils.MoveValidator;

import java.util.Arrays;

public class Move {
    private Card card;
    private int slot;
    private int[] sacrifices;

    public Move(Card card, int slot, int[] sacrifices) {
        this.card = card;
        this.slot = slot;
        this.sacrifices = sacrifices;
    }

    public Move(Card card, int slot, int sacrifice) {
        this(card, slot, new int[] {sacrifice});
    }

    public Move(Card card, int slot) {
        this(card, slot, null);
    }

    public Card getCard() {
        return card;
    }

    public int getSlot() {
        return slot;
    }

    public int[] getSacrifices() {
        return sacrifices;
    }

    public String displayMove() {
        return "Card:\n" + card.displayCard() + "Slot: " + slot + ", Sacrifice(s): " + Arrays.toString(sacrifices) + "\n";
    }

    public void executeMove(Game game) throws InvalidBoardException {
        if (MoveValidator.isValidMove(this, game)) {
            CostType costType = card.getCostType();
            switch (costType) {
                case Blood -> {
                    for (int sacrificeSlot : sacrifices) {
                        game.getBoard().discardCardInSlot(sacrificeSlot);
                    }
                }
                case Bones -> {
                    game.decreaseBones(card.getCost());
                }
            }
            Card cardPlayed = null;
            for (Card card : game.getPlayer().getHand()) { // TODO: Find a better way to fix the problem in MoveSequenceEvaluator
                if (card.equals(this.card)) {
                    cardPlayed = card;
                }
            }
            game.getBoard().placePlayerCard(cardPlayed, slot);
            game.getPlayer().removeCardFromHand(cardPlayed);
        } else {
            System.out.println("Move invalid. Not executed.");
        }
    }
}
