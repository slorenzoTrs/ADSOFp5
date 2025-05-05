package decoradores;

import java.util.function.Consumer;

import grafos.Graph;
import nodos.ComponentNode;
import nodos.Node;

/**
 * Decorador que añade capacidad de registro (logging) a un StateGraph.
 * Cada nodo es decorado con un NodeLogger y se almacena la traza de ejecución en un fichero.
 * 
 * @param <T> Tipo de datos que procesa el grafo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class StateGraphLogger<T> extends StateGraphDecorator<T> {
    private String path;

    /**
     * Constructor que decora un StateGraph con capacidades de logging.
     * 
     * @param graph Grafo original a decorar.
     * @param path Ruta del fichero de log.
     */
    public StateGraphLogger(Graph<T> graph, String path) {
        super(graph);
        this.path = path;
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
        NodeLogger<T> decorated = new NodeLogger<>(node, path);
        super.addNode(decorated);
        return this;
    }
    
    /**
     * Añade un nodo ya creado al grafo decorado.
     *
     * @param node Nodo a añadir.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    public Graph<T> addNode(ComponentNode<T> node) {
    	NodeLogger<T> decorated = new NodeLogger<>(node, path);
        return super.addNode(decorated);
    }
}

