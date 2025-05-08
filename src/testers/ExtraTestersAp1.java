package testers;

import datos.MutableDouble;
import datos.PersonData;
import datos.ShoppingListData;
import grafos.StateGraph;

/**
 * La clase ExtraTesterAp1 actúa como tester adicional para el apartado 1, mostrando
 * la flexibilidad de los flujos de trabajo mediante el uso de tres tipos de estado distintos:
 * un número (`Double`), una lista de la compra personalizada y un objeto de persona con atributos.
 * 
 * Cada flujo define sus nodos, lógica y conexiones específicas.
 * 
 * @author Sofía Castro - sofiai.castro@estudiante.uam.es
 * @author Sara Lorenzo - sara.lorenzot@estudiante.uam.es
 * Pareja 11
 */
public class ExtraTestersAp1 {

	/**
	 * Método principal para ejecutar los tres flujos de trabajo definidos.
	 * 
	 * @param args Argumentos de línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		runDoubleWorkflow();
		runShoppingWorkflow();
		runPersonWorkflow();
	}

	/**
	 * Crea y ejecuta un flujo simple con `Double` como estado.
	 * Realiza una operación de raíz cuadrada y luego multiplicación por 10.
	 */
	private static void runDoubleWorkflow() {
		StateGraph<MutableDouble> sg = new StateGraph<>("doubleFlow", "Sqrt and multiply by 10");

		sg.addNode("sqrt", (MutableDouble d) -> d.setDouble(Math.sqrt(d.getDouble())))
		  .addNode("mult10", (MutableDouble d) -> d.setDouble(d.getDouble()*10));

		sg.addEdge("sqrt", "mult10");
		sg.setInitial("sqrt");
		sg.setFinal("mult10");

		System.out.println("\n--- Double Workflow ---");
		System.out.println(sg);
		sg.run(new MutableDouble(16.0), true);
	}

	/**
	 * Crea y ejecuta un flujo con una lista de la compra como estado compartido.
	 * Agrega productos y los imprime.
	 */
	private static void runShoppingWorkflow() {
		StateGraph<ShoppingListData> sg = new StateGraph<>("shoppingFlow", "Add and print shopping items");

		sg.addNode("addItemsToBuy", (ShoppingListData list) -> {
			list.add("Pan");
			list.add("Leche");
		}).addNode("buyMilk", (ShoppingListData list) -> list.remove("Leche"))
		  .addNode("itemsLeft", (ShoppingListData list) -> System.out.println("Quedan " + list.size() + " elementos que comprar"));

		sg.addEdge("addItemsToBuy", "buyMilk");
		sg.addEdge("buyMilk", "itemsLeft");
		sg.setInitial("addItemsToBuy");

		System.out.println("\n--- Shopping List Workflow ---");
		System.out.println(sg);
		sg.run(new ShoppingListData(), true);
	}

	/**
	 * Crea y ejecuta un flujo con un objeto `PersonData` que modifica y muestra atributos.
	 */
	private static void runPersonWorkflow() {
		StateGraph<PersonData> sg = new StateGraph<>("personFlow", "Modify and display person data");

		sg.addNode("updateAge", (PersonData p) -> p.age += 1)
		  .addNode("printPerson", (PersonData p) -> System.out.println("Persona actualizada: " + p));

		sg.addEdge("updateAge", "printPerson");
		sg.setInitial("updateAge");

		System.out.println("\n--- Person Workflow ---");
		System.out.println(sg);
		sg.run(new PersonData("Lucía", 29), true);
	}
}
