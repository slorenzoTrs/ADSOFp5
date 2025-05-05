package decoradores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;

import nodos.ComponentNode;

/**
 * Decorador de nodo que permite registrar en un fichero la ejecución de cada nodo.
 * 
 * @param <T> Tipo de dato que procesa el nodo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class NodeLogger<T> extends NodeDecorator<T> {
    private String path;

    /**
     * Constructor del decorador de logging.
     * 
     * @param decoratedNode Nodo original a decorar.
     * @param path Ruta del fichero de log.
     */
    public NodeLogger(ComponentNode<T> decoratedNode, String path) {
        super(decoratedNode);
        this.path = path;
    }

    @Override
    public T execute(T input, boolean debug) {
        T result = super.execute(input, debug);
        extra(result);
        
        return result;
    }

    /**
     * Acción adicional: escribe mensaje indicando fin de ejecución de nodo.
     */
    public void extra(T output) {
        String infoEjecucion = "["+LocalDate.now()+" - "+LocalTime.now()+"] node "+ super.getNombre()+" executed, with output: "+output.toString()+"\n";
        try {
            Path archivo = Paths.get(path);
            Files.writeString(archivo, infoEjecucion, StandardOpenOption.APPEND, StandardOpenOption.CREATE); 
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
    	return super.toString()+" [logged]";

    }
}