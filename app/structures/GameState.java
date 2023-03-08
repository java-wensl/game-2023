package structures;

/**
 * This class can be used to hold information about the on-going game.
 * Its created with the GameActor.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class GameState {

	
	public boolean gameInitalised = false;
	
	public boolean something = false;
	
	public boolean CardHighLighted = false;
	
	public int cardHightLightPosition = 0;
	
	public boolean UnitHighLighted = false;


	public int getCardHightLightPosition() {
		return cardHightLightPosition;
	}

	public void setCardHightLightPosition(int cardHightLightPosition) {
		this.cardHightLightPosition = cardHightLightPosition;
	}

	public boolean isCardHighLighted() {
		return CardHighLighted;
	}

	public void setCardHighLighted(boolean cardHighLighted) {
		CardHighLighted = cardHighLighted;
	}

	public boolean isUnitHighLighted() {
		return UnitHighLighted;
	}

	public void setUnitHighLighted(boolean unitHighLighted) {
		UnitHighLighted = unitHighLighted;
	}

}
