package decoradores;

public class Trace<T> {
	private String nombreNodo;
	private double duracionMs;
	private T input;
	
	public Trace(String nombreNodo, double tiempo, T input) {
		this.nombreNodo = nombreNodo;
		this.duracionMs = tiempo;
		this.input = input;
	}
	
	@Override 
	public String toString() {
		return "["+nombreNodo+" with:"+ input+" "+duracionMs+" ms]";
	}

}
