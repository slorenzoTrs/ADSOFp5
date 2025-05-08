package testers;

import datos.DoubleData;
import datos.StringData;
import datos.PersonData;
import grafos.StateGraph;
import decoradores.StateGraphLogger;
import decoradores.StateGraphProfiler;

/**
 * Tester adicional del apartado 5 usando datos nuevos.
 * Usa decoradores StateGraphLogger y StateGraphProfiler como en el ejemplo.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class ExtraTestersAp5 {
	/**
     * Método principal que ejecuta tres flujos distintos para probar los decoradores del StateGraph.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        runDoubleDataFlow();   // usa profiler
        runStringDataFlow();   // usa logger
        runPersonDataFlow();   // usa ambos decoradores
    }

    /**
     * Flujo de DoubleData: calcula promedio acumulado.
     */
    private static void runDoubleDataFlow() {
        StateGraph<DoubleData> base = new StateGraph<>("double-flow", "Calcula promedio");
        StateGraphProfiler<DoubleData> sg = new StateGraphProfiler<>(base); // solo profiler

        sg.addNode("average", (DoubleData d) -> {
            double newAvg = (d.getAverage() + d.getValue()) / 2.0;
            d.setAverage(newAvg);
        }).setInitial("average");

        DoubleData input = new DoubleData(8.0, 4.0); // 4.0 es el promedio anterior
        System.out.println("\n" + sg + "\nInput = " + input);
        DoubleData output = sg.run(input, true);
        System.out.println("Result = " + output);
        System.out.println("History = " + sg.history());
    }

    /**
     * Flujo de StringData: repite una palabra las veces indicadas.
     */
    private static void runStringDataFlow() {
        StateGraph<StringData> base = new StateGraph<>("string-flow", "Repite palabras");
        StateGraphLogger<StringData> sg = new StateGraphLogger<>(base, "string_trace.txt");

        sg.addNode("replicate", (StringData s) -> s.replicate())
          .addConditionalEdge("replicate", "replicate", (StringData s) -> s.times() > 0)
          .setInitial("replicate");

        StringData input = new StringData("hi", "", 3);
        System.out.println("\n" + sg + "\nInput = " + input);
        StringData output = sg.run(input, true);
        System.out.println("Result = " + output);
        System.out.println("Datos guardados en string_trace.txt");
    }

    /**
     * Flujo de PersonData: incrementa la edad.
     */
    private static void runPersonDataFlow() {
        StateGraph<PersonData> base = new StateGraph<>("person-flow", "Aumenta edad");
        StateGraphLogger<PersonData> lg = new StateGraphLogger<>(base, "person_trace.txt");
        StateGraphProfiler<PersonData> sg = new StateGraphProfiler<>(lg); // logger + profiler

        sg.addNode("birthday", (PersonData p) -> p.age += 1)
          .setInitial("birthday");

        PersonData input = new PersonData("Elena", 21);
        System.out.println("\n" + sg + "\nInput = " + input);
        PersonData output = sg.run(input, true);
        System.out.println("Result = " + output);
        System.out.println("History = " + sg.history());
    }
}
