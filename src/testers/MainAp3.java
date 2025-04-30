package testers;

import grafos.StateGraph;

public class MainAp3 {
	public static void main(String[] args) {
		StateGraph<NumericData> sg = buildWorkflowIncluded();
		
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
	private static StateGraph<NumericData> buildWorkflowIncluded() {
		StateGraph<NumericData> sg = new StateGraph<>("math2", "Add two numbers, and then square");
		
		sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1")+mo.get("op2")))
		  .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result")*mo.get("result")));
		
		sg.addEdge("sum", "square");
		
		sg.setInitial("sum");
		sg.setFinal("square");
		
		return sg;
	}
	
	private static StateGraph<StringData> buildWorkflow(StateGraph<NumericData> wfNumeric) {
		StateGraph<StringData> sg = new StateGraph<>("replicate", "Replicates a given word");
		
		sg.addWfNode("calculate", wfNumeric)
			.withInjector((StringData sd) -> sd.toNumericData())
			.withExtractor((NumericData nd, StringData sd) -> sd.setTimes(nd.get("result")));
		
		sg.addNode("replicate", sd -> sd.replicate());
		
		sg.addEdge("calculate", "replicate");
		
		sg.setInitial("sum");
		sg.setFinal("square");
		
		return sg;
	}
}
