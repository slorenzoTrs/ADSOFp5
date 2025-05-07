package datos;

/**
 * Clase que representa un valor entero y su suma acumulada.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class CounterRecord implements Comparable<CounterRecord> {
    private int value;
    private int total;

    /**
     * Crea un nuevo registro con un valor dado.
     * 
     * @param value el valor actual
     */
    public CounterRecord(int value) {
        this.value = value;
    }
    
    /**
     * Obtiene el valor del dato.
     * 
     * @return valor entero.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Obtiene el total del dato.
     * 
     * @return total acumulado por los valores.
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Establece el valor total acumulado de los datos.
     * 
     * @param total El valor que se quiere establecer como el total.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return value + " (total = " + total + ")";
    }

	@Override
	public int compareTo(CounterRecord o) {
		if ((this.value - o.value) != 0) {
			return this.value - o.value;
		}
		return this.total-o.total;
	}
}
