package testers;

import java.util.LinkedHashMap;

/**
 * La clase NumericData representa un conjunto de datos numéricos utilizados en operaciones matemáticas.
 * Extiende LinkedHashMap para almacenar pares clave-valor, específicamente con claves predefinidas como "op1", "op2" y "result".
 * 
 * Esta clase es útil como contenedor de datos de entrada y salida en flujos de ejecución como grafos de estados.
 * 
 * Claves esperadas:
 * <ul>
 *   <li>"op1": primer operando</li>
 *   <li>"op2": segundo operando</li>
 *   <li>"result": resultado de la operación</li>
 * </ul>
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class NumericData extends LinkedHashMap<String, Integer>{
	private static final long serialVersionUID = 1L;

	/**
     * Constructor que inicializa los operandos y el resultado.
     * 
     * @param op1 Primer operando.
     * @param op2 Segundo operando.
     */
	public NumericData(int op1, int op2) {
		this.put("op1", op1);
		this.put("op2", op2);
		this.put("result", 0);
	}
}
