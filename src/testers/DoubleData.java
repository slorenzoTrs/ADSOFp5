package testers;

/**
 * Clase que representa un dato numérico con su promedio acumulado.
 * Se utiliza como tipo de dato que fluye por el grafo de estados.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class DoubleData implements Comparable<DoubleData>{
    private double value;
    private double average;

    /**
     * Constructor para crear un objeto DoubleData.
     *
     * @param value   Valor original de entrada.
     * @param average Promedio calculado hasta este punto.
     */
    public DoubleData(double value, double average) {
        this.value = value;
        this.average = average;
    }

    /**
     * Obtiene el valor original.
     *
     * @return Valor numérico.
     */
    public double getValue() {
        return value;
    }

    /**
     * Obtiene el promedio acumulado.
     *
     * @return Promedio actual.
     */
    public double getAverage() {
        return average;
    }

    /**
     * Establece un nuevo valor original.
     *
     * @param value Nuevo valor numérico.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Establece un nuevo promedio acumulado.
     *
     * @param average Nuevo valor promedio.
     */
    public void setAverage(double average) {
        this.average = average;
    }

    /**
     * Devuelve una representación en texto del objeto, en el formato:
     * "valor (avg.= promedio)"
     *
     * @return Cadena con el valor y el promedio formateado.
     */
    @Override
    public String toString() {
        return String.format("%.1f (avg.= %.3f)", value, average);
    }

	@Override
	public int compareTo(DoubleData o) {
		if (this.average - o.average != 0) {
			return (int)(this.average - o.average);
		} 
		return (int) (this.value -this.value);
	}
}
