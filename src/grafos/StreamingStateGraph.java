package grafos;

import java.util.LinkedList;
import java.util.List;

public class StreamingStateGraph<T extends Comparable<T>> extends StateGraph<List<T>> {
	private List<T> history = new LinkedList<>();

	public StreamingStateGraph(String nombre, String desc) {
		super(nombre, desc);
	}
	
	/**
     * Ejecuta el grafo desde el nodo inicial con un dato de entrada.
     * Guardando el estado del grafo al finalizar.
     * 
     * @param input Dato de entrada para el flujo.
     * @param trazado Si es true, imprime el trazado de pasos por consola.
     * @return Resultado final tras ejecutar el flujo completo.
     */
	public T run(T input, boolean trazado) {
		int i = 1;
		history.add(input);
		if(trazado) {
			System.out.println("Step " + i + " (" + super.getNombre() + ") -- input: " + history.toString());
			i++;
		}
		List<T> result = executeFrom(history, trazado);
		return result.getLast();
	}
		
	public List<T> history() {
		return history;
	}
}
