package testers;

import grafos.StateGraph;

public class MainAp2 {
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
