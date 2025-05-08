package testers;

import java.util.Random;

import datos.DoubleData;
import datos.PersonData;
import datos.StringData;
import grafos.StateGraph;

/**
 * Tester principal para probar flujos con condiciones usando distintos tipos de datos:
 * DoubleData: duplica el valor si el promedio es bajo.
 * PersonData: incrementa la edad si es menor de edad.
 * StringData: repite palabras mientras queden repeticiones.
 * 
 * Se prueba el uso de addConditionalEdge y ejecución controlada por condiciones.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class ExtraTestersAp2 {
	/**
     * Método principal que lanza la ejecución del grafo de estados con datos de entrada específicos.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
        System.out.println("DoubleData Loop");
        StateGraph<DoubleData> g1 = buildDoubleDecrementWorkflow();
        System.out.println(g1);
        g1.run(new DoubleData(0.0, 0.0), true);

        System.out.println("\nDoubleData Conditional (por promedio)");
        StateGraph<DoubleData> g2 = buildDoubleConditionalWorkflow();
        System.out.println(g2);
        g2.run(new DoubleData(5.0, 2.5), true);
        g2.run(new DoubleData(2.0, 7.0), true);

        System.out.println("\nPersonData Age Increase");
        StateGraph<PersonData> g3 = buildPersonDataWorkflow();
        System.out.println(g3);
        g3.run(new PersonData("Ana", 16), true);
        g3.run(new PersonData("Luis", 20), true);

        System.out.println("\nStringData Repeat Workflow");
        StateGraph<StringData> g4 = buildStringDataWorkflow();
        System.out.println(g4);
        g4.run(new StringData("Hi", "", 3), true);
    }

    /**
     * Genera un número aleatorio entre 1 y 5,
     * lo guarda como value en DoubleData y lo decrementa hasta 0.
     */
    private static StateGraph<DoubleData> buildDoubleDecrementWorkflow() {
        StateGraph<DoubleData> g = new StateGraph<>("doubleRandLoop", "Genera aleatorio y decrementa hasta cero");

        g.addNode("random", (DoubleData dd) -> {
            Random r = new Random();
            double val = 1 + r.nextInt(5); // Entero entre 1 y 5
            dd.setValue(val);
        });

        g.addNode("decrement", (DoubleData dd) -> {
            dd.setValue(dd.getValue() - 1);
        });

        g.addEdge("random", "decrement");
        g.addConditionalEdge("decrement", "decrement", (DoubleData dd) -> dd.getValue() > 0);

        g.setInitial("random");

        return g;
    }

    /**
     * Flujo con DoubleData que duplica el valor si el promedio es bajo.
     */
    private static StateGraph<DoubleData> buildDoubleConditionalWorkflow() {
        StateGraph<DoubleData> g = new StateGraph<>("doubleCheck", "Duplica si promedio < 5.0 hasta que el valor supere 10");
        
        g.addNode("check", (_) -> {
        });
        g.addNode("double", (DoubleData dd) -> {
            dd.setValue(dd.getValue() * 2);
        });
        
        g.addConditionalEdge("check", "double", (DoubleData dd) -> dd.getAverage() < 5.0);
        g.addConditionalEdge("double", "check", (DoubleData dd) -> dd.getValue() < 10.0);

        g.setInitial("check");

        return g;
    }

    /**
     * Flujo para PersonData que incrementa la edad si es menor de edad.
     */
    private static StateGraph<PersonData> buildPersonDataWorkflow() {
        StateGraph<PersonData> g = new StateGraph<>("personCheck", "Incrementa edad mientras sea < 18");

        g.addNode("verify", (_) -> {
            
        });

        g.addNode("increase", (PersonData pd) -> {
            pd.age += 1;
        });

        g.addConditionalEdge("verify", "increase", (PersonData pd) -> pd.age < 18);
        g.addEdge("increase", "verify");

        g.setInitial("verify");
        return g;
    }

    /**
     * Flujo con StringData que concatena la palabra mientras haya repeticiones.
     */
    private static StateGraph<StringData> buildStringDataWorkflow() {
        StateGraph<StringData> g = new StateGraph<>("strRepeat", "Concatena palabra hasta times == 0");

        g.addNode("repeat", (StringData sd) -> {
            sd.setResult(sd.getResult() + sd.word());
            sd.setTimes(sd.times() - 1);
        });

        g.addConditionalEdge("repeat", "repeat", (StringData sd) -> sd.times() > 0);

        g.setInitial("repeat");
        return g;
    }
}