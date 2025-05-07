package decoradores;

/**
 * Clase que guarda información sobre la ejecución de un nodo.
 * 
 * @param <T> Tipo de datos que procesa el grafo.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 * @autor Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class Trace<T> {
	private String nombreNodo;
	private double duracionMs;
	private T input;
	
	/**
	 * Contructor de la clase trace.
	 * 
	 * @param nombreNodo Nombre del nodo que se quiere crear.
	 * @param tiempo Tiempo que ha tardado en ejecutarse el nodo.
	 * @param input Entrada con la que se ejecuto el nodo.
	 */
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
