package testers;

import grafos.StateGraph;

/**
 * La clase MainAp1 actúa como tester del apartado 1 de la práctica para probar un flujo de 
 * operaciones matemáticas implementado mediante un grafo de estados (StateGraph).
 * Crea un flujo que suma dos números y luego eleva el resultado al cuadrado.
 * 
 * Ejecuta el flujo con un conjunto de datos numéricos como entrada y muestra el resultado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class MainAp1 {
	
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
	}
	
	/**
     * Construye un flujo de trabajo representado por un grafo de estados,
     * donde se realiza la suma de dos números y se eleva al cuadrado el resultado.
     * 
     * @return El grafo de estados configurado para realizar las operaciones matemáticas.
     */
	private static StateGraph<NumericData> buildWorkflow() {
		StateGraph<NumericData> sg = new StateGraph("math2", "Add two numbers, and then square");
		
		sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1")+mo.get("op2")))
		  .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result")*mo.get("result")));
		
		sg.addEdge("sum", "square");
		
		sg.setInitial("sum");
		sg.setFinal("square");
		
		return sg;
	}
}
