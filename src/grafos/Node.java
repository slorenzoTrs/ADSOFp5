package grafos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * La clase Node representa un nodo genérico dentro de una estructura de flujo
 * de ejecución, como puede ser un grafo de estados. Cada nodo contiene un nombre,
 * un bloque de código ejecutable, una lista de nodos siguientes y condiciones
 * opcionales para transitar a ellos.
 * 
 * Esta clase permite construir flujos dinámicos de procesamiento, donde cada
 * nodo puede ejecutar una función específica y decidir condicionalmente qué
 * nodo seguir a continuación.
 * 
 * @param <S> Tipo de dato sobre el cual opera la función del nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class Node<S> {
	private String nombre;
	private List<Node<S>> nextNodes = new LinkedList<>();
	private HashMap<String, Predicate<S>> condExecutes = new HashMap<>();
	private Consumer<S> codigo;
	
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
    /**
     * Constructor alternativo de la clase Node sin código asociado.
     * Este nodo podrá ser utilizado como contenedor o ser configurado más adelante.
     * 
     * @param nombre Nombre identificador del nodo.
     */
	public Node(String nombre) {
		this.nombre = nombre;
	}
	
	/**
     * Obtiene el nombre identificador del nodo.
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
     * Obtiene la lista de nodos siguientes a los que se puede transitar desde este nodo.
     * 
     * @return Lista de nodos siguientes.
     */
	public List<Node<S>> getNextNodes() {
		return nextNodes;
	}
	
	/**
     * Añade un nodo como siguiente en el flujo de ejecución desde este nodo.
     * 
     * @param node Nodo a añadir como siguiente.
     */
	public void addNextNode(Node<S> node) {
		if(nextNodes.contains(node)) { return; }
		this.nextNodes.add(node);
	}
	
	/**
     * Añade una condición para ejecutar la transición hacia un nodo específico.
     * 
     * @param node Nombre del nodo de destino.
     * @param condExecute Expresión lambda que representa la condición de transición.
     */
	public void addCondition(String node, Predicate<S> condExecute) {
		this.condExecutes.put(node, condExecute);
	}
	
	/**
     * Devuelve la condición asociada a la transición hacia un nodo específico.
     * 
     * @param node Nombre del nodo de destino.
     * @return Condición de ejecución asociada a esa transición (puede ser null).
     */
	public Predicate<S> getCondition(String node) {
		return this.condExecutes.get(node) ;
	}
	
	/**
     * Devuelve una representación en cadena del nodo, indicando su nombre
     * y cuántos nodos tiene como siguientes.
     * 
     * @return Cadena descriptiva del nodo.
     */
	@Override
	public String toString() {
		String s = "Node " + getNombre() + " (" + getNextNodes().size() + " output nodes)";
		return s;
	}
	
	/**
     * Ejecuta el código asociado al nodo con la entrada proporcionada.
     * 
     * @param input Dato de entrada a procesar.
     * @param debug Si es true, se puede imprimir información de depuración.
     * @return El mismo objeto de entrada, posiblemente modificado.
     */
	public S execute(S input, boolean debug) {
		codigo.accept(input);
		return input;
	}
}
