package New;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Board;
import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitControl;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author wsl
 * @date 2023/3/6
 */
public class UnitMove {

    public static void move(ActorRef out, GameState gameState, JsonNode message) {

//        Tile tile1 = Board.getTile(2, 2);
//        BasicCommands.addPlayer1Notification(out, "UnitMove", 2);
//        Unit humanAvatar = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 40, Unit.class);
//        humanAvatar.setPositionByTile(tile1);
//        //Congzhou Yi
//        tile1.setHaveHumanUnit(true);
//        tile1.setUnit(humanAvatar);
//        humanAvatar.setHealth(20);
//        humanAvatar.setAttack(2);
//        BasicCommands.drawUnit(out, humanAvatar, tile1);
//        UnitControl.addHumanUnit(humanAvatar,humanNum);
        System.out.println("out 对象内容"+out.toString());
        System.out.println(gameState.toString());
        System.out.println("message 对象内容"+message.toString());

    }
}
