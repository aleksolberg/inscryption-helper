package no.colfermentada;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Sigil;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.game.Game;
import no.colfermentada.moves.Move;
import no.colfermentada.moves.MoveEvaluator;
import no.colfermentada.utils.MoveValidator;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.populateStandardDeck();

        Game game = new Game(deck);
        game.playerDrawsSquirrel();
        game.playerDrawsFromDeckByIndex(0);
        game.playerDrawsFromDeckByIndex(0);


        Move move1 = MoveEvaluator.findBestSingleMoveSingleTurn(game);
        System.out.println(move1.displayMove());
        try {
            move1.executeMove(game);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        System.out.println(game.displayGame());

        Move move2 = MoveEvaluator.findBestSingleMoveSingleTurn(game);
        System.out.println(move2.displayMove());
    }
}