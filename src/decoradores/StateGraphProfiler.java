package decoradores;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import datos.PublicCloneable;
import grafos.Graph;
import nodos.ComponentNode;
import nodos.Node;

/**
 * Decorador que añade capacidad de profiling (conteo de ejecuciones) a un StateGraph.
 * Cada nodo es decorado con un NodeProfiler.
 * 
 * @param <T> Tipo de datos que procesa el grafo.
 * 
 * @autor Sofía Castro - sofiai.castro@estudiante.uam.es
 * @autor Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class StateGraphProfiler<T extends PublicCloneable<T>> extends StateGraphDecorator<T> {

    /**
     * Constructor que decora un StateGraph con capacidades de profiling.
     * 
     * @param graph Grafo original a decorar.
     */
    public StateGraphProfiler(Graph<T> graph) {
        super(graph);
    }

    /**
     * Añade un nodo al grafo original, envolviéndolo en un NodeLogger
     * para registrar su ejecución.
     *
     * @param nombreNodo Nombre del nodo a añadir.
     * @param codNodo Código que se ejecutará en el nodo.
     * @return Este decorador, permitiendo encadenamiento.
     */
    @Override
    public Graph<T> addNode(String nombreNodo, Consumer<T> codNodo) {
        Node<T> node = new Node<>(nombreNodo, codNodo);
        NodeProfiler<T> decorated = new NodeProfiler<T>(node);
        super.addNode(decorated);
        return this;
    }
    
    /**
     * Añade un nodo ya creado al grafo decorado.
     *
     * @param node Nodo a añadir.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    @Override
    public Graph<T> addNode(ComponentNode<T> node) {
    	NodeProfiler<T> decorated = new NodeProfiler<T>(node);
        return super.addNode(decorated);
    }
    
    /**
     * Obtine las trazas que los nodos han generado al ejecutarse.
     *
     * @return Lista de trazas.
     */
    public List<Trace<T>> history() {
    	List<Trace<T>> history = new ArrayList<>();
    	
    	for (ComponentNode<T> node : super.getNodes()) {
            // Desempaquetar el decorador hasta llegar a un NodeProfiled (si existe)
            ComponentNode<T> current = node;
            while (current instanceof NodeDecorator) {
                if (current instanceof NodeProfiler) {
                    history.addAll(((NodeProfiler<T>) current).getTraces());
                    break;
                }
                current = ((NodeDecorator<T>) current).getDecoratedNode();
            }
        }
    	
    	return history;
    }

    
}
