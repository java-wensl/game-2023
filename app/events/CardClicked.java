package events;


import com.fasterxml.jackson.databind.JsonNode;

import New.Resetboard;
import structures.basic.ShowScale;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Board;
import structures.basic.Card;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a card.
 * The event returns the position in the player's hand the card resides within.
 * 
 * { 
 *   messageType = “cardClicked”
 *   position = <hand index position [1-6]>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class CardClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		int handPosition = message.get("position").asInt();
		
		//Congzhou Yi
		//Clear all the colored tile on the board.
		ShowScale.cancellScale(out,TileClicked.coloredTiles);

		//Yunzhen Li
		//If the clicked position is not highlighted and no unit on it, check if mana is enough to play this card. If enough, then highlighted the position and the card.
		//if a hand position is already highlighted, if the clicked position is same as the hand position, cancel the highlighted. If not the same position, highlighted
		//highlighted the clicked position.
		if(gameState.isCardHighLighted()==false) {
			Card card = Resetboard.playerHand.get(handPosition-1);
			if(card.getManacost()<=Resetboard.humanplayer.getMana()) {
				BasicCommands.drawCard(out, card,handPosition, 1);
				gameState.setCardHighLighted(true);
				gameState.setCardHightLightPosition(handPosition);
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
			}
			
		}else if(gameState.isCardHighLighted()==true && gameState.getCardHightLightPosition() == handPosition) {
			Card card = Resetboard.playerHand.get(handPosition-1);
			BasicCommands.drawCard(out, card, handPosition, 0);
			gameState.setCardHighLighted(false);
			gameState.setCardHightLightPosition(0);
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}	
		}else if(gameState.isCardHighLighted() == true && gameState.getCardHightLightPosition()!= handPosition) {
			Card card1 = Resetboard.playerHand.get(gameState.getCardHightLightPosition()-1);
			BasicCommands.drawCard(out, card1, gameState.getCardHightLightPosition(), 0);
			gameState.setCardHighLighted(false);
			gameState.setCardHightLightPosition(0);
			
			Card card2 = Resetboard.playerHand.get(handPosition-1);
			BasicCommands.drawCard(out, card2, handPosition, 1);
			gameState.setCardHighLighted(true);
			gameState.setCardHightLightPosition(handPosition);
		}
		
	}

}
