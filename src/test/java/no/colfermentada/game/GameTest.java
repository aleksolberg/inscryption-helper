package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.*;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void displayGame_fourCardsScoreThreeTwoBones_shouldReturnCorrectString() {
        Deck deck = new Deck();
        Board board = null;
        try {
            deck.populateStandardDeck();
            board = new Board(deck);
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        Game game = new Game(board);
        try {
            board.drawSpecificCardFromDeck(0);
            board.drawSpecificCardFromDeck(0);
            board.drawSpecificCardFromDeck(0);
            board.drawSpecificCardFromDeck(0);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e);
        }

        try {
            board.placePlayerCard(board.getHand().get(0), 2);
            board.placeOpponentCard(board.getHand().get(0), 1);
            board.opponentCardsApproaches();
            board.placeOpponentCard(board.getHand().get(0), 2);
            board.placePlayerCard(board.getHand().get(0), 3);
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
                "Hand: \n" +
                "+--------+--------+--------+--------+\n" +
                "|   Stoat|Stntd Wl|Stinkbug|Bullfrog|\n" +
                "|  No 1Bl|  Ca 1Bl|  In 2Bo|  Re 1Bl|\n" +
                "|   1   3|   2   2|   1   2|   1   2|\n" +
                "+--------+--------+--------+--------+\n" +
                "Score: 3\n" +
                "Bones: 2\n";
        // Act
        String actual = game.displayGame();
        // Assert
        assertEquals(expected, actual);
    }

}