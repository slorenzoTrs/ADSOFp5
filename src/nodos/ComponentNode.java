package nodos;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Interfaz para nodos ejecutables dentro del grafo.
 * Se usa para permitir decoración del comportamiento de ejecución de nodos.
 *
 * @param <T> Tipo de dato que procesa el nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public interface ComponentNode<S> {
    /**
     * Ejecuta el código asociado al nodo.
     *
     * @param input Dato de entrada a procesar.
     * @param debug Si es true, se puede imprimir información de depuración.
     * @return El mismo objeto de entrada, posiblemente modificado.
     */
	public S execute(S input, boolean debug);
	
	/**
     * Obtiene el nombre identificador del nodo.
     * 
     * @return Nombre del nodo.
     */
	public String getNombre();

    /**
     * Devuelve una representación en cadena del nodo.
     *
     * @return Cadena descriptiva del nodo.
     */
    @Override
    String toString();

    /**
     * Devuelve la función de código asociada al nodo.
     *
     * @return Función que representa el comportamiento del nodo.
     */
    Consumer<S> getCodigo();

    /**
     * Obtiene la lista de nodos siguientes.
     *
     * @return Lista de nodos siguientes.
     */
    List<ComponentNode<S>> getNextNodes();

    /**
     * Añade un nodo como siguiente en el flujo de ejecución.
     *
     * @param node Nodo a añadir.
     */
    void addNextNode(ComponentNode<S> node);

    /**
     * Añade una condición para ejecutar la transición hacia un nodo específico.
     *
     * @param node Nombre del nodo de destino.
     * @param condExecute Expresión lambda que representa la condición.
     */
    void addCondition(String node, Predicate<S> condExecute);

    /**
     * Devuelve la condición asociada a la transición hacia un nodo específico.
     *
     * @param node Nombre del nodo destino.
     * @return Condición asociada (puede ser null).
     */
    Predicate<S> getCondition(String node);
}