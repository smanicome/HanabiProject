package fr.umlv.hanabi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model of the board, stores lists for each color of cards
 */
public class Board {
    private final HashMap<CardColor, List<Card>> fireworks;

    /**
     * Initialize board by creating Hashmap<CardColor, List>
     */
    public Board() {
        fireworks = new HashMap<CardColor, List<Card>>();
        for (CardColor cardColor : CardColor.values()) {
            fireworks.put(cardColor, new ArrayList<Card>());
        }
    }

    /**
     * Puts a card in the given list
     * @param card Card to put
     * @param cardColor In which CardColor list to put the card
     * @return boolean, true if put correctly, false other wise (not the right color or number)
     */
    public boolean putCard(Card card, CardColor cardColor) {
        if (card == null || cardColor == null) {
            throw new IllegalArgumentException("Either arguments must not be null");
        }

        // the player cannot see his own cards, therefore cannot know for sure their colors
        // except by a partner's tip
        if (!(card.getColor().equals(cardColor)))
            return false;

        List<Card> fwk = fireworks.get(cardColor);
        if (fwk.isEmpty()) {
            if (card.getValue() == 1) {
                fwk.add(card);
                return true;
            } else {
                return false;
            }
        } else {
            Card last = fwk.get(fwk.size() - 1);
            if (card.getValue() <= last.getValue()) {
                return false;
            } else {
                fwk.add(card);
                return true;
            }
        }
    }

    /**
     * Computes the current score
     * @return score, sum of each list's last card's value
     */
    public int getScore() {
        int score = 0;

        for (List<Card> fwk : fireworks.values()) {
            if (fwk.isEmpty()) continue;
            Card last = fwk.get(fwk.size() - 1);
            score += last.getValue();
        }

        return score;
    }
}