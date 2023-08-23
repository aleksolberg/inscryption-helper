package no.colfermentada.players;

import no.colfermentada.cards.*;
import no.colfermentada.game.Game;

class PlayerTest {
    public Card stoat;
    public Card stuntedWolf;
    public Card stinkbug;
    public Card bullfrog;
    public Game game;

    /*@BeforeEach
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
        board.placeApproachingCard(stinkbug,1);
        board.placeApproachingCard(bullfrog,2);

        game = new Game(board);
        game.getBoard().opponentCardsApproaches();
    }
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

    @Test
    public void playCard_validMoveRequiresOneSacrifice_shouldReturnCorrectBoard() throws InvalidCardException, InvalidMoveException, InvalidBoardException, InvalidDeckException {
        Deck deck = new Deck();
        deck.populateStandardDeck();
        Player player = new Player(game, deck);
        player.drawSpecificCardFromDeckByIndex(3);

        Board expected = new Board();
        Card stoatClone = stoat.clone();
        Card stuntedWolfClone = stuntedWolf.clone();
        Card stinkbugClone = stinkbug.clone();
        Card bullfrogClone = bullfrog.clone();
        expected.placePlayerCard(bullfrogClone, 2);
        expected.placePlayerCard(stuntedWolfClone,3);
        expected.placeApproachingCard(stinkbugClone,1);
        expected.placeApproachingCard(bullfrogClone,2);
        expected.opponentCardsApproaches();
        expected.placePlayerCard(stoatClone, 0);
        expected.discardCardInSlot(0);

        // Act
        player.playCard(0,2, 1);
        Board actual = game.getBoard();
        // Assert
        assertEquals(expected, actual);
    }*/
}