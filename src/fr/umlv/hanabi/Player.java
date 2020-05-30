package fr.umlv.hanabi;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final ArrayList<Card> cards;

    public Player(ArrayList<Card> cards) {
        this.cards = Objects.requireNonNull(cards);
    }

    public void displayCards() {
        StringBuilder builder = new StringBuilder();

        for(Card card : cards) {
            builder.append(card.toString());
            builder.append(' ');
        }

        System.out.println(builder.toString());
    }

    public Card useCard(int i) {
        if (i < 0 || i > cards.size() - 1) {
            throw new IllegalArgumentException("Index out of bound");
        }

        return cards.remove(i);
    }
}