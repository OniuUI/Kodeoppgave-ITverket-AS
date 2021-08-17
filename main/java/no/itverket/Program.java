package no.itverket;

import java.util.Random;
import java.util.Scanner;

public class Program {
    final Deck deck = new Deck();

    Player player = new Player();
    Player dealer = new Player();

    boolean game_status = true;
    boolean bust = false;

    final Scanner scanner = new Scanner(System.in);

    /**
     * Allows the dealer to make a game move.
     * @param dealer Player object for the dealer.
     */
    private void dealer_move(Player dealer) {
        System.out.println("Dealer pulls a card: ");
        Card card = this.pull_card();
        String symbol = String.valueOf(card.rank);

        if (card.rank == 1 || card.rank >= 11) {
            symbol = String.valueOf(this.is_special(card));
            this.is_ace(dealer, card);
        }

        dealer.hand.add(card);
        dealer.score = dealer.hand.stream().map(x -> Math.min(x.rank, 13)).reduce(0, Integer::sum);
        System.out.printf("Dealer pulled %s %s. Dealer Total is %s%n", card.suit, symbol, dealer.score);
    }

    /**
     * Allows the player to select what he wants to do, hit or stand.
     * @param player Player object for the User.
     */
    private void play_move(Player player) {
        System.out.println("Your turn: Stand or Hit");
        final String read = scanner.nextLine();

        if (read.equals("Hit")) {
            Card card = this.pull_card();
            String symbol = String.valueOf(card.rank);

            if (card.rank == 1 || card.rank >= 11) {
                symbol = String.valueOf(this.is_special(card));
                this.is_ace(player, card);
            }
            player.hand.add(card);
            player.score = player.hand.stream().map(x -> Math.min(x.rank, 13)).reduce(0, Integer::sum);
            System.out.printf("Hit with %s %s. Total is %s%n", card.suit, symbol, player.score);

        } else if (read.equals("Stand")) {
            this.game_status = false;
        }
    }

    /**
     * Checks if the card has the rank 1, and determines if the rank should stay at 1 or be 11.
     * @param player Player object for dealer or user.
     * @param card Last card that was pulled by user or dealer.
     */
    public void is_ace(Player player, Card card) {
        if (card.rank == 1) {
            if (player.score + 11 <= 21) {
                card.rank = 11;
            }
        }
    }

    /**
     * Sets a letter based on card rank.
     * @param card Last card that was pulled by user or dealer.
     * @return returns the selected character that was chosen by card rank.
     */
    public char is_special(Card card) {
        return switch (card.rank) {
            case 1 -> 'A';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            default -> ' ';
        };
    }

    /**
     * Selects a random card from the deck.
     * @return returns a random card
     */
    public Card pull_card() {
        Random generator = new Random();
        int id = generator.nextInt(deck.cards.size());
        Card card = deck.cards.get(id);
        deck.cards.remove(id);
        return card;
    }

    /**
     * Runs the game loop, dictates the order that the game is played out.
     */
    public void game_logic() {
        this.dealer_move(this.dealer);

        while (this.game_status) {
            this.play_move(this.player);
            if (this.player.score > 21) {
                System.out.println("You have gone bust!");
                this.bust = true;
                break;
            }
        }
        while (this.dealer.score < 17 && !this.bust) {
            this.dealer_move(this.dealer);
            if (this.dealer.score > 21) {
                System.out.println("Dealer have gone bust!");
                this.bust = true;
                break;
            }
        }
        this.select_winner(this.dealer, this.player);
    }

    /**
     * Determines who won the game.
     * @param dealer Player object for Dealer
     * @param player Player object for User
     */
    public void select_winner(Player dealer, Player player) {
        if (!this.bust) {
            if (dealer.score == player.score) {
                System.out.println("It's a tie, can't always be a winner.");
            }
            if (player.score > dealer.score) {
                System.out.println("You won, congratulations!");
            } else {
                System.out.println("You lost, better luck next time!");
            }
        }else{
            System.out.println("Game has ended!");
        }
    }

    /**
     * Main loop.
     * @param args
     */
    public static void main(String[] args) {
        Program game = new Program();
        game.game_logic();
    }
}
