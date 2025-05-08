package testers;

import java.util.List;

import datos.DoubleData;
import grafos.StreamingStateGraph;

/**
 * La clase MainAp4 actúa como tester del apartado 4 de la práctica para probar un flujo de 
 * operaciones matemáticas implementado mediante un grafo de estados que guarda su estado en un historial al ejecutarse (StreamingStateGraph).
 * 
 * Ejecuta el flujo con un conjunto de datos numéricos como entrada y muestra el resultado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class MainAp4 {
	/**
     * Método principal que lanza la ejecución del grafo de estados con datos de entrada específicos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
        // Crear el flujo con el nodo average
        StreamingStateGraph<DoubleData> sg = buildWorkflow();
        
        System.out.println(sg);

        // Ejecutar entradas secuenciales
        double[] entradas = {1, 5, 2, 4};
        for (double valor : entradas) {
            DoubleData input = new DoubleData(valor, 0.0);
            System.out.println("Workflow input = " + input);
            sg.run(input, true); // true → modo trazado
        }

        // Mostrar historial final
        System.out.println("History=" + sg.history());
    }

    /**
     * Construye el flujo con el nodo average.
     * 
     * @return Un StreamingStateGraph con el flujo configurado.
     */
    public static StreamingStateGraph<DoubleData> buildWorkflow() {
        StreamingStateGraph<DoubleData> graph = new StreamingStateGraph<>("average", "Calculates the average of incomming data");
        graph.addNode("average", (List<DoubleData> dd) -> {
            double suma = 0.0;
            
            for (DoubleData v : dd) {
                suma += v.getValue();
            }
            dd.getLast().setAverage(suma / dd.size());
        });
        graph.setInitial("average");
        return graph;
    }
}
