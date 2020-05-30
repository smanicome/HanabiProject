package fr.umlv.hanabi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 */
public class Reserve {
	private ArrayList<Card> deck;
	private int redTokens, blueTokens;

	public Reserve(){
		int i;
		this.redTokens = 0;
		this.blueTokens = 8;
		this.deck = new ArrayList<Card>();
		for(CardColor color : CardColor.values()){
			for(i = 0; i < 3; i++)
				this.deck.add(new Card(1, color));
			for(i = 0; i < 2; i++){
				this.deck.add(new Card(2, color));
				this.deck.add(new Card(3, color));
				this.deck.add(new Card(4, color));
			}
			this.deck.add(new Card(5, color));
		}
		Collections.shuffle(deck);
	}

	/**
	 * @return True if there are at least 3 red tokens in reserve.
	 */
	public boolean addRed(){
		redTokens += 1;
		return redTokens >= 3;
	}

	/**
	 * @return number of blue tokens in reserve.
	 */
	public int addBlue(){
		if(blueTokens < 8)
			blueTokens += 1;
		return blueTokens;
	}

	/**
	 * @return number of blue tokens in reserve.
	 */
	public int subBlue(){
		if(blueTokens > 0)
			blueTokens -= 1;
		return blueTokens;
	}

	/**
	 * @return Card extracted from the deck.
	 */
	public Card draw(){
		return deck.remove();
	}
}