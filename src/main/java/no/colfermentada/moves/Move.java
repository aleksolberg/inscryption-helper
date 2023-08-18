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
        return "Card:\n" + card.displayCard() + "\nSlot: " + slot + "\nSacrifice(s): " + Arrays.toString(sacrifices);
    }

    public void executeMove(Game game) throws InvalidBoardException {
        if (MoveValidator.isValidMove(this, game)) {
            CostType costType = card.getCostType();
            switch (costType) {
                case Blood -> {
                    for (int sacrificeSlot : sacrifices) {
                        game.getBoard().discardCardInSlot(sacrificeSlot);
                    }
                    break;
                }
                case Bones -> {
                    game.decreaseBones(card.getCost());
                }
            }
            game.getBoard().placePlayerCard(card, slot);
            game.getPlayer().receiveCardInHand(card);
        } else {
            System.out.println("Move invalid. Not executed.");
        }
    }
}
