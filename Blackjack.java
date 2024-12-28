import java.util.*;

// Card class to represent a single card
class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

// Deck class to manage the deck of cards
class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}

// Player class to handle player actions and attributes
class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getValue() == 11) {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public void showHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Hand value: " + getHandValue());
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public String getName() {
        return name;
    }
}

// Main Blackjack game class
public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        // Initial deal
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("Your hand:");
        player.showHand();

        // Player's turn
        while (true) {
            System.out.print("Hit or Stand? (h/s): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("h")) {
                player.addCard(deck.drawCard());
                System.out.println("Your hand:");
                player.showHand();

                if (player.isBust()) {
                    System.out.println("You bust! Dealer wins.");
                    return;
                }
            } else if (choice.equalsIgnoreCase("s")) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'h' or 's'.");
            }
        }

        // Dealer's turn
        System.out.println("Dealer's hand:");
        dealer.showHand();

        while (dealer.getHandValue() < 17) {
            System.out.println("Dealer hits.");
            dealer.addCard(deck.drawCard());
            dealer.showHand();

            if (dealer.isBust()) {
                System.out.println("Dealer busts! You win.");
                return;
            }
        }

        // Determine winner
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (playerValue > dealerValue) {
            System.out.println("You win!");
        } else if (playerValue < dealerValue) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("It's a tie.");
        }
    }
}
