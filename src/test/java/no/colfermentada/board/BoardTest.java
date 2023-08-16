package no.colfermentada.board;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void displayPlayed_twoCards_shouldReturnCorrectString() {
        // Arrange
        Deck deck = new Deck();
        try {
            deck.populateStandardDeck();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        Board board = null;
        try {
            board = new Board(deck);
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        try {
            board.drawSquirrel();
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        try {
            board.drawSpecificCardFromDeck(0);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }
        try {
            board.placePlayerCard(board.getHand().get(0), 1);
            board.placePlayerCard(board.getHand().get(1), 3);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        System.out.println(board.displayCards(board.getPlayedCards()));
    }

    @Test
    public void displayDeck() throws InvalidBoardException, InvalidDeckException {
        Deck deck = new Deck();
        try {
            deck.populateStandardDeck();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        Board board = null;
        try {
            board = new Board(deck);
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        board.drawSquirrel();
        board.drawSquirrel();
        board.drawSpecificCardFromDeck(0);
        board.drawSpecificCardFromDeck(0);
        board.drawSquirrel();
        System.out.println(board.displayCards(board.getHand()));
    }

    @Test
    public void drawCard_validCard_shouldReturnCorrectCardInHand() throws InvalidCardException, InvalidDeckException {
        // Arrange
        Deck deck = new Deck();
        Card stoat = new Card.Builder()
                .withName("Stoat")
                .withHealth(3)
                .withPower(1)
                .withCostType(CostType.Blood)
                .withCost(1)
                .withTribe(Tribe.None)
                .build();
        deck.addCard(stoat);
        Board board = new Board(deck);
        Card expected = new Card.Builder()
                .withName("Stoat")
                .withHealth(3)
                .withPower(1)
                .withCostType(CostType.Blood)
                .withCost(1)
                .withTribe(Tribe.None)
                .build();
        // Act
        board.drawSpecificCardFromDeck(0);
        Card actual = board.getHand().get(0);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void drawSquirrel_valid_shouldReturnSquirrelInHand() throws InvalidCardException, InvalidBoardException {
        // Arrange
        Board board = new Board();
        Card expected = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        // Act
        board.drawSquirrel();
        Card actual = board.getHand().get(0);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void placeCard_valid_shouldReturnSquirrelInCorrectSlotOnBoard() throws InvalidCardException {
        // Arrange
        Board board = new Board();
        Card squirrel = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        Card expected = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        // Act
        try {
            board.placePlayerCard(squirrel, 2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        Card actual = board.getPlayedCards()[2];
        // Assert
        assertEquals(expected, actual);

    }
}