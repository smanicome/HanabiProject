package fr.umlv.hanabi;

/**
 - Model of a card, composed of a color and a value
 */
public class Card {
	int value;
	CardColor color;

	public Card(int val, CardColor color){
		this.value = val;
		this.color = color;
		if(val < 1 || val > 5)
			throw new IllegalArgumentException();
	}

	int getValue(){
		return value;
	}

	CardColor getColor(){
		return color;
	}

	@Override
	public String toString(){
		return color.toString() + Integer.toString(value);
	}
}