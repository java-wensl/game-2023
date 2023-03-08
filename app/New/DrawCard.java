package New;

import commands.BasicCommands;
import akka.actor.ActorRef;

public class DrawCard {
	
	public static void drawCard(ActorRef out) {
		if(Resetboard.getHandCardPosition()<7) {
		BasicCommands.addPlayer1Notification(out, "Draw one card", 20);
		BasicCommands.drawCard(out, Resetboard.player1Cards.get(Resetboard.getDeckOrder()), Resetboard.getHandCardPosition(), 0);
		Resetboard.playerHand.add(Resetboard.player1Cards.get(Resetboard.getDeckOrder()));
		Resetboard.setHandCardPosition(Resetboard.getHandCardPosition()+1);
		//Every time draw one card, deckOrder+1
		Resetboard.setDeckOrder(Resetboard.getDeckOrder()+1);
		
		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
		}else {	
		BasicCommands.drawCard(out, Resetboard.player1Cards.get(Resetboard.getDeckOrder()), Resetboard.getHandCardPosition(), 0);
//		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.deleteCard(out, Resetboard.getHandCardPosition());
		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
		
		Resetboard.setDeckOrder(Resetboard.getDeckOrder()+1);
		}
		
	}

}
