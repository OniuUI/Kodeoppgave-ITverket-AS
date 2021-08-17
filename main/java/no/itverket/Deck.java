package no.itverket;

//import java.util.ArrayDeque;
import java.util.ArrayList;
//import java.util.Queue;

class Deck {
//    Queue<Card> cards;
    ArrayList<Card> cards = new ArrayList<>();

    Deck() {
//        cards = new ArrayDeque<>();
        for (Suit suit : Suit.values()) {
            for (int i = 1; i < 14; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }
}
