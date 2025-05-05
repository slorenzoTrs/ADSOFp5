package decoradores;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import nodos.ComponentNode;

/**
 * Decorador abstracto para nodos que permite añadir funcionalidades extra.
 *
 * @param <S> Tipo de dato que procesa el nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public abstract class NodeDecorator<S> implements ComponentNode<S> {
    protected ComponentNode<S> decoratedNode;

    /**
     * Constructor del decorador.
     *
     * @param decoratedNode Nodo original a decorar.
     */
    public NodeDecorator(ComponentNode<S> decoratedNode) {
        this.decoratedNode = decoratedNode;
    }

    @Override
    public S execute(S input, boolean debug) {
        return decoratedNode.execute(input, debug);
    }
    
    /**
     * Obtiene el nodo decorado por el actual decorador.
     * 
     * @return Nodo decorado dentro del actual decorador.
     */
    public ComponentNode<S> getDecoratedNode() {
    	return decoratedNode;
    }
    
    /**
     * Obtiene el nombre identificador del nodo.
     * 
     * @return Nombre del nodo.
     */
    @Override
    public String getNombre() {
    	return decoratedNode.getNombre();
    }
    
    /**
     * Devuelve una representación en cadena del nodo, indicando su nombre
     * y cuántos nodos tiene como siguientes.
     * 
     * @return Cadena descriptiva del nodo.
     */
	@Override
	public String toString() {
		return decoratedNode.toString();
	}
	
	/**
     * Devuelve la función de código asociada al nodo decorado.
     *
     * @return Función que representa el comportamiento del nodo decorado.
     */
    @Override
    public Consumer<S> getCodigo() {
        return decoratedNode.getCodigo();
    }

    /**
     * Devuelve la lista de nodos siguientes a los que se puede transitar desde el nodo decorado.
     *
     * @return Lista de nodos siguientes.
     */
    @Override
    public List<ComponentNode<S>> getNextNodes() {
        return decoratedNode.getNextNodes();
    }

    /**
     * Añade un nodo como siguiente en el flujo de ejecución del nodo decorado.
     *
     * @param node Nodo a añadir como siguiente.
     */
    @Override
    public void addNextNode(ComponentNode<S> node) {
        decoratedNode.addNextNode(node);
    }

    /**
     * Añade una condición de transición hacia un nodo específico en el nodo decorado.
     *
     * @param node Nombre del nodo destino.
     * @param condExecute Condición de ejecución para transitar a dicho nodo.
     */
    @Override
    public void addCondition(String node, Predicate<S> condExecute) {
        decoratedNode.addCondition(node, condExecute);
    }

    /**
     * Devuelve la condición asociada a la transición hacia un nodo específico desde el nodo decorado.
     *
     * @param node Nombre del nodo de destino.
     * @return Condición de transición, o {@code null} si no existe.
     */
    @Override
    public Predicate<S> getCondition(String node) {
        return decoratedNode.getCondition(node);
    }
}