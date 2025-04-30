package grafos;

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
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
public class StateGraph<T> extends Elemento<T>{
	private String desc;
	private LinkedHashMap<String, Elemento<?>> nodos = new LinkedHashMap<>();
	private Elemento<?> nodoI;
	private Elemento<?> nodoF;
	private Function<?, T> inyectorGenerico;
	private BiConsumer<T, ?> extractorGenerico;
	
	/**
     * Constructor de la clase StateGraph.
     * 
     * @param nombre Nombre identificador del grafo.
     * @param desc Descripción del propósito del grafo.
     */
	public StateGraph(String nombre, String desc) {
		super(nombre);
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
     * Añade un nodo, en modo de StateGraph, al grafo con sus propios nodos.
     * 
     * @param nombreGrafo Nombre identificador del nodo-grafo.
     * @param grafo Grafo hijo que estará contenido como un nodo más.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
	public <U> StateGraph<T> addWfNode(String nombreGrafo, StateGraph<U> grafo) {
			this.nodos.put(nombreGrafo, grafo);
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
		Elemento<?> nodoI = null, nodoF = null;
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
		Elemento<?> nodoF = null;
		Elemento<T> nodoI = null;
		nodoI = (Elemento<T>) this.nodos.get(origen);
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
		Elemento<?> nodo = nodos.get(init);
		this.nodoI = nodo;
	}
	
	/**
     * Define el nodo final del grafo.
     * 
     * @param fin Nombre del nodo final.
     */
	public void setFinal(String fin) {
		Elemento<?> nodo = nodos.get(fin);
		this.nodoF = nodo;
	}
	
	/**
     * Obtiene el nodo inicial del grafo.
     * 
     * @result init Nodo inicial.
     */
	public Elemento<?> getInitial() {
		return this.nodoI;
	}
	
	/**
     * Obtiene el nodo final del grafo.
     * 
     * @result init Nodo final.
     */
	public Elemento<?> getFinal() {
		return this.nodoF;
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
			System.out.println("Step " + i + " (" + super.toString() + ") -- input: " + input.toString());
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
	private T executeFrom(Elemento<?> node, T data, boolean debug, int i) {
        if (node == null) return data;
        
        ((Elemento<T>) node).ejecutar(data, debug);
        if(debug) {
			System.out.println("Step " + i + " (" + super.toString() + ") -- " + node.getNombre() + " executed: " + data.toString());
			i++;
		}

        for (Elemento<?> next : node.getNextNodes()) {
        	Predicate<T> condExecute = (Predicate<T>) node.getCondition(next.getNombre());
        	if (condExecute != null) {
        		if (condExecute.test(data) == false) {
        			return data;
        		}
        	}
        	if (next instanceof StateGraph<?> sg) {
                // Grafo hijo: aplicar inyector y extractor
        		Function<Object, Object> inyector = (Function<Object, Object>) ((StateGraph<?>) next).inyectorGenerico;
        		BiConsumer<Object, Object> extractor = (BiConsumer<Object, Object>) ((StateGraph<?>) next).extractorGenerico;

        		StateGraph<Object> sgCast = (StateGraph<Object>) sg;

        		Object datoHijo = inyector != null ? inyector.apply(data) : null;

        		sgCast.run(datoHijo, debug);  // ejecutar el subgrafo

                if (extractor != null) {
                    extractor.accept(datoHijo, data);  // combinar resultados
                }

            } else {
                // Nodo normal
                executeFrom(next, data, debug, i);
            }
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
		s += "-- Nodes: " + nodos.toString() + "\n";
		s += "-- Initial: " + nodoI.getNombre() + "\n";
		s += "-- Final: " + nodoF.getNombre();
		return s;
	}
	
	public <R> void withInjector(Function<R, T> injector) {
		this.inyectorGenerico = injector;
	}

	public <R> void withExtractor(BiConsumer<T, R> extractor) {
		this.extractorGenerico = extractor;
	}

	@Override
	public boolean isNode() {
		return false;
	}

	
	@Override
	public void ejecutar(T input, boolean debug) {
		this.run(input, debug);
	}
}
