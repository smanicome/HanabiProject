package fr.umlv.hanabi;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Model of a player, basically a list of Card
 */
public class Player {
    private final ArrayList<Card> cards;

    /**
     * @param cards Initial hand (Must not be null)
     */
    public Player(ArrayList<Card> cards) {
        this.cards = Objects.requireNonNull(cards);
    }

    /**
     * @param i index of the card int player's hand
     * @return card of given index in hand
     * throws IllegalArgumentException if i exceeds boundaries
     */
    public Card useCard(int i) {
        if (i < 0 || i > cards.size() - 1) {
            throw new IllegalArgumentException("Index out of bound");
        }

        return cards.remove(i);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Player{ ");
        for(Card card : cards) {
            builder.append(card.toString());
            builder.append(' ');
        }
        builder.append("}");

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(cards, player.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }
}