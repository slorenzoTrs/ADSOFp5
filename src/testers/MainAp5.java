package testers;

import datos.NumericData;
import decoradores.StateGraphLogger;
import decoradores.StateGraphProfiler;
import grafos.StateGraph;

/**
 * La clase MainAp5 actúa como tester del apartado 5 de la práctica para probar un flujo de 
 * operaciones matemáticas implementado en grafos decorados.
 * 
 * Ejecuta el flujo con un conjunto de datos numéricos como entrada y muestra el resultado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class MainAp5 {
	/**
     * Método principal que lanza la ejecución del grafo de estados con datos de entrada específicos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
        StateGraph<NumericData> g = new StateGraph<>("loop-down", "Get a number, and decrease if positive");
        StateGraphLogger<NumericData> lg = new StateGraphLogger<>(g, "traces.txt"); // guarda info en traces.txt
        StateGraphProfiler<NumericData> sg = new StateGraphProfiler<>(lg);           // almacena timestamps
        
        sg.addNode("decrease", (NumericData mo) -> mo.put("op1", mo.get("op1") - 1))  // decrementa op1
          .addConditionalEdge("decrease", "decrease", (NumericData mo) -> mo.get("op1") > 0) // decrementa hasta 0
          .setInitial("decrease");

        NumericData input = new NumericData(3, 0);
        System.out.println(sg + "\ninput = " + input);
        NumericData output = sg.run(input, true);
        System.out.println("result = " + output);
        System.out.println("history = " + sg.history());

    }
}
