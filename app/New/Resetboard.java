package New;

import java.util.ArrayList;
import java.util.List;

import commands.BasicCommands;
import akka.actor.ActorRef;
import structures.basic.Board;
import structures.basic.Card;
import structures.basic.Player;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitControl;
import structures.basic.UnitPosition;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.OrderedCardLoader;

public class Resetboard {
	public static List<Card> playerHand = new ArrayList<>();
	public static List<UnitPosition> humanUnitBoard = new ArrayList<>();
	public static List<UnitPosition> aiUnitBoard = new ArrayList<>();

	static Unit humanAvatar = null;
	private static int deckOrder = 0;//玩家位置，点卡，调用position，tilex，tiley，tilex-positionx绝对值+取绝对值《=2，大于0，不能重叠，
	static int handCardPosition = 1;
	static int startCardNum = 3;
	
	public static int humanNum = 0;
	static int aiNum = 0;
	
	public static List<Card> player1Cards = OrderedCardLoader.getPlayer1Cards();

	public static int getDeckOrder() {
		return deckOrder;
	}

	public static void setDeckOrder(int deckOrder) {
		Resetboard.deckOrder = deckOrder;
	}

	public static int getHandCardPosition() {
		return handCardPosition;
	}

	public static void setHandCardPosition(int handCardPosition) {
		Resetboard.handCardPosition = handCardPosition;
	}

	public static List<Card> player2Cards = OrderedCardLoader.getPlayer2Cards();
	
	public static Player humanplayer = new Player(20, 0);
	public static Player aiplayer = new Player(20, 0);

	public static void ResetDemo(ActorRef out) {

		Board.generateBoard(out);

		BasicCommands.addPlayer1Notification(out, "Reset Health of Player1", 2);
		BasicCommands.setPlayer1Health(out, humanplayer);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		BasicCommands.addPlayer1Notification(out, "Reset Mana of HumanPlayer", 2);
		humanplayer.setMana(humanplayer.getManamax());
		BasicCommands.setPlayer1Mana(out, humanplayer);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		BasicCommands.addPlayer1Notification(out, "Reset Health of AIPlayer", 2);
		BasicCommands.setPlayer2Health(out, aiplayer);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		BasicCommands.addPlayer1Notification(out, "Reset Mana of AIPlayer", 2);
		aiplayer.setMana(aiplayer.getManamax());
		BasicCommands.setPlayer2Mana(out, aiplayer);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// place Human avatar
		Tile tile1 = Board.getTile(1, 2);
		BasicCommands.addPlayer1Notification(out, "drawUnit", 2);
		humanAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 40, Unit.class);
		humanAvatar.setPositionByTile(tile1);
		//Congzhou Yi
		tile1.setHaveHumanUnit(true);
		tile1.setUnit(humanAvatar);
		humanAvatar.setHealth(20);
		humanAvatar.setAttack(2);
		BasicCommands.drawUnit(out, humanAvatar, tile1);
		UnitControl.addHumanUnit(humanAvatar,humanNum);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// set Attack
		BasicCommands.addPlayer1Notification(out, "setUnitAttack",2);
		BasicCommands.setUnitAttack(out, humanAvatar, 2);

		// setUnitHealth
		BasicCommands.addPlayer1Notification(out, "setUnitHealth", humanplayer.getHealth());
		BasicCommands.setUnitHealth(out, humanAvatar, humanplayer.getHealth());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// place AI avatar
		Tile tile2 = Board.getTile(7, 2);
		BasicCommands.addPlayer1Notification(out, "drawUnit", 2);
		Unit aiAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 41, Unit.class);
		//Congzhou Yi
		tile2.setUnit(aiAvatar);
		aiAvatar.setPositionByTile(tile2);
		aiAvatar.setHealth(aiplayer.getHealth());
		aiAvatar.setHealth(20);
		aiAvatar.setAttack(2);
		
		BasicCommands.drawUnit(out, aiAvatar, tile2);
		UnitControl.addAiUnit(aiAvatar,aiNum);
		tile2.setHaveAiUnit(true);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		BasicCommands.addPlayer1Notification(out, "setUnitAttack", 2);
		BasicCommands.setUnitAttack(out, aiAvatar, 2);

		// setUnitHealth
		BasicCommands.addPlayer1Notification(out, "setUnitHealth", 20);
		BasicCommands.setUnitHealth(out, aiAvatar, aiplayer.getHealth());
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<startCardNum;i++) {
			DrawCard.drawCard(out);
		}

	}


	public static void highLightTiles(ArrayList<Tile> tiles, ActorRef out) {
		for (Tile i : tiles) {
			BasicCommands.drawTile(out, i, 1);
		}
	}


}
