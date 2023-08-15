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

    /*@Test
    public void drawCard_validCard_shouldReturnCorrectCardInHand() {
        // Arrange
        Deck deck = new Deck();
        Card card = new Card("Stoat", 2, 1, CostType.Blood, 1, Tribe.None);
        deck.addCard(card);
        Board board = new Board(deck);
        Card expected = new Card("Stoat", 2, 1, CostType.Blood, 1, Tribe.None);
        // Act
        board.drawFromDeck(0);
        Card actual = board.getHand().get(0);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void drawSquirrel_valid_shouldReturnSquirrelInHand() {
        // Arrange
        Board board = new Board();
        Card expected = new Card("Squirrel", 1, 0, CostType.None, 0, Tribe.Squirrel);
        // Act
        board.drawSquirrel();
        Card actual = board.getHand().get(0);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void placeCard_valid_shouldReturnSquirrelInCorrectSlotOnBoard() {
        // Arrange
        Board board = new Board();
        Card squirrel = new Card("Squirrel", 1, 0, CostType.None, 0, Tribe.Squirrel);
        Card expected = new Card("Squirrel", 1, 0, CostType.None, 0, Tribe.Squirrel);
        // Act
        try {
            board.placePlayerCard(squirrel, 2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        Card actual = board.getPlayedCards()[2];
        // Assert
        assertEquals(expected, actual);

    }*/
}