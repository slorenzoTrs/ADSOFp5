package decoradores;

import java.util.Collection;
import java.util.function.Predicate;

import grafos.Graph;
import grafos.StateGraph;
import nodos.ComponentNode;
import nodos.NodeG;

/**
 * Decorador abstracto para la interfaz Graph.
 *
 * @param <T> Tipo de dato de entrada y salida.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public abstract class StateGraphDecorator<T> implements Graph<T> {
    private Graph<T> graph;

    /**
     * Constructor del decorador.
     *
     * @param graph Grafo a decorar.
     */
    public StateGraphDecorator(Graph<T> graph) {
        this.graph = graph;
    }
    
    /**
     * Ejecuta el grafo decorado desde el nodo inicial con el dato proporcionado.
     *
     * @param input Dato de entrada.
     * @param trazado Si es true, se imprime el trazado de ejecución.
     * @return Resultado final tras la ejecución.
     */
    @Override
    public T run(T input, boolean trazado) {
        return graph.run(input, trazado);
    }
    
    /**
     * Devuelve el nombre identificador del grafo.
     *
     * @return Nombre del grafo decorado.
     */
    @Override
    public String getNombre() {
        return graph.getNombre();
    }

    /**
     * Devuelve la descripción del grafo decorado.
     *
     * @return Descripción del grafo.
     */
    @Override
    public String getDescripcion() {
        return graph.getDescripcion();
    }
    
    /**
     * Obtiene los nodos del grafo.
     *
     * @return Colección con todos los nodos del grafo actual.
     */
    @Override
	public Collection<ComponentNode<T>> getNodes() {
    	return graph.getNodes();
    }

    /**
     * Añade un nodo ya creado al grafo decorado.
     *
     * @param node Nodo a añadir.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    @Override
    public Graph<T> addNode(ComponentNode<T> node) {
        return graph.addNode(node);
    }

    /**
     * Añade un nodo-grafo al grafo decorado.
     *
     * @param <U> Tipo del grafo hijo.
     * @param nombreGrafo Nombre del nodo-grafo.
     * @param grafo Grafo hijo a insertar.
     * @return NodoG creado e insertado en el grafo.
     */
    @Override
    public <U> NodeG<T, U> addWfNode(String nombreGrafo, StateGraph<U> grafo) {
        return graph.addWfNode(nombreGrafo, grafo);
    }

    /**
     * Añade una transición directa entre dos nodos del grafo decorado.
     *
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    @Override
    public StateGraph<T> addEdge(String origen, String destino) {
        return graph.addEdge(origen, destino);
    }

    /**
     * Añade una transición condicional entre dos nodos del grafo decorado.
     *
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @param condExecute Condición que debe cumplirse para la transición.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    @Override
    public StateGraph<T> addConditionalEdge(String origen, String destino, Predicate<T> condExecute) {
        return graph.addConditionalEdge(origen, destino, condExecute);
    }

    /**
     * Define el nodo inicial del grafo decorado.
     *
     * @param init Nombre del nodo inicial.
     */
    @Override
    public void setInitial(String init) {
    	graph.setInitial(init);
    }

    /**
     * Define el nodo final del grafo decorado.
     *
     * @param fin Nombre del nodo final.
     */
    @Override
    public void setFinal(String fin) {
    	graph.setFinal(fin);
    }

    /**
     * Obtiene el nodo inicial del grafo decorado.
     *
     * @return Nodo inicial.
     */
    @Override
    public ComponentNode<T> getInitial() {
        return graph.getInitial();
    }

    /**
     * Obtiene el nodo final del grafo decorado.
     *
     * @return Nodo final.
     */
    @Override
    public ComponentNode<T> getFinal() {
        return graph.getFinal();
    }

    /**
     * Ejecuta el flujo del grafo desde el nodo inicial de forma recursiva.
     *
     * @param data Dato de entrada.
     * @param debug Si es true, se imprimen pasos intermedios.
     * @return Resultado tras ejecutar el flujo.
     */
    @Override
    public T executeFrom(T data, boolean debug) {
        return graph.executeFrom(data, debug);
    }

    /**
     * Devuelve una representación textual del grafo decorado.
     *
     * @return Cadena representando el grafo.
     */
    @Override
    public String toString() {
        return graph.toString();
    }
}