package testers;

import datos.NumericData;
import grafos.StateGraph;

/**
 * La clase MainAp2 actúa como tester del apartado 2 de la práctica para probar un flujo de 
 * operaciones matemáticas implementado mediante un grafo de estados (StateGraph).
 * Crea un flujo que suma dos números y luego eleva el resultado al cuadrado, teniendo en cuenta las condiciones que 
 * pueden tener las aristas.
 * 
 * Ejecuta el flujo con un conjunto de datos numéricos como entrada y muestra el resultado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class MainAp2 {
	/**
     * Método principal que lanza la ejecución del grafo de estados con datos de entrada específicos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
		StateGraph<NumericData> sg = buildWorkflow();
		
		System.out.println(sg);
		
		NumericData input = new NumericData(2, 3);
		System.out.println("input = " + input);
		NumericData output = sg.run(input, true);
		System.out.println("result = " + output);
		
		input = new NumericData(2, 2);
		System.out.println("input = " + input);
		output = sg.run(input, true);
		System.out.println("result = " + output);
	}
	
	/**
     * Construye un flujo de trabajo representado por un grafo de estados,
     * donde se realiza la suma de dos números y se eleva al cuadrado el resultado.
     * 
     * @return El grafo de estados configurado para realizar las operaciones matemáticas.
     */
	private static StateGraph<NumericData> buildWorkflow() {
		StateGraph<NumericData> sg = new StateGraph<>("math1", "Add two numbers, and square if even");
		
		sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1")+mo.get("op2")))
		  .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result")*mo.get("result")));
		
		sg.addConditionalEdge("sum", "square", (NumericData mo) ->mo.get("result")%2 == 0);
		
		sg.setInitial("sum");
		sg.setFinal("square");
		
		return sg;
	}
}
