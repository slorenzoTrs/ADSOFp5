package grafos;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import nodos.ComponentNode;
import nodos.NodeG;

/**
 * Interfaz general para grafos que se pueden ejecutar con un input.
 * Se usa para aplicar el patrón Decorator sobre distintos tipos de grafos.
 *
 * @param <T> Tipo de dato que procesa el grafo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public interface Graph<T> {
    /**
     * Ejecuta el grafo desde su nodo inicial con un input.
     *
     * @param input Input de entrada para ejecutar el grafo.
     * @param trace Si es true, imprime trazado de la ejecución.
     * @return Resultado tras ejecutar el grafo.
     */
    T run(T input, boolean trace);
    
    /**
     * Añade un nodo al grafo con su código asociado.
     * 
     * @param nombreNodo Nombre identificador del nodo.
     * @param codNodo Código que se ejecutará al activar el nodo.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
    public Graph<T> addNode(String nombreNodo, Consumer<T> codNodo);
    
    /**
     * Añade un nodo al grafo con su código asociado.
     * 
     * @param node Nodo que se desea añadir.
     * @return Referencia al grafo actual para permitir llamadas encadenadas.
     */
    public Graph<T> addNode(ComponentNode<T> node);

    /**
     * Devuelve el nombre identificador del grafo.
     *
     * @return Nombre del grafo decorado.
     */
    String getNombre();

    /**
     * Devuelve la descripción del grafo decorado.
     *
     * @return Descripción del grafo.
     */
    String getDescripcion();

    /**
     * Añade un nodo-grafo al grafo decorado.
     *
     * @param <U> Tipo del grafo hijo.
     * @param nombreGrafo Nombre del nodo-grafo.
     * @param grafo Grafo hijo a insertar.
     * @return NodoG creado e insertado en el grafo.
     */
    <U> NodeG<T, U> addWfNode(String nombreGrafo, StateGraph<U> grafo);

    /**
     * Añade una transición directa entre dos nodos del grafo decorado.
     *
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    StateGraph<T> addEdge(String origen, String destino);

    /**
     * Añade una transición condicional entre dos nodos del grafo decorado.
     *
     * @param origen Nombre del nodo de origen.
     * @param destino Nombre del nodo de destino.
     * @param condExecute Condición que debe cumplirse para la transición.
     * @return El grafo decorado para permitir llamadas encadenadas.
     */
    StateGraph<T> addConditionalEdge(String origen, String destino, Predicate<T> condExecute);

    /**
     * Define el nodo inicial del grafo decorado.
     *
     * @param init Nombre del nodo inicial.
     */
    void setInitial(String init);

    /**
     * Define el nodo final del grafo decorado.
     *
     * @param fin Nombre del nodo final.
     */
    void setFinal(String fin);

    /**
     * Obtiene el nodo inicial del grafo decorado.
     *
     * @return Nodo inicial.
     */
    ComponentNode<T> getInitial();

    /**
     * Obtiene el nodo final del grafo decorado.
     *
     * @return Nodo final.
     */
    ComponentNode<T> getFinal();

    /**
     * Ejecuta el flujo del grafo desde el nodo inicial de forma recursiva.
     *
     * @param data Dato de entrada.
     * @param debug Si es true, se imprimen pasos intermedios.
     * @return Resultado tras ejecutar el flujo.
     */
    T executeFrom(T data, boolean debug);
    
    /**
     * Obtiene los nodos del grafo.
     *
     * @return Colección con todos los nodos del grafo actual.
     */
	public Collection<ComponentNode<T>> getNodes();

    /**
     * Devuelve una representación textual del grafo decorado.
     *
     * @return Cadena representando el grafo.
     */
    @Override
    String toString();
}
