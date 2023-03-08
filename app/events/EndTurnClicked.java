package events;

import New.endTurn;
import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import structures.GameState;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * the end-turn button.
 * <p>
 * {
 * messageType = “endTurnClicked”
 * }
 *
 * @author Dr. Richard McCreadie
 */
public class EndTurnClicked implements EventProcessor {

    @Override
    public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

        endTurn.endTurnClicked(out, gameState, message);

    }

}
