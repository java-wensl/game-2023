package structures.basic;

public class UnitPosition {
    private Unit unit;
    private int position;
    
    public UnitPosition(Unit unit, int position) {
        this.unit = unit;
        this.position = position;
    }
    
    public Unit getUnit() {
        return unit;
    }
    
    public int getPosition() {
        return position;
    }
}
