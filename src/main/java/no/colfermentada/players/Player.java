package no.colfermentada.players;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.game.Game;
import no.colfermentada.game.InvalidMoveException;
import no.colfermentada.utils.Rules;

import java.util.ArrayList;
import java.util.Optional;

public class Player {
    private Game game;
    private ArrayList<Card> hand;
    private Deck deck;

    public Player(Game game, Deck deck) {
        this.game = game;
        hand = new ArrayList<>();
        this.deck = deck;
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

    public void playCard(int cardIndex, int slot, Optional<int[]> sacrifices) throws InvalidMoveException {
        Card card = hand.get(cardIndex);
        CostType costType = card.getCostType();
        int cost = card.getCost();

        switch (costType) {
            case Blood:
                if (Rules.validateMoveBloodCostType(game.getBoard(), card, slot, sacrifices.orElse(new int[0]))){
                    for (int sacrificeSlot : sacrifices.orElse(new int[0])) {
                        game.getBoard().discardCardInSlot(sacrificeSlot);
                        try {
                            game.getBoard().placePlayerCard(hand.remove(cardIndex), slot);
                        } catch (InvalidBoardException e) {
                            throw new InvalidMoveException(e.getMessage());
                        }
                    }
                }
                break;
            case Bones:
                if (Rules.validateMoveBonesCostType(game.getBoard(), card, slot, game.getBones())) {
                    game.decreaseBones(card.getCost());
                    try {
                        game.getBoard().placePlayerCard(hand.remove(cardIndex), slot);
                    } catch (InvalidBoardException e) {
                        throw new InvalidMoveException(e.getMessage());
                    }
                }
                break;
            case None:
                if (Rules.validateMoveFreeCost(game.getBoard(), card, slot)) {
                    try {
                        game.getBoard().placePlayerCard(hand.remove(cardIndex), slot);
                    } catch (InvalidBoardException e) {
                        throw new InvalidMoveException(e.getMessage());
                    }
                }
                break;
            default:
                throw new InvalidMoveException("Unknown cost type");
        }
    }

    public void playCard(int cardIndex, int slot) throws InvalidMoveException {
        playCard(cardIndex, slot, null);
    }

    public void endTurn() {

    }
}
