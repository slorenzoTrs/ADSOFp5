package grafos;

import java.util.HashMap;
import java.util.function.Consumer;

public class StateGraph<T> {
	private String nombre;
	private String desc;
	private HashMap<String, Node<T>> nodos = new HashMap<>();
	private Node<T> nodoI;
	private Node<T> nodoF;
	
	public StateGraph(String nombre, String desc) {
		this.nombre = nombre;
		this.desc = desc;
	}
	
	public StateGraph<T> addNode(String nombreNodo, Consumer<T> codNodo) {
		return this;
	}
}
