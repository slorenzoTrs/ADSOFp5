package decoradores;

import java.util.ArrayList;
import java.util.List;

import datos.PublicCloneable;
import nodos.ComponentNode;

/**
 * Decorador de nodo que guarda el tiempo de ejecucion de un nodo.
 * 
 * @param <T> Tipo de dato que procesa el nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class NodeProfiler<T extends PublicCloneable<T>> extends NodeDecorator<T> {
    private List<Trace<T>> traces = new ArrayList<>();

    /**
     * Constructor del decorador de profiling.
     * 
     * @param decoratedNode Nodo original a decorar.
     */
    public NodeProfiler(ComponentNode<T> decoratedNode) {
        super(decoratedNode);
    }

    @Override
    public T execute(T input, boolean debug) {
    	long inicio = System.nanoTime();
    	T t = (T)input.clone();
    	T output = super.execute(input, debug);
        long fin = System.nanoTime();
        
        Trace<T> e = new Trace<>(super.getNombre(), (fin - inicio) / 1_000_000.0, t );
        traces.add(e);
    	
        return output;
    }
    
    /**
     * Obtiene las trazas de ejecución guardadas en este nodo .
     * 
     * @return Lista de trazas de este nodo.
     */
    public List<Trace<T>> getTraces() {
    	return traces;
    }

    @Override
    public String toString() {
        return super.toString()+" [profiled]";
    }
}
