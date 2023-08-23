package no.colfermentada;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.*;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.game.Game;
import no.colfermentada.moves.*;
import no.colfermentada.players.InvalidPlayerException;
import no.colfermentada.utils.CardDisplayer;
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

        try {
            game.playerDrawsSquirrel();
            game.playerDrawsFromDeckByIndex(1);
            game.playerDrawsFromDeckByIndex(1);
            game.playerDrawsFromDeckByIndex(2);
        } catch (InvalidBoardException | InvalidDeckException e) {
            throw new RuntimeException(e);
        }


        Move playSquirrel1 = new Move(game.getPlayer().getHand().get(0), 0, 0);
        try {
            playSquirrel1.executeMove(game);
        } catch (InvalidBoardException | InvalidPlayerException | InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        Move playStuntedWolf = new Move(game.getPlayer().getHand().get(0), 0, 0);
        try {
            playStuntedWolf.executeMove(game);
        } catch (InvalidBoardException | InvalidPlayerException | InvalidMoveException e) {
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
        try {
            game.playerDrawsSquirrel();
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }

        System.out.println(CardDisplayer.displayGame(game));
        System.out.println(CardDisplayer.displayCards(game.getPlayer().getDeck().getCurrentCards()));
        MoveSequence[] bestSequences = MoveSequenceEvaluator.findBestMoveSequenceTwoTurns(game);

        for (MoveSequence sequence : bestSequences) {
            System.out.println("SEQUENCE:");
            if (sequence != null) {
                for (Move move : sequence.getSequence()) {
                    System.out.println(CardDisplayer.displayMove(move));
                }
            }
        }
    }
}