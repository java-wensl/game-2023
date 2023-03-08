package structures.basic;

import java.util.ArrayList;
import java.util.List;

import structures.basic.ShowScale;
import akka.actor.ActorRef;
import commands.BasicCommands;
import events.TileClicked;
import utils.BasicObjectBuilders;

public class Board {
    static ArrayList<Tile> tileList = new ArrayList<>();

    public static List generateBoard(ActorRef out) {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 4; j++) {
                Tile tile = BasicObjectBuilders.loadTile(i, j);
                BasicCommands.drawTile(out, tile, 0);
                tileList.add(tile);
            }
        }
        return tileList;
    }

	//Congzhou Yi
    public static Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > 8 || y > 4) {
            return null;
        }

        int pos = x * 5 + y;
        return tileList.get(pos);
    }

    public static void setTile(int x, int y,Tile tile) {
    	int pos = x * 5 + y;
    	tileList.set(pos,tile);

    }

	public static ArrayList<Tile> getTileList() {
		return tileList;
	}

	public static void setTileList(ArrayList<Tile> tileList) {
		Board.tileList = tileList;
	}

}
