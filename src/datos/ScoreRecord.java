package datos;

/**
 * Clase que representa una puntuación y si ha sido la más alta hasta el momento.
 */
public class ScoreRecord implements Comparable<ScoreRecord> {
    private int score;
    private boolean highScore;

    /**
     * Crea un nuevo registro con una puntuación dada.
     *
     * @param score la puntuación actual
     */
    public ScoreRecord(int score) {
        this.score = score;
        this.highScore = false;
    }

    /**
     * Devuelve la puntuación actual.
     *
     * @return la puntuación
     */
    public int getScore() {
        return score;
    }

    /**
     * Indica si esta puntuación ha sido la más alta hasta el momento.
     *
     * @return true si es la puntuación más alta, false en caso contrario
     */
    public boolean isHighScore() {
        return highScore;
    }

    /**
     * Establece si esta puntuación es la más alta.
     *
     * @param highScore true si es la puntuación más alta
     */
    public void setHighScore(boolean highScore) {
        this.highScore = highScore;
    }

    /**
     * Devuelve una representación en texto del registro, marcando si es un "HIGH SCORE".
     *
     * @return cadena de texto representando el objeto
     */
    @Override
    public String toString() {
        return score + (highScore ? " (High Score)" : "");
    }

	@Override
	public int compareTo(ScoreRecord o) {
		return this.score - o.score;
	}
}