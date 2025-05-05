package nodos;

import java.util.function.BiConsumer;
import java.util.function.Function;

import grafos.StateGraph;

/**
 * La clase NodeG representa un nodo especializado dentro de un grafo de estados que
 * encapsula otro grafo (subgrafo) de tipo StateGraph. Permite la ejecución de un flujo
 * anidado como si fuera un único nodo dentro de un flujo principal.
 * 
 * Utiliza funciones de inyección y extracción para transformar los datos entre el tipo
 * del grafo principal y el tipo del subgrafo, facilitando la integración entre diferentes tipos.
 * 
 * @param <S> Tipo de dato del grafo principal (contenedor).
 * @param <V> Tipo de dato del grafo encapsulado (interno).
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class NodeG<S, V> extends Node<S>{
	private StateGraph<V> graph;
	private Function<S, V> inyectorGenerico;
	private BiConsumer<V, S> extractorGenerico;

	/**
     * Constructor de la clase NodeG.
     * 
     * @param nombre Nombre identificador del nodo.
     * @param grafo Subgrafo que será ejecutado al activarse este nodo.
     */
	public NodeG(String nombre, StateGraph<V> grafo) {
		super(nombre);
		this.graph = grafo;
	}

	/**
     * Ejecuta el subgrafo encapsulado con una entrada transformada mediante el inyector,
     * y aplica los resultados a la entrada original mediante el extractor.
     * 
     * @param input Entrada sobre la que se ejecuta el nodo.
     * @param debug Si es true, imprime el trazado de ejecución del subgrafo.
     * @return El mismo objeto de entrada, posiblemente modificado.
     */
	@Override
	public S execute(S input, boolean debug) {
		V dataReturn;
		dataReturn = graph.run(getInjector().apply(input), debug);
		getExtractor().accept(dataReturn, input);
		return input;
	}
	
	/**
     * Define la función inyectora que transforma el dato del grafo principal al tipo del subgrafo.
     * 
     * @param injector Función de transformación S -> V.
     * @return Referencia a este nodo para permitir llamadas encadenadas.
     */
	public NodeG<S, V> withInjector(Function<S, V> injector) {
		this.inyectorGenerico = injector;
		return this;
	}

	/**
     * Define la función extractora que toma el resultado del subgrafo y lo aplica a la entrada original.
     * 
     * @param extractor Bi-función que actualiza el dato original con el resultado del subgrafo.
     * @return Referencia a este nodo para permitir llamadas encadenadas.
     */
	public NodeG<S, V> withExtractor(BiConsumer<V, S> extractor) {
		this.extractorGenerico = extractor;
		return this;
	}
	
	/**
     * Devuelve la función inyectora definida para este nodo.
     * 
     * @return Función de tipo S -> V.
     */
	public Function<S, V> getInjector() {
		return inyectorGenerico;
	}
	
	/**
     * Devuelve la función extractora definida para este nodo.
     * 
     * @return Función de tipo (V, S) -> void.
     */
	public BiConsumer<V, S> getExtractor() {
		return extractorGenerico;
	}
}
