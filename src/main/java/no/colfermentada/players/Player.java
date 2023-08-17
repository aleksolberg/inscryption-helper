package no.colfermentada.players;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.game.Game;
import no.colfermentada.game.InvalidMoveException;
import no.colfermentada.utils.CardDisplayer;
import no.colfermentada.utils.Rules;

import java.util.ArrayList;
import java.util.Optional;

public class Player {
    private Game game;
    private ArrayList<Card> hand;
    private Deck deck;
    private CardDisplayer displayer;

    public Player(Game game, Deck deck) {
        this.game = game;
        hand = new ArrayList<>();
        this.deck = deck;
        displayer = new CardDisplayer();
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Deck getDeck() {
        return deck;
    }

    public String displayHand() {
        return displayer.displayCards(hand);
    }

    public void drawSpecificCardFromDeck(int index) throws InvalidDeckException {
        if (index >= deck.currentSize()) {
            throw new InvalidDeckException("Index of deck out of bounds");
        } else {
            hand.add(deck.drawSpecificCard(index));
        }
    }

    public void drawSquirrel() throws InvalidBoardException {
        hand.add(game.getBoard().drawSquirrel());
    }

    public void playCard(int cardIndex, int slot, int[] sacrifices) throws InvalidMoveException {
        Card card = hand.get(cardIndex);
        CostType costType = card.getCostType();

        switch (costType) {
            case Blood -> {
                if (sacrifices != null && Rules.validateMoveBloodCostType(game.getBoard(), card, slot, sacrifices)) {
                    for (int sacrificeSlot : sacrifices) {
                        game.getBoard().discardCardInSlot(sacrificeSlot);
                    }
                }
            }
            case Bones -> {
                if (Rules.validateMoveBonesCostType(game.getBoard(), card, slot, game.getBones())) {
                    game.decreaseBones(card.getCost());
                }
            }
            case None -> Rules.validateMoveFreeCost(game.getBoard(), card, slot);
            default -> throw new InvalidMoveException("Unknown cost type");
        }

        try {
            game.getBoard().placePlayerCard(hand.remove(cardIndex), slot);
        } catch (InvalidBoardException e) {
            throw new InvalidMoveException(e.getMessage());
        }
    }

    public void playCard(int cardIndex, int slot) throws InvalidMoveException {
        playCard(cardIndex, slot, null);
    }

    public void playCard(int cardIndex, int slot, int sacrifice) throws InvalidMoveException {
        playCard(cardIndex, slot, new int[]{sacrifice});
    }

    public void endTurn() {
        game.executeTurn();
    }
}
