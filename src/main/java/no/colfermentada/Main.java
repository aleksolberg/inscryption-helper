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
        deck.addCard(CardTemplate.createMagpie());
        deck.getPermanentCards().get(4).upgradeHealth(2);

        Game game = new Game(deck);
        game.playerDrawsSquirrel();
        game.playerDrawsFromDeckByIndex(1);
        game.playerDrawsFromDeckByIndex(1);
        game.playerDrawsFromDeckByIndex(2);

        Move playSquirrel1 = new Move(game.getPlayer().getHand().get(0), 0, 0);
        try {
            playSquirrel1.executeMove(game);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        Move playStuntedWolf = new Move(game.getPlayer().getHand().get(0), 0, 0);
        try {
            playStuntedWolf.executeMove(game);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }

        try {
            game.getBoard().placeApproachingCard(CardTemplate.createWolfCub(), 1);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }

        game.executeTurn();
        try {
            game.getBoard().placeApproachingCard(CardTemplate.createAlpha(), 2);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        game.playerDrawsSquirrel();

        System.out.println(game.displayGame());
        MoveSequence[] bestSequences = MoveSequenceEvaluator.findBestMoveSequenceTwoTurns(game);

        for (MoveSequence sequence : bestSequences) {
            System.out.println("SEQUENCE:");
            for (Move move : sequence.getSequence()) {
                System.out.println(move.displayMove());
            }
        }
        System.out.println(bestSequences[1].getOutcome());
    }
}