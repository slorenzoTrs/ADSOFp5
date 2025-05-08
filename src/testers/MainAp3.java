package testers;

import datos.NumericData;
import datos.StringData;
import grafos.StateGraph;

/**
 * Clase principal que demuestra el uso de la librería de flujos de ejecución basados en grafos.
 * En este ejemplo, se construyen dos grafos de estados:
 * 
 * - Uno numérico (`buildWorkflowIncluded`) que suma dos operandos y eleva el resultado al cuadrado.
 * - Otro de cadenas (`buildWorkflow`) que incorpora el grafo numérico como subgrafo, y utiliza su resultado
 *   para repetir una palabra determinada.
 * 
 * El flujo completo transforma datos de tipo `StringData` en `NumericData`, ejecuta una operación matemática,
 * y luego repite una palabra según el resultado obtenido.
 * 
 * El resultado del flujo se imprime en consola con trazado paso a paso.
 * 
 * Requiere las clases `StateGraph`, `Node`, `NodeG`, `StringData` y `NumericData`.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class MainAp3 {
	public static void main(String[] args) {
		StateGraph<NumericData> sg = buildWorkflowIncluded();
		StateGraph<StringData> sd = buildWorkflow(sg);
		
		System.out.println(sd);
		
		StringData input = new StringData("jamon", "", 2);
		System.out.println("input = " + input);
		StringData output = sd.run(input, true);
		System.out.println("result = " + output);
	}
	
	/**
     * Construye un flujo de trabajo representado por un grafo de estados,
     * donde se realiza la suma de dos números y se eleva al cuadrado el resultado.
     * 
     * @return El grafo de estados configurado para realizar las operaciones matemáticas.
     */
	private static StateGraph<NumericData> buildWorkflowIncluded() {
		StateGraph<NumericData> sg = new StateGraph<>("math1", "Add two numbers, and square if even");
		
		sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1")+mo.get("op2")))
		  .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result")*mo.get("result")));
		
		sg.addConditionalEdge("sum", "square", (NumericData mo) ->mo.get("result")%2 == 0);
		
		sg.setInitial("sum");
		sg.setFinal("square");
		
		return sg;
	}
	
	/**
     * Construye un grafo de estados que procesa una palabra y un número de repeticiones.
     * 
     * Incorpora un grafo numérico como subflujo, utilizando sus resultados para
     * decidir cuántas veces se repetirá una palabra.
     * 
     * @param wfNumeric Subgrafo numérico que calcula el número de repeticiones.
     * @return Grafo de estados completo que procesa datos tipo StringData.
     */
	private static StateGraph<StringData> buildWorkflow(StateGraph<NumericData> wfNumeric) {
		StateGraph<StringData> sg = new StateGraph<>("replicate", "Replicates a given word");
		
		sg.addWfNode("calculate", wfNumeric)
			.withInjector((StringData sd) -> sd.toNumericData())
			.withExtractor((NumericData nd, StringData sd) -> sd.setTimes(nd.get("result")));
		
		sg.addNode("replicate", sd -> sd.replicate());
		
		sg.addEdge("calculate", "replicate")
		  .addConditionalEdge("replicate", "replicate", sd -> sd.times()>0);
		
		sg.setInitial("calculate");
		
		return sg;
	}
}
