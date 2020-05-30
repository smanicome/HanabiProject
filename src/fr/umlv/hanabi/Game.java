package fr.umlv.hanabi;

import java.util.List;
import java.util.Scanner;

import static fr.umlv.hanabi.CardColor.*;

public class Game {
	private final Reserve reserve;
	private final Board board;
	private final List<Player> players;
	private Card lastDiscard;

	public Game(){
		this.reserve = new Reserve();
		this.board = new Board();
		Player p1 = new Player(reserve.draw(5));
		Player p2 = new Player(reserve.draw(5));
		this.players = List.of(p1, p2);
	}

	private int selection(Scanner in, int min, int max, Integer... excepts){
		boolean correctInput = false;
		List<Integer> exceptions = List.of(excepts);
		int value = -1;

		do{
			try{
				value = in.nextInt();
				correctInput = !exceptions.contains(value) && value >= min && value <= max;

				if (!correctInput) {
					View.displayIndexError(min, max, exceptions);
				}
			}catch(Exception e){
				View.displayText("Wrong input !\nEnter a number :");
			}
		} while(!correctInput);

		return value;
	}

	/**
	 * Lets the user choose which tip he want to give, he is allowed to be dumb
	 * @param playerIndex Current turn's player index
	 * @param in Scanner on standard input
	 */
	private void tipsChoice(int playerIndex, Scanner in){
		int targetPlayer;
		
		if(players.size() > 2){
			View.displayText("Select a player :");
			View.displayPlayers(players, playerIndex);
			targetPlayer = selection(in, 0, players.size() - 1, playerIndex);
		} else {
			targetPlayer = (0 == playerIndex ? 1 : 0);
		}
		
		View.displayValueOrColor();
		View.displayText("Choose which: ");

		switch (selection(in, 1, 2)) {
			case 1:
				View.displayValues(players.get(targetPlayer).getValues());
				View.displayTips(selection(in, 1, 5), players.get(playerIndex));
				break;
			case 2:
				View.displayColors(players.get(targetPlayer).getColors());
				View.displayTips(selection(in, 1, CardColor.values().length), players.get(playerIndex));
		}
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean discard(Player p, Scanner in){
		View.displayText("Enter the index of the card you want to");
		View.displayHand(p);
		lastDiscard = p.useCard(selection(in, 0, p.getHandSize() -1));
		reserve.addBlue();
		try{
			p.addCard(reserve.draw());
			return false;
		}catch(Exception e) {
			return true;
		}
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean play(Player p, Scanner in){
		View.displayText("Enter the index of your played card");

		View.displayHand(p);
		Card played = p.useCard(selection(in, 0, p.getHandSize() - 1));
		View.displayCardColors();
		CardColor cardColor = switch (selection(in, 1, CardColor.values().length)) {
			case 1 -> Red;
			case 2 -> Green;
			case 3 -> Blue;
			case 4 -> White;
			case 5 -> Yellow;
			default -> null; //never happens
		};

		if(!board.putCard(played, cardColor)){
			reserve.addRed();
			View.displayText("Carte rejetée !\nAttention !");
		} else {
			View.displayText("Carte acceptée !");
		}

		View.displayBoard(board);
		try {
			p.addCard(reserve.draw());
			return false;
		} catch(Exception e) {
			return true;
		}

	}

	public Card getDiscarded() {
		return lastDiscard;
	}

	private void playerChoice(Player p, Scanner in){
		int input;
		boolean correctInput;
		do{
			View.displayChoice(reserve);
			input = in.nextInt();
			correctInput = true;
			switch(input){ /* mettre input = in.nextLine() directement ici ?*/
				case 1:
					play(p, in);break;
				case 2:
					tipsChoice(players.indexOf(p), in);break;
				case 3:
					discard(p, in);break;
				default:
					correctInput = false;
					View.displayText("Wrong input");
			}
		}while(!correctInput);
	}

	/**
	 * @return the score when the game ends
	 */
	public int gameLoop(){
		Scanner in = new Scanner(System.in);
		while(!board.isFilled()){
			for(Player p : players){
				playerChoice(p, in);
			}
		}

		View.displayText("Score: " + board.getScore());
		return board.getScore();
	}
}