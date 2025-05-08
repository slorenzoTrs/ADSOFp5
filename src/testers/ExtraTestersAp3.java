package testers;

import grafos.StateGraph;

/**
 * Clase principal que prueba flujos complejos compuestos por subgrafos con tipos de estado distintos.
 * 
 * Se construyen dos flujos principales:
 * 
 * - `buildWorkflow1`: flujo principal de tipo `TextState` que contiene un subgrafo `buildNumericSubgraph`
 *   con tipo de estado `CounterState`. El flujo convierte un texto en longitud, y lo multiplica.
 * 
 * - `buildWorkflow2`: flujo principal de tipo `CounterState` que contiene dos subgrafos:
 *      - Uno con el mismo tipo de estado (`CounterState`), que aumenta el contador.
 *      - Otro con tipo distinto (`FlagState`), que activa o desactiva una bandera según el valor del contador.
 * 
 * Estos flujos demuestran el uso de subgrafos con estados variados, condicionales y bucles finitos.
 * 
 * Requiere las clases `StateGraph`, `Node`, `NodeG` y los estados personalizados usados en cada flujo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class ExtraTestersAp3 {

    public static void main(String[] args) {
        StateGraph<TextState> workflow1 = buildWorkflow1();
        System.out.println(workflow1);
        TextState textInput = new TextState("hola");
        System.out.println("input = " + textInput);
        TextState textOutput = workflow1.run(textInput, true);
        System.out.println("result = " + textOutput);

        StateGraph<CounterState> workflow2 = buildWorkflow2();
        System.out.println("\n" + workflow2);
        CounterState counterInput = new CounterState(1);
        System.out.println("input = " + counterInput);
        CounterState counterOutput = workflow2.run(counterInput, true);
        System.out.println("result = " + counterOutput);
    }

    // ---------- Estados personalizados ----------

    static class TextState {
        String text;
        int length;

        public TextState(String text) {
            this.text = text;
            this.length = text.length();
        }

        public CounterState toCounterState() {
            return new CounterState(length);
        }

        public void setLength(int val) {
        	int dif = val - this.length;
        	// Resetear el texto
        	if(dif < 0) {
        		text = text.substring(0, dif);
        	}
        	else if(dif > 0) {
        		for(int i = 0; i < dif; i++) {
        			text += text.charAt(length - 1);
        		}
        	}
            this.length = val;
        }

        @Override
        public String toString() {
            return "TextState[text=" + text + ", length=" + length + "]";
        }
    }

    static class CounterState {
        int value;

        public CounterState(int v) {
            this.value = v;
        }

        public void increment() {
            value++;
        }

        @Override
        public String toString() {
            return "CounterState[value=" + value + "]";
        }
    }

    static class FlagState {
        boolean flag;

        public void setFlag(boolean value) {
            this.flag = value;
        }

        @Override
        public String toString() {
            return "FlagState[flag=" + flag + "]";
        }
    }

    // ---------- Subgrafico numérico usado dentro de flujo1 ----------
    private static StateGraph<CounterState> buildNumericSubgraph() {
        StateGraph<CounterState> sg = new StateGraph<>("numericSub", "Doubles the counter");
        sg.addNode("double", cs -> cs.value *= 2);
        sg.setInitial("double");
        return sg;
    }

    // ---------- Flujo 1: Usa TextState con subgrafo CounterState ----------
    private static StateGraph<TextState> buildWorkflow1() {
        StateGraph<TextState> sg = new StateGraph<>("textLengthProcessor", "Processes text length");

        sg.addWfNode("compute", buildNumericSubgraph())
          .withInjector(TextState::toCounterState)
          .withExtractor((CounterState cs, TextState ts) -> ts.setLength(cs.value));

        sg.addNode("log", ts -> System.out.println("Computed value: " + ts.length));

        sg.addEdge("compute", "log");
        sg.setInitial("compute");
        sg.setFinal("log");

        return sg;
    }

    // ---------- Subgrafo del mismo tipo: aumenta el contador ----------
    private static StateGraph<CounterState> buildIncrementSubgraph() {
        StateGraph<CounterState> sg = new StateGraph<>("incSub", "Increments up to 3");
        sg.addNode("inc", cs -> cs.increment());

        sg.addConditionalEdge("inc", "inc", cs -> cs.value < 3);
        sg.setInitial("inc");
        sg.setFinal("inc");

        return sg;
    }

    // ---------- Subgrafo con tipo diferente: activa bandera si contador es impar ----------
    private static StateGraph<FlagState> buildFlagSubgraph() {
        StateGraph<FlagState> sg = new StateGraph<>("flagSub", "Set flag if true");
        sg.addNode("check", fs -> fs.setFlag(true));
        sg.setInitial("check");
        sg.setFinal("check");
        return sg;
    }

    // ---------- Flujo 2: Usa CounterState con dos subgrafos ----------
    private static StateGraph<CounterState> buildWorkflow2() {
        StateGraph<CounterState> sg = new StateGraph<>("counterProcessor", "Uses subgraphs of same and different types");

        sg.addWfNode("incrementer", buildIncrementSubgraph())
          .withInjector(cs -> cs)  // mismo tipo
          .withExtractor((CounterState sub, CounterState main) -> main.value = sub.value);

        sg.addWfNode("flagChecker", buildFlagSubgraph())
          .withInjector(cs -> {
              FlagState fs = new FlagState();
              fs.setFlag(cs.value % 2 != 0);
              return fs;
          })
          .withExtractor((FlagState fs, CounterState cs) -> {
              if (fs.flag) cs.value += 5;
          });

        sg.addEdge("incrementer", "flagChecker");
        sg.setInitial("incrementer");
        sg.setFinal("flagChecker");

        return sg;
    }
}

