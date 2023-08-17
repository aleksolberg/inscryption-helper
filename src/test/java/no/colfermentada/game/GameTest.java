package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.*;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.players.Player;
import no.colfermentada.utils.CardDisplayer;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    public Card stoat;
    public Card stuntedWolf;
    public Card stinkbug;
    public Card bullfrog;
    public Game game;

    @BeforeEach
    public void createCardsAndGame() throws InvalidBoardException, InvalidCardException {
        try {
            stoat = new Card.Builder()
                    .withName("Stoat")
                    .withHealth(3)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.None)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }

        try {
            stuntedWolf = new Card.Builder()
                    .withName("Stunted Wolf")
                    .withHealth(2)
                    .withPower(2)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Canine)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }

        try {
            stinkbug = new Card.Builder()
                    .withName("Stinkbug")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Bones)
                    .withCost(2)
                    .withTribe(Tribe.Insect)
                    .withSigil(Sigil.Stinky)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }

        try {
            bullfrog = new Card.Builder()
                    .withName("Bullfrog")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Reptile)
                    .withSigil(Sigil.MightyLeap)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }

        Board board = new Board();

        board.placePlayerCard(stoat, 1);
        board.placePlayerCard(stuntedWolf,3);
        board.placeOpponentCard(stinkbug,1);
        board.placeOpponentCard(bullfrog,2);

        game = new Game(board);
        game.getBoard().opponentCardsApproaches();
    }

    @Test
    public void displayGame_fourCardsScoreThreeTwoBones_shouldReturnCorrectString() {
        Deck deck = new Deck();
        Board board = null;
        try {
            deck.populateStandardDeck();
            board = new Board();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        Game game = new Game(board);
        Player player = new Player(game, deck);
        try {
            player.drawSpecificCardFromDeck(0);
            player.drawSpecificCardFromDeck(0);
            player.drawSpecificCardFromDeck(0);
            player.drawSpecificCardFromDeck(0);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }

        try {
            board.placePlayerCard(player.getHand().get(0), 2);
            board.placeOpponentCard(player.getHand().get(0), 1);
            board.opponentCardsApproaches();
            board.placeOpponentCard(player.getHand().get(0), 2);
            board.placePlayerCard(player.getHand().get(0), 3);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }

        game.increaseScore(3);
        game.increaseBones(2);

        String expected = "Approaching: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |        |   Stoat|        |\n" +
                "|        |        |  No 1Bl|        |\n" +
                "|        |        |   1   3|        |\n" +
                "+--------+--------+--------+--------+\n" +
                "Opposing: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |   Stoat|        |        |\n" +
                "|        |  No 1Bl|        |        |\n" +
                "|        |   1   3|        |        |\n" +
                "+--------+--------+--------+--------+\n" +
                "Played: \n" +
                "+--------+--------+--------+--------+\n" +
                "|        |        |   Stoat|   Stoat|\n" +
                "|        |        |  No 1Bl|  No 1Bl|\n" +
                "|        |        |   1   3|   1   3|\n" +
                "+--------+--------+--------+--------+\n" +
                "Score: 3\n" +
                "Bones: 2\n";
        // Act
        String actual = game.displayGame();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void playerAttacks_attacksScoreAndOpposingCard_shouldReturnCorrectBoard() throws InvalidCardException, InvalidBoardException {
        // Arrange
        Board expected = new Board();
        Card stoatClone = stoat.clone();
        Card stuntedWolfClone = stuntedWolf.clone();
        Card stinkbugClone = stinkbug.clone();
        Card bullfrogClone = bullfrog.clone();
        stinkbugClone.takeDamage(1);

        expected.placePlayerCard(stoatClone, 1);
        expected.placePlayerCard(stuntedWolfClone,3);
        expected.placeOpponentCard(stinkbugClone,1);
        expected.placeOpponentCard(bullfrogClone,2);
        expected.opponentCardsApproaches();
        // Act
        game.playerAttacks();
        Board actual = game.getBoard();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void executeTurn_attacksScoreAndOpposingCard_shouldReturnCorrectBoard() throws InvalidCardException, InvalidBoardException {
        // Arrange
        Board expected = new Board();
        Card stoatClone = stoat.clone();
        Card stuntedWolfClone = stuntedWolf.clone();
        Card stinkbugClone = stinkbug.clone();
        Card bullfrogClone = bullfrog.clone();
        stinkbugClone.takeDamage(1);
        stoatClone.takeDamage(1);

        expected.placePlayerCard(stoatClone, 1);
        expected.placePlayerCard(stuntedWolfClone,3);
        expected.placeOpponentCard(stinkbugClone,1);
        expected.placeOpponentCard(bullfrogClone,2);
        expected.opponentCardsApproaches();
        // Act
        game.executeTurn();
        Board actual = game.getBoard();
        // Assert
        assertEquals(expected, actual);
    }
}