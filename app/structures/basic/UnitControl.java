package structures.basic;
import java.util.List;

import New.Resetboard;
import structures.basic.Tile;
import structures.basic.Unit;
import akka.actor.ActorRef;
import commands.BasicCommands;
import events.Initalize;
import utils.BasicObjectBuilders;
import utils.HumanUnitLoader;
import utils.OrderedCardLoader;

public class UnitControl {

    public static void unitPlacement(ActorRef out,Unit unit,int tilex,int tiley) {

        Tile tile = Board.getTile(tilex, tiley);
        tile.setUnit(unit);
        unit.setPositionByTile(tile);
        BasicCommands.drawUnit(out, unit, tile);
        addHumanUnit(unit,Resetboard.humanNum);

        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
        //Congzhou Yi
        BasicCommands.setUnitAttack(out, unit, unit.getAttack());
        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
        BasicCommands.setUnitHealth(out, unit, unit.getHealth());
        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

    }
    public static void unitAiPlacement(ActorRef out,Unit unit,int tilex,int tiley) {

        Tile tile = Board.getTile(tilex, tiley);
        tile.setUnit(unit);
        unit.setPositionByTile(tile);
        BasicCommands.drawUnit(out, unit, tile);
        BasicCommands.setUnitAttack(out, unit, unit.getAttack());
        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
        BasicCommands.setUnitHealth(out, unit, unit.getHealth());
        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

    }
	
//	public static void moveUnit(ActorRef out, Unit unit,int tilex, int tiley) {
//		Tile tile = BasicObjectBuilders.loadTile(tilex, tiley);
//		BasicCommands.moveUnitToTile(out, Resetboard.humanUnitBoard.get(0), tile);
//		Resetboard.humanUnitBoard.get(0).setPositionByTile(tile);
//
//		try {
//			Thread.sleep(400);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	

	
    public static void addHumanUnit(Unit unit, int position) {
        Resetboard.humanUnitBoard.add(new UnitPosition(unit, position));
        Resetboard.humanNum = Resetboard.humanNum +1 ;
    }
    
    public int getHumanPosition(Unit unit) {
    	for (UnitPosition unitPosition : Resetboard.humanUnitBoard) {
            if (unitPosition.getUnit() == unit) {
                return unitPosition.getPosition();
            }
        }
        return -1;
    }
    
    public static void addAiUnit(Unit unit, int position) {
        Resetboard.aiUnitBoard.add(new UnitPosition(unit, position));
    }
    
    public int getAiPosition(Unit unit) {
    	for (UnitPosition unitPosition : Resetboard.aiUnitBoard) {
            if (unitPosition.getUnit() == unit) {
                return unitPosition.getPosition();
            }
        }
        return -1;
    }
    
//	 public static Unit setCardToUnit(int unitId) {
//		  
//		  List<Card> player1Cards = OrderedCardLoader.getPlayer1Cards();
//		  Card card = player1Cards.get(unitId);
//		  
//		  List<Unit> humanPlayerUnits = HumanUnitLoader.getHumanPlayerUnits();
//		  Unit unit = humanPlayerUnits.get(unitId);
//		  
//		  BigCard bigCard = card.getBigCard();
//		  int attack2 = bigCard.getAttack();
//		  int health2 = bigCard.getHealth();
//		  
//		  unit.setAttack(attack2);
//		  unit.setHealth(health2);
//		  
//		  return unit;
//		 }
    
}
