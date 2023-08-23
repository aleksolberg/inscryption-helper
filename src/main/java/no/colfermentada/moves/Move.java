package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.game.Game;
import no.colfermentada.players.InvalidPlayerException;
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
    public Move () {
        this.card = null;
        this.slot = 0;
        this.sacrifices = null;
    }

    public Move(Move other) {
        this.card = other.card;
        this.slot = other.slot;
        this.sacrifices = other.sacrifices;
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

    public void executeMove(Game game) throws InvalidBoardException, InvalidPlayerException, InvalidMoveException {
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
            Card cardToPlay = game.getPlayer().findCardInHand(card);
            game.getBoard().placePlayerCard(cardToPlay, slot);
            game.getPlayer().popCardFromHand(cardToPlay);
        } else {
            throw new InvalidMoveException("Invalid Move.");
        }
    }
}
