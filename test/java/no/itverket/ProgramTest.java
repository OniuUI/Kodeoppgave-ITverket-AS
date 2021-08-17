package no.itverket;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// Using Junit 5, Previous imports outdated.
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;

public class ProgramTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Deck deck;
    private Program game;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        game = new Program();
        deck = new Deck();
    }

    @Test
    public void shouldHave52Cards() {
        Assertions.assertEquals(52, deck.cards.size());
    }

    @Test
    public void shouldHave4DistinctSuits() {
        Assertions.assertEquals(4, deck.cards.stream().map(c -> c.suit).distinct().count());
    }
    @Test
    public void shouldGiveSymbolOnSpecialCard() {
        Assertions.assertEquals('J', game.is_special(deck.cards.get(10)));
    }

    @Test
    public void shouldPickARandomCard(){
        Assertions.assertNotEquals(1, game.pull_card());
    }

    @Test
    public void shouldAceShouldAjustRankToEleven(){
        Card card = game.deck.cards.get(0);
        game.is_ace(game.player, card);
        Assertions.assertEquals(11, card.rank);
    }

    @Test
    public void shouldSelectCorrectWinner(){
        game.player.score = 21;
        game.dealer.score = 20;
        game.select_winner(game.dealer,game.player);
        Assertions.assertEquals("You won, congratulations!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void shouldEndGameIfBust(){
        game.bust = true;
        game.select_winner(game.dealer,game.player);
        Assertions.assertEquals("Game has ended!", outputStreamCaptor.toString().trim());
    }

}