package structures.basic;

import actors.GameActor;
import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.BasicCommands;

import java.util.HashMap;

/**
 * This is a representation of a Unit on the game board.
 * A unit has a unique id (this is used by the front-end.
 * Each unit has a current UnitAnimationType, e.g. move,
 * or attack. The position is the physical position on the
 * board. UnitAnimationSet contains the underlying information
 * about the animation frames, while ImageCorrection has
 * information for centering the unit on the tile.
 *
 * @author Dr. Richard McCreadie
 */
public class Unit {

    @JsonIgnore
    protected static ObjectMapper mapper = new ObjectMapper(); // Jackson Java Object Serializer, is used to read java objects from a file

    int id;
    UnitAnimationType animation;
    Position position;
    UnitAnimationSet animations;
    ImageCorrection correction;

    private int attack;
    private int health;
    private int attackRound;
    private int moveRound = 1;

    //Congzhou Yi
    public int getMoveRound() {
        return moveRound;
    }

    public void setMoveRound(int moveRound) {
        this.moveRound = moveRound;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackRound() {
        return attackRound;
    }

    public void setAttackRound(int attackRound) {
        this.attackRound = attackRound;
    }

    private boolean turnMove = false;
    private boolean turnAttack = false;

    public Unit() {

    }

    public Unit(int id, UnitAnimationSet animations, ImageCorrection correction) {
        super();
        this.id = id;
        this.animation = UnitAnimationType.idle;
        position = new Position(0, 0, 0, 0);
        this.correction = correction;
        this.animations = animations;
    }

    public Unit(int id, UnitAnimationSet animations, ImageCorrection correction, Tile currentTile) {
        super();
        this.id = id;
        this.animation = UnitAnimationType.idle;
        position = new Position(currentTile.getXpos(), currentTile.getYpos(), currentTile.getTilex(), currentTile.getTiley());
        this.correction = correction;
        this.animations = animations;
    }


    public Unit(int id, UnitAnimationType animation, Position position, UnitAnimationSet animations,
                ImageCorrection correction) {
        super();
        this.id = id;
        this.animation = animation;
        this.position = position;
        this.animations = animations;
        this.correction = correction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UnitAnimationType getAnimation() {
        return animation;
    }

    public void setAnimation(UnitAnimationType animation) {
        this.animation = animation;
    }

    public ImageCorrection getCorrection() {
        return correction;
    }

    public void setCorrection(ImageCorrection correction) {
        this.correction = correction;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public UnitAnimationSet getAnimations() {
        return animations;
    }

    public void setAnimations(UnitAnimationSet animations) {
        this.animations = animations;
    }

    /**
     * This command sets the position of the Unit to a specified
     * tile.
     *
     * @param tile
     */
    @JsonIgnore
    public void setPositionByTile(Tile tile) {
        position = new Position(tile.getXpos(), tile.getYpos(), tile.getTilex(), tile.getTiley());
    }

    public boolean isTurnMove() {
        return turnMove;
    }

    public void setTurnMove(boolean turnMove) {
        this.turnMove = turnMove;
    }


    public boolean isTurnAttack() {
        return turnAttack;
    }

    public void setTurnAttack(boolean turnAttack) {
        this.turnAttack = turnAttack;
    }


    //Congzhou Yi
    public void attack(ActorRef out, HashMap<Tile, Tile> attackedUnit, Tile tileOfAttacked) {
        if (this.getAttackRound() > 0) {
            tileOfAttacked.canBeAttack = false;
            this.attackRound--;
            if (this.getId() == 2 || this.getId() == 11) {
                if (ShowScale.getRound(Board.getTile(this.position.tilex, this.position.tiley)).contains(tileOfAttacked)) {
                    this.attack(out, tileOfAttacked, true);
                } else {
                    this.attack(out, tileOfAttacked, false);
                }
            } else {
                if (!ShowScale.getRound(Board.getTile(this.position.tilex, this.position.tiley)).contains(tileOfAttacked)) {
                    Tile tile = attackedUnit.get(tileOfAttacked);
                    if (null != tile) {
                        //xia
                        tile.setHaveHumanUnit(true);
                        this.move(out, tile);
                    }
                }
                this.attack(out, tileOfAttacked, true);
            }
        }
    }

    //xia
    public void attackHum(ActorRef out, Tile tileOfAttacked) {
        System.out.println("----可进行攻击次数：" + this.getAttackRound());
        if (this.getAttackRound() > 0) {
            tileOfAttacked.canBeAttack = false;
            this.attackRound--;
            System.out.println("----进行攻击" + this.getId());
            if (this.getId() == 22 || this.getId() == 31) {
                if (ShowScale.getRound(Board.getTile(this.position.tilex, this.position.tiley)).contains(tileOfAttacked)) {
                    this.attack(out, tileOfAttacked, true);
                } else {
                    this.attack(out, tileOfAttacked, false);
                }
            } else {
                if (!ShowScale.getRound(Board.getTile(this.position.tilex, this.position.tiley)).contains(tileOfAttacked)) {
                    this.attackRound++;
                    return;
                }
                this.attack(out, tileOfAttacked, true);
            }
        }
    }
    //xia
    public void attack(ActorRef out, Tile tileOfAttacked, boolean canCounter) {
        if (null == tileOfAttacked || null == tileOfAttacked.getUnit()) {
            return;
        }
        BasicCommands.addPlayer1Notification(out, "playUnitAnimation [Attack]", 2);
        BasicCommands.playUnitAnimation(out, this, UnitAnimationType.attack);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BasicCommands.playUnitAnimation(out, tileOfAttacked.getUnit(), UnitAnimationType.hit);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BasicCommands.playUnitAnimation(out, this, UnitAnimationType.idle);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (tileOfAttacked.getUnit().getHealth() <= this.attack) {
            tileOfAttacked.getUnit().death(out);
        } else {
            tileOfAttacked.getUnit().setHealth(tileOfAttacked.getUnit().getHealth() - this.attack);
            // setUnitHealth
            BasicCommands.addPlayer1Notification(out, "setUnitHealth", 2);
            BasicCommands.setUnitHealth(out, tileOfAttacked.getUnit(), tileOfAttacked.getUnit().getHealth());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BasicCommands.playUnitAnimation(out, tileOfAttacked.getUnit(), UnitAnimationType.idle);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (canCounter) {
                tileOfAttacked.getUnit().attack(out, Board.getTile(this.position.tilex, this.position.tiley), false);
            }
        }
    }

    public void death(ActorRef out) {
        this.setHealth(0);
        // setUnitHealth
        BasicCommands.addPlayer1Notification(out, "setUnitHealth", 2);
        BasicCommands.setUnitHealth(out, this, 0);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // playUnitAnimation [Death]
        BasicCommands.playUnitAnimation(out, this, UnitAnimationType.death);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BasicCommands.deleteUnit(out, this);
        // deleteUnit
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.id == 40) {
            BasicCommands.addPlayer1Notification(out, "Game over, you lose.", 2);
            GameActor.getGameState().something = false;
        } else if (this.id == 41) {
            BasicCommands.addPlayer1Notification(out, "Game over, you win.", 2);
            GameActor.getGameState().something = false;
        } else {
            Board.getTile(this.position.tilex, this.position.tiley).setUnit(null);
            Board.getTile(this.position.tilex, this.position.tiley).setHaveHumanUnit(false);
        }
    }


    //xia update
    public void move(ActorRef out, Tile tile) {
        Board.getTile(this.position.tilex, this.position.tiley).setUnit(null);
        Board.getTile(this.position.tilex, this.position.tiley).setHaveHumanUnit(false);
        tile.setUnit(this);
        this.setPositionByTile(tile);
        if (this.position.tiley != tile.tiley) {
            if ((this.position.tilex > tile.tilex && Board.getTile(this.position.tilex - 1, this.position.tiley).isHaveAiUnit()) || (this.position.tilex < tile.tilex && Board.getTile(this.position.tilex + 1, this.position.tiley).isHaveAiUnit())) {
                BasicCommands.moveUnitToTile(out, this, tile, true);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            BasicCommands.moveUnitToTile(out, this, tile);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
