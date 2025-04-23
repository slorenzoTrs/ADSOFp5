package grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * La clase Node representa un nodo genérico dentro de una estructura de flujo,
 * que contiene un nombre identificador, una operación a ejecutar y una lista de nodos siguientes.
 * Esta clase permite construir estructuras tipo grafo para modelar flujos de ejecución.
 *
 * @param <S> Tipo de dato sobre el cual opera la función del nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class Node<S> {
	private String nombre;
	private Consumer<S> codigo;
	private List<Node<S>> nextNodes = new ArrayList<>();
	
	/**
     * Constructor de la clase Node.
     * 
     * @param nombre Nombre identificador del nodo.
     * @param codigo Código a ejecutar cuando el nodo es activado.
     */
	public Node(String nombre, Consumer<S> codigo) {
		this.nombre = nombre;
		this.codigo = codigo;
	}
	
	/**
     * Obtiene el nombre del nodo.
     * 
     * @return Nombre del nodo.
     */
	public String getNombre() {
		return nombre;
	}
	
	/**
     * Obtiene la función de código asociada al nodo.
     * 
     * @return Función que representa el comportamiento del nodo.
     */
	public Consumer<S> getCodigo() {
		return codigo;
	}
	
	/**
     * Obtiene la lista de nodos siguientes en el flujo.
     * 
     * @return Lista de nodos siguientes.
     */
	public List<Node<S>> getNextNode() {
		return nextNodes;
	}
	
	/**
     * Añade un nodo como siguiente en el flujo de ejecución.
     * 
     * @param node Nodo a añadir como siguiente.
     */
	public void setNextNode(Node<S> node) {
		if(nextNodes.contains(node)) { return; }
		this.nextNodes.add(node);
	}
	
	/**
     * Ejecuta el código asociado al nodo con una entrada dada.
     * 
     * @param input Entrada sobre la que se ejecuta el código del nodo.
     */
	public void execute(S input) {
		codigo.accept(input);
	}
	
	/**
     * Devuelve una representación en formato String del nodo.
     * 
     * @return Cadena con el nombre del nodo y el número de nodos siguientes.
     */
	@Override
	public String toString() {
		String s = "Node " + nombre + " (" + nextNodes.size() + " output nodes)";
		return s;
	}
}
