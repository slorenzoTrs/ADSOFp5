package grafos;

import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StateGraph<T> {
	private String nombre;
	private String desc;
	private LinkedHashMap<String, Node<T>> nodos = new LinkedHashMap<>();
	private Node<T> nodoI;
	private Node<T> nodoF;
	
	public StateGraph(String nombre, String desc) {
		this.nombre = nombre;
		this.desc = desc;
	}
	
	public StateGraph<T> addNode(String nombreNodo, Consumer<T> codNodo) {
		Node<T> node = new Node<T>(nombreNodo, codNodo);
		this.nodos.put(nombreNodo, node);
		return this;
	}
	
	public StateGraph<T> addEdge(String origen, String destino) {
		Node<T> nodoI = nodos.get(origen);
		Node<T> nodoF = nodos.get(destino);
		nodoI.setNextNode(nodoF);
		nodoI.addCondition(destino, null);
		return this;
	}
	
	public StateGraph<T> addConditionalEdge(String origen, String destino, Predicate<T> condExecute) {
		Node<T> nodoI = nodos.get(origen);
		Node<T> nodoF = nodos.get(destino);
		nodoI.setNextNode(nodoF);
		nodoI.addCondition(destino, condExecute);
		return this;
	}
	
	public void setInitial(String init) {
		Node<T> nodo = nodos.get(init);
		this.nodoI = nodo;
	}
	
	public void setFinal(String fin) {
		Node<T> nodo = nodos.get(fin);
		this.nodoF = nodo;
	}
	
	public T run(T input, boolean trazado) {
		int i = 1;
		if(trazado) {
			System.out.println("Step " + i + " (" + this.nombre + ") -- input: " + input.toString());
			i++;
		}
		T result = executeFrom(nodoI, input, trazado, i);
		return result;
	}
	
	private T executeFrom(Node<T> node, T data, boolean debug, int i) {
        if (node == null) return data;

        node.execute(data);
        if(debug) {
			System.out.println("Step " + i + " (" + this.nombre + ") -- " + node.getNombre() + " executed: " + data.toString());
			i++;
		}

        for (Node<T> next : node.getNextNode()) {
        	Predicate<T> condExecute = node.getCondition(next.getNombre());
        	if (condExecute != null) {
        		if (condExecute.test(data) == false) {
        			return data;
        		}
        	}
            executeFrom(next, data, debug, i);
        }

        return data;
    }
	
	@Override
	public String toString() {
		String s = "";
		s += "Workflow '" + nombre + "' (" + desc + "):\n";
		s += "-- Nodes: " + nodos.toString() + "\n";
		s += "-- Initial: " + nodoI.getNombre() + "\n";
		s += "-- Final: " + nodoF.getNombre();
		return s;
	}
}
