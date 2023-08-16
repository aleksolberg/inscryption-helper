package no.colfermentada.players;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.game.Game;
import no.colfermentada.game.InvalidMoveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void drawSquirrel_shouldReturnSquirrelInHand() throws InvalidBoardException, InvalidCardException {
        // Arrange
        Player player = new Player(new Game(), new Deck());
        Card expected = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        // Act
        player.drawSquirrel();
        Card actual = player.getHand().get(0);
        // Assert
        assertEquals(expected, actual);

    }

    @Test
    public void playCard_validMoveFreeCost_shouldReturnCardInCorrectSlot() throws InvalidCardException, InvalidBoardException, InvalidMoveException {
        // Arrange
        Game game = new Game();
        Player player = new Player(game, new Deck());
        player.drawSquirrel();
        Card expected = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        // Act
        player.playCard(0, 0);
        Card actual = game.getBoard().getPlayedCards()[0];
        game.getBoard().displayBoard();
        // Assert
        assertEquals(expected, actual);
    }

}