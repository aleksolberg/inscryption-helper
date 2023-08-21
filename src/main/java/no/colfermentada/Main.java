package no.colfermentada;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.*;
import no.colfermentada.deck.Deck;
import no.colfermentada.game.Game;
import no.colfermentada.moves.Move;
import no.colfermentada.moves.MoveEvaluator;
import no.colfermentada.moves.MoveSequence;
import no.colfermentada.moves.MoveSequenceEvaluator;
import no.colfermentada.utils.MoveValidator;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.populateStandardDeck();

        Game game = new Game(deck);
        game.playerDrawsSquirrel();
        game.playerDrawsFromDeckByIndex(0);
        game.playerDrawsFromDeckByIndex(0);
        try {
            game.getBoard().placeOpposingCard(CardTemplate.createBloodhound(), 2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }

        System.out.println(game.displayGame());
        //MoveSequence bestSequence = MoveSequenceEvaluator.findBestMoveSequenceSingleTurn(game);
        MoveSequence[] bestSequences = MoveSequenceEvaluator.findBestMoveSequenceTwoTurns(game);

        for (MoveSequence sequence : bestSequences) {
            for (Move move : sequence.getSequence()) {
                System.out.println(move.displayMove());
            }
        }

        /*Move move1 = MoveEvaluator.findBestSingleMoveSingleTurn(game);
        Move move2 = new Move(game.getPlayer().getHand().get(2), 0, 0);
        MoveSequence sequence1 = new MoveSequence(new ArrayList<>(){{add(move1); add(move2);}});
        try {
            sequence1.executeSequence(game);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        System.out.println(game.displayGame());*/
    }
}