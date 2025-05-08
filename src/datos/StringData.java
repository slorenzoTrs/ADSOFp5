package datos;

/**
 * La clase StringData encapsula una estructura de datos que contiene:
 * una palabra, un número de repeticiones, y un resultado acumulado.
 * 
 * Esta clase se utiliza como entrada y salida en flujos de ejecución
 * que manipulan cadenas, como repetir una palabra múltiples veces.
 * 
 * También incluye una conversión a un tipo numérico (`NumericData`),
 * para su uso en subflujos que operan con datos numéricos.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class StringData {
	private String word;
	private String result;
	private int times;
	
	/**
     * Constructor de la clase StringData.
     * 
     * @param word Palabra base que se desea repetir.
     * @param result Resultado inicial acumulado (generalmente vacío).
     * @param times Número de veces que se desea repetir la palabra.
     */
	public StringData(String word, String result, int times) {
		this.word = word;
		this.result = result;
		this.times = times;
	}
	
	/**
     * Convierte el objeto StringData a un objeto NumericData,
     * utilizando el campo `times` como primer operando.
     * 
     * @return Objeto NumericData con el número de repeticiones como dato numérico.
     */
	public NumericData toNumericData() {
		NumericData nmd = new NumericData(times, 0);
		return nmd;
	}
	
	/**
     * Realiza una repetición de la palabra base.
     * Decrementa el contador de repeticiones y concatena la palabra al resultado.
     */
	public void replicate() {
		this.times--;
		this.result += this.word;
	}
	
	/**
     * Establece el número de repeticiones a realizar.
     * 
     * @param times Nuevo valor para el contador de repeticiones.
     */
	public void setTimes(int times) {
		this.times = times;
	}
	
	/**
     * Devuelve el número actual de repeticiones restantes.
     * 
     * @return Número de repeticiones.
     */
	public int times() {
		return times;
	}
	
	/**
     * Establece la palabra a repetir.
     * 
     * @param word Nuevo valor para la palabra.
     */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
     * Devuelve la palabra.
     * 
     * @return La palabra que se repite.
     */
	public String word() {
		return word;
	}
	
	/**
     * Devuelve una representación textual del estado actual del objeto.
     * 
     * @return Cadena con los valores de los campos internos.
     */
	@Override
	public String toString() {
		return "word: " + word + ", times: " + times + ", result: " + result;
	}
}
