package structures.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import commands.BasicCommands;
import structures.basic.Tile;
import utils.BasicObjectBuilders;
import akka.actor.ActorRef;

public class ShowScale {

    //Congzhou Yi
    public static ArrayList<Tile> moveScale(ActorRef out, Tile tile) {
        ArrayList<Tile> targetTiles = new ArrayList<>();
        ArrayList<Tile> uniqueList = new ArrayList<>();
        int x = tile.getTilex();
        int y = tile.getTiley();

        if (!isUnitOnTile(Board.getTile(x, y + 1))) {
            targetTiles.addAll(getmoveScale(Board.getTile(x, y + 1)));
            targetTiles.add(Board.getTile(x, y + 1));
        }
        if (!isUnitOnTile(Board.getTile(x, y - 1))) {
            targetTiles.addAll(getmoveScale(Board.getTile(x, y - 1)));
            targetTiles.add(Board.getTile(x, y - 1));
        }
        if (!isUnitOnTile(Board.getTile(x + 1, y))) {
            targetTiles.addAll(getmoveScale(Board.getTile(x + 1, y)));
            targetTiles.add(Board.getTile(x + 1, y));
        }
        if (!isUnitOnTile(Board.getTile(x - 1, y))) {
            targetTiles.addAll(getmoveScale(Board.getTile(x - 1, y)));
            targetTiles.add(Board.getTile(x - 1, y));
        }

        for (Tile t : targetTiles) {
            if (!uniqueList.contains(t)) {
                uniqueList.add(t);
                BasicCommands.drawTile(out, t, 1);
            }
        }
        return uniqueList;
    }

    public static ArrayList<Tile> getmoveScale(Tile tile) {
        int scaleX = tile.getTilex();
        int scaleY = tile.getTiley();
        ArrayList<Tile> targetTiles = new ArrayList<>();

        if (!isUnitOnTile(Board.getTile(scaleX + 1, scaleY))) {
            targetTiles.add(Board.getTile(scaleX + 1, scaleY));
        }
        if (!isUnitOnTile(Board.getTile(scaleX - 1, scaleY))) {
            targetTiles.add(Board.getTile(scaleX - 1, scaleY));
        }
        if (!isUnitOnTile(Board.getTile(scaleX, scaleY + 1))) {
            targetTiles.add(Board.getTile(scaleX, scaleY + 1));
        }
        if (!isUnitOnTile(Board.getTile(scaleX, scaleY - 1))) {
            targetTiles.add(Board.getTile(scaleX, scaleY - 1));
        }
        return targetTiles;
    }

    public static boolean isUnitOnTile(Tile tile) {
        if (tile == null){
            return true;
        }
        if (tile.isHaveAiUnit()) {
            return true;
        }
        return false;
    }
    //Congzhou Yi

