package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CardTemplate;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveEvaluatorTest {
    public Card stoat;
    public Card stuntedWolf;
    public Card stinkbug;
    public Card bullfrog;
    public Card bloodhound;
    public Card squirrel;
    public Game game;

    @BeforeEach
    public void createCardsAndGame() {
        stoat = CardTemplate.createStoat();
        stuntedWolf = CardTemplate.createStuntedWolf();
        stinkbug = CardTemplate.createStinkbug();
        bullfrog = CardTemplate.createBullfrog();
        bloodhound = CardTemplate.createBloodhound();
        squirrel = CardTemplate.createSquirrel();

        Deck deck = new Deck(new ArrayList<>(){{
            add(squirrel);
            add(stoat);}});

        game = new Game(deck);
    }


    @Test
    public void generateCombinations_threeIntsCombosOfTwo_shouldReturnCorrectNumberOfCombos() {
        // Arrange
        ArrayList<Integer> list = new ArrayList<>() {{
            add(0);
            add(2);
            add(3);}};

        int expected = 3;
        // Act
        List<List<Integer>> combinations = MoveEvaluator.generateCombinations(list, 2);
        int actual = combinations.size();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void generateCombinations_fourIntsCombosOfOne_shouldReturnCorrectNumberOfCombos() {
        // Arrange
        ArrayList<Integer> list = new ArrayList<>() {{
            add(0);
            add(1);
            add(2);
            add(3);}};

        int expected = 4;
        // Act
        List<List<Integer>> combinations = MoveEvaluator.generateCombinations(list, 1);
        int actual = combinations.size();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void generateCombinations_fourIntsCombosOfThree_shouldReturnCorrectNumberOfCombos() {
        // Arrange
        ArrayList<Integer> list = new ArrayList<>() {{
            add(0);
            add(1);
            add(2);
            add(3);}};

        int expected = 4;
        // Act
        List<List<Integer>> combinations = MoveEvaluator.generateCombinations(list, 3);
        int actual = combinations.size();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void findValidMoves_emptyBoardOneFreeCardInHand_shouldReturnListOfCorrectLength() {
        // Arrange
        try {
            game.getPlayer().drawSpecificCardFromDeckByIndex(0);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }

        int expected = 4;
        // Act
        ArrayList<Move> validMoves = MoveEvaluator.findValidMoves(game);
        int actual = validMoves.size();
        //
        for (Move move : validMoves) {
            System.out.println(move.displayMove());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findValidMoves_oneCardOnBoardOneFreeCardInHand_shouldReturnListOfCorrectLength() {
        // Arrange
        try {
            game.getBoard().placePlayerCard(stuntedWolf,2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        try {
            game.getPlayer().drawSpecificCardFromDeckByIndex(0);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }

        int expected = 3;
        // Act
        ArrayList<Move> validMoves = MoveEvaluator.findValidMoves(game);
        int actual = validMoves.size();
        //
        for (Move move : validMoves) {
            System.out.println(move.displayMove());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findValidMoves_oneCardOnBoardOneBloodCardInHand_shouldReturnListOfCorrectLength() {
        // Arrange
        try {
            game.getBoard().placePlayerCard(stuntedWolf,2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        try {
            game.getPlayer().drawSpecificCardFromDeckByIndex(1);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }

        int expected = 4;
        // Act
        ArrayList<Move> validMoves = MoveEvaluator.findValidMoves(game);
        int actual = validMoves.size();
        //
        for (Move move : validMoves) {
            System.out.println(move.displayMove());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findValidMoves_threeCardsOnBoardTwoBloodCardInHand_shouldReturnListOfCorrectLength() {
        // Arrange
        try {
            game.getBoard().placePlayerCard(stuntedWolf,2);
            game.getBoard().placePlayerCard(stinkbug, 1);
            game.getBoard().placePlayerCard(stuntedWolf, 0);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        game.getPlayer().receiveCardInHand(stoat);
        game.getPlayer().receiveCardInHand(bloodhound);

        int expected = 6+9;
        // Act
        ArrayList<Move> validMoves = MoveEvaluator.findValidMoves(game);
        int actual = validMoves.size();
        //
        for (Move move : validMoves) {
            System.out.println(move.displayMove());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void evaluateMove_playerAttacksScore_shouldReturnCorrectScore() {
        // Arrange
        try {
            game.getBoard().placePlayerCard(squirrel, 2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        Move move = new Move(stoat, 2, 2);
        int expected = 1;
        // Act
        int actual = MoveEvaluator.evaluateMove(move, game);
        // Assert
        assertEquals(expected, actual);
    }
}