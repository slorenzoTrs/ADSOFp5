package datos;

public class MutableDouble {
    private double value;
    
    public MutableDouble(double value) {
        this.value = value;
    }
    
    public void setDouble(double value) {
    	this.value = value;
    }
    
    public double getDouble() {
    	return value;
    }
    
    @Override
    public String toString() {
        return Double.toString(value);
    }
}
