package grafos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Elemento<R> {
	private String nombre;
	private List<Elemento<?>> nextNodes = new LinkedList<>();
	private HashMap<String, Predicate<R>> condExecutes = new HashMap<>();
	
	public Elemento(String nombre) {
		this.nombre = nombre;
	}
	
	public abstract boolean isNode();
	public abstract void ejecutar(R input, boolean debug);
	
	public String getNombre() {
		return nombre;
	}
	
	/**
     * Obtiene la lista de nodos siguientes en el flujo.
     * 
     * @return Lista de nodos siguientes.
     */
	public List<Elemento<?>> getNextNodes() {
		return nextNodes;
	}
	
	/**
     * Añade un nodo como siguiente en el flujo de ejecución.
     * 
     * @param node Nodo a añadir como siguiente.
     */
	public void addNextNode(Elemento<?> node) {
		if(nextNodes.contains(node)) { return; }
		this.nextNodes.add(node);
	}
	
	/**
     * Añade una condicion para ejecutar a la arista que une los nodos.
     * 
     * @param node Nombre identificador del nodo.
     * @param condExecute expresión lambda que sirve de condicion para la arista.
     */
	public void addCondition(String node, Predicate<R> condExecute) {
		this.condExecutes.put(node, condExecute);
	}
	
	/**
     * Devuelve la expresión lambda que sirve de condicion para la arista que une ambos nodos.
     * 
     * @param node Nombre identificador del nodo.
     * 
     * @return Condicion para ejecutar la arista entre ambos nodos.
     */
	public Predicate<R> getCondition(String node) {
		return this.condExecutes.get(node) ;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
