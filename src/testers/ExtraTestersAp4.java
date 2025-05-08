package testers;

import java.util.List;

import datos.CounterRecord;
import datos.LevelProgress;
import datos.ScoreRecord;
import grafos.StreamingStateGraph;

/**
 * Clase que contiene testers para flujos de datos en streaming con diferentes tipos de estado.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class ExtraTestersAp4 {

    /**
     * Método principal que ejecuta tres flujos distintos: contadores, puntuaciones y niveles.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        runCounterWorkflow();
        runScoreWorkflow();
        runLevelWorkflow();
    }

    /**
     * Flujo que acumula la suma total de valores recibidos.
     */
    private static void runCounterWorkflow() {
        StreamingStateGraph<CounterRecord> sg = new StreamingStateGraph<>("counterFlow", "Accumulate total values");

        sg.addNode("sumUp", (List<CounterRecord> history) -> {
            int total = history.stream().mapToInt(CounterRecord::getValue).sum();
            history.getLast().setTotal(total);
        });

        sg.setInitial("sumUp");
        sg.setFinal("sumUp");

        System.out.println("\n");
        System.out.println(sg);

        List.of(3, 5, 2, 6).forEach(n -> {
            CounterRecord input = new CounterRecord(n);
            System.out.println("Input: " + input);
            sg.run(input, true);
        });

        System.out.println("History: " + sg.history());
    }

    /**
     * Flujo que marca si una puntuación ha sido la más alta hasta ese momento.
     */
    private static void runScoreWorkflow() {
        StreamingStateGraph<ScoreRecord> sg = new StreamingStateGraph<ScoreRecord>("scoreFlow", "Mark highest score");

        sg.addNode("checkHigh", (List<ScoreRecord> history) -> {
            int max = history.stream().mapToInt(ScoreRecord::getScore).max().orElse(0);
            ScoreRecord last = history.getLast();
            last.setHighScore(last.getScore() >= max);
        });

        sg.setInitial("checkHigh");
        sg.setFinal("checkHigh");

        System.out.println("\n");
        System.out.println(sg);

        List.of(20, 35, 18, 40, 38).forEach(n -> {
            ScoreRecord input = new ScoreRecord(n);
            System.out.println("Input: " + input);
            sg.run(input, true);
        });

        System.out.println("History: " + sg.history());
    }

    /**
     * Flujo que calcula el porcentaje de progreso según el nivel más alto alcanzado.
     */
    private static void runLevelWorkflow() {
        StreamingStateGraph<LevelProgress> sg = new StreamingStateGraph<>("levelFlow", "Calculate progress percentage");

        sg.addNode("calcProgress", (List<LevelProgress> history) -> {
            int max = history.stream().mapToInt(LevelProgress::getLevel).max().orElse(1);
            LevelProgress last = history.getLast();
            last.setProgressPercent((last.getLevel() * 100.0) / max);
        });

        sg.setInitial("calcProgress");
        sg.setFinal("calcProgress");

        System.out.println("\n");
        System.out.println(sg);

        List.of(1, 5, 3, 2, 4).forEach(n -> {
            LevelProgress input = new LevelProgress(n);
            System.out.println("Input: " + input);
            sg.run(input, true);
        });

        System.out.println("History: " + sg.history());
    }
}