    public static void attackScale(ActorRef out, int x, int y) {
        ShowScale showScale = new ShowScale();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (i < 2) {
                    Tile tile = Board.getTile(x + i, y + j);
                    BasicCommands.drawTile(out, tile, showScale.getcolor(x + i, y + j));
                    Tile tile2 = Board.getTile(x - i, y - j);
                    BasicCommands.drawTile(out, tile2, showScale.getcolor(x - i, y - j));
                    Tile tile3 = Board.getTile(x + i, y - j);
                    BasicCommands.drawTile(out, tile3, showScale.getcolor(x - i, y - j));
                    Tile tile4 = Board.getTile(x - i, y + j);
                    BasicCommands.drawTile(out, tile4, showScale.getcolor(x - i, y - j));
                }
            }
        }
    }

    public int getcolor(int x, int y) {
        if (x < 0 || y < 0 || x > 8 || y > 4) {
            return 0;
        } else {
            Tile tile = Board.getTile(x, y);
            if (tile.isHaveAiUnit() == true) {
                return 2;
            } else {
                return 1;
            }
        }
    }
    
    public static int getTarget(int x,int y) {
        if (x < 0 || y < 0 || x > 8 || y > 4) {
            return 0;
        } else {
            Tile tile = Board.getTile(x, y);
            if (tile.isHaveAiUnit() == true) {
                return 2;
            } else {
                return 0;
            }
        }
    }
    //如果是玩家回合，则把所有敌方单位显示为红色
    public static void boardAttackTarget(ActorRef out) {
  
    	for(int i = 0;i < 9;i++) {
    		for(int j = 0;j < 5;j++) {
    			Tile tile = Board.getTile(i, j);
    			BasicCommands.drawTile(out, tile, getTarget(i, j));
    		}
    	}
    }
    	


    public static void cancellScale(ActorRef out, ArrayList<Tile> tiles) {
        if (tiles != null) {
            for (Tile i : tiles) {
                BasicCommands.drawTile(out, i, 0);
                i.setCanBeAttack(false);
            }
        }
    }

    public static ArrayList<Tile> getRound(Tile tile) {

        int tilex = tile.getTilex();
        int tiley = tile.getTiley();
        ArrayList<Tile> targetTiles = new ArrayList<>();
        targetTiles.add(Board.getTile(tilex, tiley));
        targetTiles.add(Board.getTile(tilex - 1, tiley));
        targetTiles.add(Board.getTile(tilex - 1, tiley - 1));
        targetTiles.add(Board.getTile(tilex, tiley - 1));
        targetTiles.add(Board.getTile(tilex - 1, tiley + 1));
        targetTiles.add(Board.getTile(tilex, tiley + 1));
        targetTiles.add(Board.getTile(tilex + 1, tiley));
        targetTiles.add(Board.getTile(tilex + 1, tiley - 1));
        targetTiles.add(Board.getTile(tilex + 1, tiley + 1));
        return removeOutTile(targetTiles);
    }

    public static HashMap<Tile, Tile> getAttackArea(ActorRef out, ArrayList<Tile> tiles) {
        boolean flag = true;
        HashMap<Tile, Tile> attackedUnit = new HashMap<Tile, Tile>();
        for (Tile i : tiles) {
            ArrayList<Tile> temp = getRound(i);
            for (Tile j : temp) {
                if (j.isHaveAiUnit()) {
                    for (Map.Entry<Tile, Tile> entry : attackedUnit.entrySet()) {
                        if (entry.getKey() == j) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        attackedUnit.put(j, i);
                        flag = true;
                    }
                }
            }
        }
        for (Map.Entry<Tile, Tile> entry : attackedUnit.entrySet()) {
            BasicCommands.drawTile(out, entry.getKey(), 2);
            entry.getKey().setCanBeAttack(true);
        }
        return attackedUnit;
    }

    public static HashMap<Tile, Tile> getAttackArea(ActorRef out, Tile tile) {
        HashMap<Tile, Tile> attackedUnit = new HashMap<Tile, Tile>();
        for (Tile i : Board.tileList) {
            if (i.isHaveAiUnit()){
                attackedUnit.put(i, tile);
            }
        }
        for (Map.Entry<Tile, Tile> entry : attackedUnit.entrySet()) {
            BasicCommands.drawTile(out, entry.getKey(), 2);
            entry.getKey().setCanBeAttack(true);
        }
        return attackedUnit;
    }

    public static HashMap<Tile, Tile> getAttackHumanArea(ActorRef out, Tile tile) {
        HashMap<Tile, Tile> attackedUnit = new HashMap<Tile, Tile>();
        for (Tile i : Board.tileList) {
            if (i.isHaveHumanUnit()){
                attackedUnit.put(i, tile);
            }
        }
        for (Map.Entry<Tile, Tile> entry : attackedUnit.entrySet()) {
            BasicCommands.drawTile(out, entry.getKey(), 2);
            entry.getKey().setCanBeAttack(true);
        }
        return attackedUnit;
    }

    public static ArrayList<Tile> removeOutTile(ArrayList<Tile> targetTiles) {
        for (int i = targetTiles.size() - 1; i > -1; i--) {
            if (targetTiles.get(i) == null) {
                targetTiles.remove(targetTiles.get(i));
            } else if (targetTiles.get(i).getTilex() < 0 || targetTiles.get(i).getTilex() > 8
                    || targetTiles.get(i).getTiley() < 0 || targetTiles.get(i).getTiley() > 4) {
                targetTiles.remove(targetTiles.get(i));
            }
        }
        return targetTiles;
    }

}
