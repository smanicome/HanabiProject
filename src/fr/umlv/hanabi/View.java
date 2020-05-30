package fr.umlv.hanabi;

import java.util.List;
import java.util.Objects;

public class View {
    public static void displayChoice(Reserve r) {
        Objects.requireNonNull(r);

        System.out.println("1) Engage a card");

        if (r.getBlueTokens() > 0) {
            System.out.println("2) Give a tips");
        }
        if (r.getBlueTokens() < 8) {
            System.out.println("3) Discard");
        }
    }

    public static void displayBoard(Board b) {
        System.out.println(Objects.requireNonNull(b).toString());
    }

    public static void displayHand(Player p) {
        System.out.println(Objects.requireNonNull(p));
    }

    public static void displayReserve(Reserve r) {
        Objects.requireNonNull(r);
        StringBuilder builder = new StringBuilder();

        builder.append("Blue tokens: ");
        builder.append(r.getBlueTokens());
        builder.append("\nRed Tokens: ");
        builder.append(r.getRedTokens());
        builder.append("\nDeck: ");
        builder.append(r.getRemainingCards());

        System.out.println(builder.toString());
    }

    private static void displayTips(List<Integer> indexes, StringBuilder builder) {
        builder.append(" : ");

        for (int i : indexes) {
            builder.append(i);
            builder.append(" ");
        }

        System.out.println(builder.toString());
    }

    public static void displayTips(CardColor cardColor, Player p) {
        Objects.requireNonNull(cardColor);
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Cards with color ");
        builder.append(cardColor.toString());

        displayTips(p.getIndexOfCardsByCardColor(cardColor), builder);
    }

    public static void displayTips(int value, Player p) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Value must be between 1 and 5 included");
        }
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Cards with value ");
        builder.append(value);

        displayTips(p.getIndexOfCardsByValue(value), builder);
    }

    public static void displayText(String text) {
        System.out.println(text);
    }

    public static void displayDiscarded(Game g) {
        Card c = Objects.requireNonNull(g).getDiscarded();
        if (c == null) {
            System.out.println("No discarded card");
        } else {
            System.out.println(
                "Last discarded card : "
                + c.toString()
            );
        }
    }
}
