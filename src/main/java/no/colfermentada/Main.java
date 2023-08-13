package no.colfermentada;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.populateStandardDeck();
        Board board = new Board(deck);

        // Setup initial state
        board.drawSquirrel();
        board.drawFromDeck(1);
        board.drawFromDeck(0);

        Card sparrow = new Card("Sparrow", "Sparrow", 2, 1, CostType.Blood, 1, Tribe.Avian);
        Card elk = new Card("Elk", "Elk", 4, 2, CostType.Blood, 2, Tribe.Hooved);
        board.opponentPlaysCard(sparrow, 2);
        board.opponentPlaysCard(elk, 3);

        System.out.println(board.displayBoard());
        board.playCard(0, 3);
        System.out.println(board.displayBoard());
        board.playCard(0, 3, new int[]{3});
        System.out.println(board.displayBoard());

    }
}