package grafos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Node<S> {
	private String nombre;
	private Consumer<S> codigo;
	private List<Node<S>> nextNodes = new ArrayList<>();
	
	public Node(String nombre, Consumer<S> codigo) {
		this.nombre = nombre;
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Consumer<S> getCodigo() {
		return codigo;
	}
	
	public List<Node<S>> getNextNode() {
		return nextNodes;
	}
	
	public void setNextNode(Node<S> node) {
		if(nextNodes.contains(node)) { return; }
		this.nextNodes.add(node);
	}
	
	public void execute(S input) {
		codigo.accept(input);
	}
	
	@Override
	public String toString() {
		String s = "Node " + nombre + " (" + nextNodes.size() + " output nodes)";
		return s;
	}
}
