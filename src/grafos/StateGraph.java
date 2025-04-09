package grafos;

import java.util.HashMap;

public class StateGraph<T> {
	private String nombre;
	private String desc;
	private HashMap<String, Node<T>> nodos = new HashMap<>();
	
	public StateGraph(String nombre, String desc) {
		this.nombre = nombre;
		this.desc = desc;
	}
	
	
}
