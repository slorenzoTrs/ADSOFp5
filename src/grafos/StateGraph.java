package grafos;

import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * La clase StateGraph representa un grafo dirigido que modela un flujo de estados o ejecución.
 * Cada nodo del grafo tiene asociado un bloque de código ejecutable y puede estar condicionado
 * por predicados que determinan si se sigue una transición hacia otro nodo.
 * 
 * Permite construir flujos de ejecución dinámicos mediante nodos y aristas (condicionales o no),
 * y ejecutar el flujo completo desde un nodo inicial hasta uno final.
 * 
 * @param <T> Tipo de dato sobre el cual operan los nodos del grafo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class StateGraph<T> {
	private String nombre;
	private String desc;
	private LinkedHashMap<String, Node<T>> nodos = new LinkedHashMap<>();
	private Node<T> nodoI;
	private Node<T> nodoF;
	
	/**
     * Constructor de la clase StateGraph.
     * 
     * @param nombre Nombre identificador del grafo.
     * @param desc Descripción del propósito del grafo.
     */
	public StateGraph(String nombre, String desc) {
		this.nombre = nombre;
		this.desc = desc;
	}
	
	/**
     * Añade un nodo al grafo con su código asociado.
     * 
     * @param nombreNodo Nombre identificador del nodo.
     * @param codNodo Código que se ejecutará al activar el nodo.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public StateGraph<T> addNode(String nombreNodo, Consumer<T> codNodo) {
		Node<T> node = new Node<T>(nombreNodo, codNodo);
		this.nodos.put(nombreNodo, node);
		return this;
	}
	
	/**
     * Añade una arista no condicional entre dos nodos.
     * 
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public StateGraph<T> addEdge(String origen, String destino) {
		Node<T> nodoI = nodos.get(origen);
		Node<T> nodoF = nodos.get(destino);
		nodoI.setNextNode(nodoF);
		nodoI.addCondition(destino, null);
		return this;
	}
	
	/**
     * Añade una arista condicional entre dos nodos.
     * 
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @param condExecute Condición que debe cumplirse para realizar la transición.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public StateGraph<T> addConditionalEdge(String origen, String destino, Predicate<T> condExecute) {
		Node<T> nodoI = nodos.get(origen);
		Node<T> nodoF = nodos.get(destino);
		nodoI.setNextNode(nodoF);
		nodoI.addCondition(destino, condExecute);
		return this;
	}
	
	/**
     * Define el nodo inicial del grafo.
     * 
     * @param init Nombre del nodo inicial.
     */
	public void setInitial(String init) {
		Node<T> nodo = nodos.get(init);
		this.nodoI = nodo;
	}
	
	/**
     * Define el nodo final del grafo.
     * 
     * @param fin Nombre del nodo final.
     */
	public void setFinal(String fin) {
		Node<T> nodo = nodos.get(fin);
		this.nodoF = nodo;
	}
	
	/**
     * Ejecuta el grafo desde el nodo inicial con un dato de entrada.
     * 
     * @param input Dato de entrada para el flujo.
     * @param trazado Si es true, imprime el trazado de pasos por consola.
     * @return Resultado final tras ejecutar el flujo completo.
     */
	public T run(T input, boolean trazado) {
		int i = 1;
		if(trazado) {
			System.out.println("Step " + i + " (" + this.nombre + ") -- input: " + input.toString());
			i++;
		}
		T result = executeFrom(nodoI, input, trazado, i);
		return result;
	}
	
	/**
     * Ejecuta recursivamente el flujo desde un nodo dado.
     * 
     * @param node Nodo desde el cual comienza la ejecución.
     * @param data Dato a procesar.
     * @param debug Si es true, se imprime el paso actual.
     * @param i Contador de pasos para trazado.
     * @return Resultado tras procesar el flujo desde el nodo dado.
     */
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
	
	/**
     * Devuelve una representación en cadena del grafo, incluyendo nodos y conexiones.
     * 
     * @return Cadena representando el estado del grafo.
     */
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
