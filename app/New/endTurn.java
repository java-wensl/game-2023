package New;


import actors.GameActor;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.AiUnitLoader;
import utils.OrderedCardLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//xia
public class endTurn {

    private static Map<Integer, Unit> aiUnitMap;

    public static void endTurnClicked(ActorRef out, GameState gameState, JsonNode message) {
        if (!GameActor.getGameState().something) {
            BasicCommands.addPlayer2Notification(out, "Game over", 2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        DrawCard.drawCard(out);

        BasicCommands.addPlayer1Notification(out, "Lost rest mana", 20);
        Resetboard.humanplayer.setMana(0);

        BasicCommands.setPlayer1Mana(out, Resetboard.humanplayer);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //xia
        if (Resetboard.humanplayer.getManamax() < 9) {
            Resetboard.humanplayer.setManamax(Resetboard.humanplayer.getManamax() + 1);
        }
        if (Resetboard.aiplayer.getManamax() < 9) {
            Resetboard.aiplayer.setManamax(Resetboard.aiplayer.getManamax() + 1);
        }

        BasicCommands.addPlayer1Notification(out, "Reset Mana of AIPlayer", 2);
        Resetboard.aiplayer.setMana(Resetboard.aiplayer.getManamax());
        BasicCommands.setPlayer2Mana(out, Resetboard.aiplayer);

        Resetboard.humanplayer.setMana(Resetboard.humanplayer.getManamax());
        BasicCommands.addPlayer1Notification(out, "Get Mana", 2);
        BasicCommands.setPlayer1Mana(out, Resetboard.humanplayer);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (UnitPosition i : Resetboard.humanUnitBoard) {
            if (i.getUnit().getId() == 7 || i.getUnit().getId() == 17) {
                i.getUnit().setAttackRound(2);
            } else {
                i.getUnit().setAttackRound(1);
            }
            i.getUnit().setMoveRound(1);
        }

        List<Tile> aiTileList = new ArrayList<>();
        for (Tile tile : Board.getTileList()) {
            Unit unit = tile.getUnit();
            if (null != unit && tile.isHaveAiUnit()) {
                aiTileList.add(tile);
            }
        }
        if (aiTileList.size() == 0) {
            return;
        }
        Map<String, Position> positionMap = new HashMap<>();
        for (Tile tile : aiTileList) {
            if (!GameActor.getGameState().something) {
                BasicCommands.addPlayer2Notification(out, "Game over", 2);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            Unit unit = tile.getUnit();
            Tile newTile = moveTo(unit.getPosition().getTilex(), unit.getPosition().getTiley());
            if (null != newTile && null == newTile.getUnit()) {
                newTile.setHaveAiUnit(true);
                tile.getUnit().move(out, newTile);
                buildPosition(positionMap, newTile.getTilex(), newTile.getTiley());
            }
            if (unit.getId() == 27 || unit.getId() == 37) {
                unit.setAttackRound(2);
            } else {
                unit.setAttackRound(1);
            }

            HashMap<Tile, Tile> attackMap = ShowScale.getAttackHumanArea(out, newTile);
            System.out.println("----场上敌方单位：" + attackMap.size());
            for (Tile t : attackMap.keySet()) {
                if (t.canBeAttack() && unit.getAttackRound() > 0 && null != t.getUnit()) {
                    unit.attackHum(out, t);
                }
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buildCard(out, positionMap);
        BasicCommands.addPlayer2Notification(out, "It's your turn", 2);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void buildCard(ActorRef out, Map<String, Position> positionMap) {
        int mana = Resetboard.aiplayer.getMana();
        for (Card card : OrderedCardLoader.getPlayer2Cards()) {
            if ("c_entropic_decay".equals(card.getCardname()) || "c_staff_of_ykir".equals(card.getCardname())) {
                continue;
            }
            if (card.getManacost() < mana) {
                Unit cardUnit = cardToUnit(card);
                if (null == cardUnit) {
                    continue;
                }
                List<String> list = new ArrayList<>(positionMap.keySet());
                for (String k : list) {
                    String key = list.get((int) (Math.ceil(Math.random() * (list.size() - 1))));
                    Position value = positionMap.get(key);
                    if (null == value) {
                        continue;
                    }
                    Tile tile = Board.getTile(value.getTilex(), value.getTiley());
                    if (null != tile && null == tile.getUnit()) {
                        tile.setUnit(cardUnit);
                        cardUnit.setPositionByTile(tile);
                        BasicCommands.drawUnit(out, cardUnit, tile);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BasicCommands.setUnitAttack(out, cardUnit, cardUnit.getAttack());
                        BasicCommands.setUnitHealth(out, cardUnit, cardUnit.getHealth());
                        tile.setHaveAiUnit(true);

                        mana -= card.getManacost();
                        Resetboard.aiplayer.setMana(mana);
                        BasicCommands.setPlayer2Mana(out, Resetboard.aiplayer);

                        buildPosition(positionMap, value.getTilex(), value.getTiley());
                        getAiUnitMap().remove(cardUnit.getId());
                        positionMap.remove(key);
                        break;
                    }
                    positionMap.remove(key);
                }
            }
        }
    }

    public static Unit cardToUnit(Card card) {
        int id = card.getId();
        int attack = card.getBigCard().getAttack();
        int health = card.getBigCard().getHealth();
        Unit unit = getAiUnitMap().get(id);
        if (null == unit) {
            return null;
        }
        if (id == 27 || id == 37) {
            unit.setAttackRound(2);
        } else {
            unit.setAttackRound(1);
        }
        unit.setAttack(attack);
        unit.setHealth(health);
        return unit;
    }

    public static Map<Integer, Unit> getAiUnitMap() {
        if (null == aiUnitMap) {
            aiUnitMap = new HashMap<>();
            for (Unit unit : AiUnitLoader.getAiPlayerUnits()) {
                if (null != unit) {
                    aiUnitMap.put(unit.getId(), unit);
                }
            }
        }
        return aiUnitMap;
    }

    public static void buildPosition(Map<String, Position> positionMap, int currentX, int currentY) {
        if (currentX > 0) {
            positionMap.put((currentX - 1) + "_" + (currentY + 1), new Position((currentX - 1), (currentY + 1)));
            positionMap.put((currentX - 1) + "_" + currentY, new Position((currentX - 1), currentY));
            positionMap.put((currentX - 1) + "_" + (currentY - 1), new Position((currentX - 1), (currentY - 1)));
        }

        if (currentX < 8) {
            positionMap.putIfAbsent((currentX + 1) + "_" + (currentY + 1), new Position((currentX - 1), (currentY + 1)));
            positionMap.putIfAbsent((currentX + 1) + "_" + currentY, new Position((currentX - 1), currentY));
            positionMap.putIfAbsent((currentX + 1) + "_" + (currentY - 1), new Position((currentX - 1), (currentY - 1)));
        }

        positionMap.putIfAbsent(currentX + "_" + (currentY + 1), new Position(currentX, (currentY + 1)));
        positionMap.putIfAbsent(currentX + "_" + (currentY - 1), new Position(currentX, (currentY - 1)));
    }

    public static Tile moveTo(int currentX, int currentY) {
        int x = (int) (Math.ceil(Math.random() * 3)) - 1;
        if (currentX > 7) {
            currentX -= Math.abs(x);
        } else if (currentX < 1) {
            currentX += Math.abs(x);
        } else {
            currentX += (currentX % 2 == 0 ? -1 : 1) * Math.abs(x);
        }
        int y = 0;
        if (Math.abs(x) == 1) {
            y = (int) (Math.ceil(Math.random() * 2)) - 1;
        } else if (x == 0) {
            y = (int) (Math.ceil(Math.random() * 3)) - 1;
        }
        if (currentY > 3) {
            currentY -= Math.abs(y);
        } else if (currentY < 1) {
            currentY += Math.abs(y);
        } else {
            currentY += (currentY % 2 == 0 ? -1 : 1) * Math.abs(y);
        }
        int tx = Math.min(Math.max(currentX, 0), 8);
        int ty = Math.min(Math.max(currentY, 0), 4);
        return Board.getTile(tx, ty);
    }

}
	

