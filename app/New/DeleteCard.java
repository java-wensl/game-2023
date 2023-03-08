package New;

import commands.BasicCommands;
import akka.actor.ActorRef;

public class DeleteCard {
	
	public static void deleteCard(ActorRef out,int position) {
		
	for(int i=0;i<Resetboard.playerHand.size();i++) {
		BasicCommands.deleteCard(out, i+1);
	}

	Resetboard.playerHand.remove(position-1);
	Resetboard.setHandCardPosition(Resetboard.playerHand.size()+1);
	
	for(int i=0;i<Resetboard.playerHand.size();i++) {
		BasicCommands.drawCard(out, Resetboard.playerHand.get(i), i+1, 0);
	}
	
	try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
	
	}
}
