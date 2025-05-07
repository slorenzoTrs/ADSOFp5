package datos;

/**
 * Clase que representa el progreso de un nivel y su porcentaje relativo.
 */
public class LevelProgress implements Comparable<LevelProgress> {
    private int level;
    private double progressPercent;

    /**
     * Crea un nuevo progreso de nivel con el nivel actual.
     *
     * @param level nivel alcanzado
     */
    public LevelProgress(int level) {
        this.level = level;
        this.progressPercent = 0.0;
    }

    /**
     * Devuelve el nivel alcanzado.
     *
     * @return el nivel actual
     */
    public int getLevel() {
        return level;
    }

    /**
     * Devuelve el porcentaje de progreso calculado con respecto al nivel más alto.
     *
     * @return porcentaje de progreso
     */
    public double getProgressPercent() {
        return progressPercent;
    }

    /**
     * Establece el porcentaje de progreso.
     *
     * @param progressPercent nuevo porcentaje de progreso
     */
    public void setProgressPercent(double progressPercent) {
        this.progressPercent = progressPercent;
    }

    /**
     * Devuelve una representación en texto del nivel y el porcentaje de progreso.
     *
     * @return cadena de texto representando el objeto
     */
    @Override
    public String toString() {
        return "Level " + level + " (" + String.format("%.2f", progressPercent) + "% progress)";
    }

	@Override
	public int compareTo(LevelProgress o) {
		if ((this.level - o.level) != 0) {
			return (this.level - o.level);
		}
		return (int) (this.progressPercent - this.progressPercent);
	}
}
