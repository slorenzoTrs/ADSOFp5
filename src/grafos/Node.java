package grafos;

import java.util.function.Consumer;

public class Node<S> {
	private Consumer<S> codigo;
	private Node<S> nextNode;
}
