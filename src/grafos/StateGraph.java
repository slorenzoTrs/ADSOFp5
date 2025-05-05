package grafos;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

import nodos.ComponentNode;
import nodos.Node;
import nodos.NodeG;

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
public class StateGraph<T> implements Graph<T>{
	private String nombre;
	private String desc;
	private LinkedHashMap<String, ComponentNode<T>> nodos = new LinkedHashMap<>();
	private ComponentNode<T> nodoI;
	private ComponentNode<T> nodoF;
	
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
     * Obtiene el nombre del grafo.
     *
     * @return Nombre del grafo actual.
     */
	public String getNombre() {
		return nombre;
	}
	
	/**
     * Obtiene la descripcion del grafo.
     *
     * @return Descripción del grafo actual.
     */
	public String getDescripcion() {
		return desc;
	}
	
	/**
     * Obtiene los nodos del grafo.
     *
     * @return Colección con todos los nodos del grafo actual.
     */
	public Collection<ComponentNode<T>> getNodes() {
		return nodos.values();
	}
	
	/**
     * Añade un nodo al grafo con su código asociado.
     * 
     * @param nombreNodo Nombre identificador del nodo.
     * @param codNodo Código que se ejecutará al activar el nodo.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	@Override
	public StateGraph<T> addNode(String nombreNodo, Consumer<T> codNodo) {
		Node<T> node = new Node<T>(nombreNodo, codNodo);
		this.nodos.put(nombreNodo, node);
		return this;
	}
	
	/**
     * Añade un nodo al grafo con su código asociado.
     * 
     * @param node Nodo que se desea añadir.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
    public Graph<T> addNode(ComponentNode<T> node) {
    	this.nodos.put(node.getNombre(), node);
		return this;
    }
	
	/**
     * Añade un nodo, en modo de StateGraph, al grafo con sus propios nodos.
     * 
     * @param nombreGrafo Nombre identificador del nodo-grafo.
     * @param grafo Grafo hijo que estará contenido como un nodo más.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public <U> NodeG<T, U> addWfNode(String nombreGrafo, StateGraph<U> grafo) {
		NodeG<T, U> node = new NodeG<T, U>(nombreGrafo, grafo);
		this.nodos.put(nombreGrafo, node);
		return node;
	}
	
	/**
     * Añade una arista no condicional entre dos nodos.
     * 
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public StateGraph<T> addEdge(String origen, String destino) {
		ComponentNode<T> nodoI = null, nodoF = null;
		nodoI = this.nodos.get(origen);
		nodoF = this.nodos.get(destino);
		nodoI.addNextNode(nodoF);
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
		ComponentNode<T> nodoI = null, nodoF = null;
		nodoI = this.nodos.get(origen);
		nodoF = this.nodos.get(destino);
		nodoI.addNextNode(nodoF);
		nodoI.addCondition(destino, condExecute);
		return this;
	}
	
	/**
     * Define el nodo inicial del grafo.
     * 
     * @param init Nombre del nodo inicial.
     */
	public void setInitial(String init) {
		ComponentNode<T> nodo = nodos.get(init);
		this.nodoI = nodo;
	}
	
	/**
     * Define el nodo final del grafo.
     * 
     * @param fin Nombre del nodo final.
     */
	public void setFinal(String fin) {
		ComponentNode<T> nodo = nodos.get(fin);
		this.nodoF = nodo;
	}
	
	/**
     * Obtiene el nodo inicial del grafo.
     * 
     * @return init Nodo inicial.
     */
	public ComponentNode<T> getInitial() {
		return this.nodoI;
	}
	
	/**
     * Obtiene el nodo final del grafo.
     * 
     * @return init Nodo final.
     */
	public ComponentNode<T> getFinal() {
		return this.nodoF;
	}
	
	/**
     * Ejecuta el grafo desde el nodo inicial con un dato de entrada.
     * 
     * @param input Dato de entrada para el flujo.
     * @param trazado Si es true, imprime el trazado de pasos por consola.
     * @return Resultado final tras ejecutar el flujo completo.
     */
	@Override
	public T run(T input, boolean trazado) {
		int i = 1;
		if(trazado) {
			System.out.println("Step " + i + " (" + this.getNombre() + ") -- input: " + input.toString());
			i++;
		}
		T result = executeFrom(input, trazado);
		return result;
	}
	
	/**
     * Ejecuta recursivamente el flujo desde el nodo inicial.
     * 
     * @param data Dato a procesar.
     * @param debug Si es true, se imprime el paso actual.
     * @return Resultado tras procesar el flujo desde el nodo dado.
     */
	public T executeFrom(T data, boolean debug) {
        int step = 2;
        ComponentNode<T> actual = nodoI;
        
        while (actual != null) {
    		// Ejecutar el código del nodo actual
    		actual.execute(data, debug);
    		if(debug) {
    			System.out.println("Step " + step + " (" + nombre + ") -- " + actual.getNombre() + " executed: " + data.toString());
    			step++;
    		}

    		// Si es el nodo final, detener
    		if (actual == nodoF) break;

    		// Buscar el siguiente nodo válido
    		ComponentNode<T> siguiente = null;
    		for (ComponentNode<T> next : actual.getNextNodes()) {
    			Predicate<T> cond = actual.getCondition(next.getNombre());
    			if (cond == null || cond.test(data)) {
    				siguiente = next;
    				break; // Solo seguimos el primer nodo cuya condición se cumpla
    			}
    		}

    		// Si no se encontró un siguiente nodo válido, terminar
    		if (siguiente == null) break;

    		actual = siguiente;
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
		s += "Workflow '" + this.getNombre() + "' (" + desc + "):\n";
		s += "-- Nodes: " + nodos + "\n";
		s += "-- Initial: " + nodoI.getNombre() + "\n";
		s += "-- Final: ";
		if(nodoF != null) {
			s += nodoF.getNombre();
		} else { s += "None"; }
		return s;
	}
}
