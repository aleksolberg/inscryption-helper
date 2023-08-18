package no.colfermentada;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Sigil;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.game.Game;
import no.colfermentada.moves.Move;
import no.colfermentada.utils.MoveValidator;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.populateStandardDeck();

        Game game = new Game(deck);
        game.playerDrawsSquirrel();
        game.playerDrawsFromDeckByIndex(0);

        Move move = new Move(game.getPlayer().getHand().get(0), 1);
        System.out.println(MoveValidator.isValidMove(move, game));


    }
}