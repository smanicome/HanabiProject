package fr.umlv.hanabi;

import java.util.Scanner;

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

	private int getIndex(Scanner in){
		boolean correctInput = False;
		do{
			try{
				return in.nextInt();
			}catch(Exception e){
				View.displayText("Wrong input !\nEnter a number :");
			}
		}while(!correctInput);
	}

	private void TipsChoice(int playerIndex, Scanner in){
		int playerIndex = (0 == players.lastIndexOf()?1:0);
		if(players.size() > 2){
			View.displayText("Select a player :");
			playerIndex = getIndex(in);
		}
		/* TODO selection de valeur/couleur */
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean discard(Player p, Scanner in){
		View.displayText("Enter the index of your discarded card");
		lastDiscard = p.useCard(getIndex(in));
		reserve.addBlue();
		try{
			p.addCard(reserve.draw());
			return False;
		}catch(Exception e)
			return True;
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean play(Player p, Scanner in){
		View.displayText("Enter the index of your played card");
		try{
			if(!board.putCard(p.useCard(getIndex(in)))){
				reserve.addRed();
				View.displayText("Carte rejetée !\nAttention !");
			} else
				View.displayText("Carte acceptée !");
			View.displayBoard();
			return False;
		}catch(Exception e)
			return True;

	}

	private int playerChoice(Player p, Scanner in){
		String input;
		boolean correctInput;
		do{
			View.displayChoice(reserve);
			input = in.nextLine();
			correctInput = True;
			switch(input){ /* mettre input = in.nextLine() directement ici ?*/
				case "tips":
					tipsChoice(p, in);break;
				case "discard":
					discard(p, in);break;
				case "play":
					play(p, in);break;
				default:
					correctInput = False;
					View.displayText("Wrong input");
			}
		}while(correctInput != True);
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
		return board.score();
	}
}