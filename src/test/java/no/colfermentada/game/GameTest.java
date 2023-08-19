package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.*;
import no.colfermentada.deck.Deck;
import no.colfermentada.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    public Card stoat;
    public Card stuntedWolf;
    public Card stinkbug;
    public Card bullfrog;
    public Card bloodhound;
    public Game game;

    @BeforeEach
    public void createCardsAndGame() throws InvalidBoardException, InvalidCardException {
        stoat = CardTemplate.createStoat();
        stuntedWolf = CardTemplate.createStuntedWolf();
        stinkbug = CardTemplate.createStinkbug();
        bullfrog = CardTemplate.createBullfrog();
        bloodhound = CardTemplate.createBloodhound();


        Deck deck = new Deck(new ArrayList<>(){{
            add(bullfrog);
            add(stinkbug);}});

        game = new Game(deck);

        game.getBoard().placePlayerCard(stoat, 1);
        game.getBoard().placePlayerCard(stuntedWolf,3);
        game.getBoard().placeOpposingCard(bloodhound, 1);


        game.getBoard().opponentCardsApproaches();
        game.playerDrawsFromDeckByIndex(0);
    }

    @Test
    public void displayGame_fourCardsScoreThreeTwoBones_shouldReturnCorrectString() {
        game.increaseScore(3);
        game.increaseBones(2);

        String expected = "Approaching: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |        |        |        |\n" +
                "|        |        |        |        |\n" +
                "|        |        |        |        |\n" +
                "+--------+--------+--------+--------+\n" +
                "Opposing: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |Bloodhnd|        |        |\n" +
                "|        |  Ca 2Bl|        |        |\n" +
                "|        |   2   3|        |        |\n" +
                "+--------+--------+--------+--------+\n" +
                "Played: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |   Stoat|        |Stntd Wl|\n" +
                "|        |  No 1Bl|        |  Ca 1Bl|\n" +
                "|        |   1   3|        |   2   2|\n" +
                "+--------+--------+--------+--------+\n" +
                "Hand:\n" +
                "+--------+\n" +
                "|Bullfrog|\n" +
                "|  Re 1Bl|\n" +
                "|   1   2|\n" +
                "+--------+\n" +
                "Score: 3\n" +
                "Bones: 2\n";
        // Act
        String actual = game.displayGame();
        // Assert
        //System.out.println(actual);
        //System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void playerAttacks_attacksOpposingCard_shouldReturnCorrectBoard() throws InvalidCardException, InvalidBoardException {
        // Arrange
        Board expected = new Board();
        Card stoatClone = new Card(stoat);
        Card stuntedWolfClone = new Card(stuntedWolf);
        Card bloodhoundClone = new Card(bloodhound);
        bloodhoundClone.takeDamage(1);

        expected.placePlayerCard(stoatClone, 1);
        expected.placePlayerCard(stuntedWolfClone,3);
        expected.placeOpposingCard(bloodhoundClone, 1);
        // Act
        game.playerAttacks();
        Board actual = game.getBoard();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void playerAttacks_attacksScore_shouldReturnCorrectScore() throws InvalidCardException, InvalidBoardException {
        // Arrange
        int expected = 2;
        // Act
        game.playerAttacks();
        int actual = game.getScore();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void opponentAttacks_attacksOpposingCard_shouldReturnCorrectBoard() throws InvalidCardException, InvalidBoardException {
        // Arrange
        Board expected = new Board();
        Card stoatClone = new Card(stoat);
        Card stuntedWolfClone = new Card(stuntedWolf);
        Card bloodhoundClone = new Card(bloodhound);
        stoatClone.takeDamage(2);

        expected.placePlayerCard(stoatClone, 1);
        expected.placePlayerCard(stuntedWolfClone,3);
        expected.placeOpposingCard(bloodhoundClone, 1);
        // Act
        game.opponentAttacks();
        Board actual = game.getBoard();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void opponentAttacks_attacksScore_shouldReturnCorrectScore() throws InvalidCardException, InvalidBoardException {
        // Arrange
        game.getBoard().discardCardByCard(stoat);

        int expected = -2;
        // Act
        game.opponentAttacks();
        int actual = game.getScore();
        // Assert
        assertEquals(expected, actual);
    }
}