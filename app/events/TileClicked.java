package events;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import New.DeleteCard;
import New.Resetboard;
import structures.basic.ShowScale;
import structures.basic.UnitControl;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Board;
import structures.basic.Card;
import structures.basic.Tile;
import structures.basic.Unit;
import utils.BasicObjectBuilders;
import utils.HumanUnitLoader;
import utils.OrderedCardLoader;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a tile.
 * The event returns the x (horizontal) and y (vertical) indices of the tile that was
 * clicked. Tile indices start at 1.
 * 
 * { 
 *   messageType = “tileClicked”
 *   tilex = <x index of the tile>
 *   tiley = <y index of the tile>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class TileClicked implements EventProcessor{
	
    static ArrayList<Tile> coloredTiles;
    Unit attacker;

    public void setSomething(boolean something) {
        this.something = something;
    }

    boolean something = true;

    HashMap<Tile, Tile> attackedUnit;

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		int tilex = message.get("tilex").asInt();
		int tiley = message.get("tiley").asInt();
		
		if (gameState.something) {

            Tile tile = Board.getTile(tilex, tiley);
            //Tile tile = BasicObjectBuilders.loadTile(tilex, tiley);

            //It checks if there is no unit on the tile and if one card in hand is highlighted.
            // If both conditions are met, it gets the ID of the highlighted card and uses it
            // to retrieve a Unit object from the HumanUnitLoader. The code then calls
            // unitControl.unitPlacement(out, unit, tilex, tiley) to place the unit on the
            // board and deletes the card from the player's hand.

            if (tile.canBeAttack()) {
                //Congzhou Yi
                ShowScale.cancellScale(out, coloredTiles);
                tile.setTileHighLighted(false);
                attacker.attack(out, attackedUnit, tile);
            }

            //If the tile already has a human unit and is not highlighted, the code calls
            // ShowScale.attackScale(out, tilex, tiley) to show the attack scale for that
            // tile. If the tile is already highlighted, the code calls
            // ShowScale.cancellScale(out, tilex, tiley) to cancel the highlight.
            //Congzhou Yi
            else if (tile.isHaveHumanUnit() && !tile.isTileHighLighted()) {
                //ShowScale.tileW(out, tile);
                coloredTiles = ShowScale.moveScale(out, tile);
                if (tile.getUnit().getAttackRound()>0){
                    if (tile.getUnit().getId() == 2 || tile.getUnit().getId() == 11){
                        attackedUnit = ShowScale.getAttackArea(out, tile);
                    }else {
                        attackedUnit = ShowScale.getAttackArea(out, coloredTiles);
                    }
                    for (Map.Entry<Tile, Tile> entry : attackedUnit.entrySet()) {
                        coloredTiles.add(entry.getKey());
                    }
                    attacker = tile.getUnit();
                }
                //ShowScale.attackScale(out, tilex, tiley);
                tile.setTileHighLighted(true);
            } else if (gameState.CardHighLighted && !tile.isHaveHumanUnit() && !tile.isHaveAiUnit()) {
                Card temp = Resetboard.playerHand.get(gameState.cardHightLightPosition - 1);
                int id = temp.getId();
                int attack = temp.getBigCard().getAttack();
                int health = temp.getBigCard().getHealth();
                Unit unit = HumanUnitLoader.getHumanPlayerUnits().get(id);
                if (id == 7 || id == 17) {
                    unit.setAttackRound(2);
                } else {
                    unit.setAttackRound(1);
                }
                unit.setAttack(attack);
                unit.setHealth(health);
                //Congzhou Yi
                UnitControl.unitPlacement(out, unit, tilex, tiley);
                DeleteCard.deleteCard(out, gameState.cardHightLightPosition);
                Resetboard.humanplayer.setMana(Resetboard.humanplayer.getMana()-temp.getManacost());
                BasicCommands.setPlayer1Mana(out, Resetboard.humanplayer);
        		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
                
                gameState.CardHighLighted = false;
                gameState.setCardHightLightPosition(0);
                tile.setHaveHumanUnit(true);
            } else {
                //if(tile.isHaveHumanUnit() == true&& tile.isTileHighLighted()==true)
                ShowScale.cancellScale(out, coloredTiles);
                tile.setTileHighLighted(false);
            }

        }
		
//		if (gameState.something == true) {
//			
//			Tile tile=Board.getTile(tilex, tiley);
//			
//			//If there is no unit on this tile and one card in hand is highlighted.
//			if(gameState.CardHighLighted == true && tile.isHaveHumanUnit()==false && tile.isHaveAiUnit()==false) {
//				
//				int id = Resetboard.playerHand.get(gameState.cardHightLightPosition-1).getId();
//				Unit unit = HumanUnitLoader.getHumanPlayerUnits().get(id);
//				UnitControl.unitPlacement(out, unit, tilex, tiley);
//				DeleteCard.deleteCard(out, gameState.cardHightLightPosition);
//				gameState.CardHighLighted = false;
//				gameState.setCardHightLightPosition(0);	
//				tile.setHaveHumanUnit(true);
//			}
//			}else if(tile.isHaveHumanUnit() == true)
//		
//		}	
		
	}

}
