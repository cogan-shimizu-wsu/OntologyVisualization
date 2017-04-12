package dase.wright.edu.ontoViz.OntolologyVisualization;

public class Node {
	Node left;
	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	Node right;
	boolean root;
	String value;
	//Values value;

	public Node(Node l, Node r, boolean root, Values val) {
		setLeft(l);
		setRight(r);
		setRoot(root);
		setValue(value);
	}

	public enum Values {
		/** AND. = "\\sqcap "; */
		AND,
		/** OR. = "\\sqcup "; */
		OR,
		/** NOT. = "\\lnot "; */
		NOT,
		/** ALL. = "\\forall "; */
		ALL,
		/** SOME. = "\\exists "; */
		SOME,
		/** HASVALUE. = "hasValue "; */
		HASVALUE,
		/** MIN. = "\\geq"; */
		MIN,
		/** MAX. = "\\leq"; */
		MAX,
		/** MINEX. = ">"; */
		MINEX,
		/** MAXEX. = "<"; */
		MAXEX,
		/** EQUAL. = "="; */
		EQUAL,
		/** SUBCLASS. = "&\\sqsubseteq "; */
		SUBCLASS,
		/** EQUIV. = "&\\equiv "; */
		EQUIV,
		/** NOT_EQUIV. = "&\\not\\equiv "; */
		NOT_EQUIV,
		/** TOP. = "\\top "; */
		TOP,
		/** BOTTOM. = "\\bot "; */
		BOTTOM,
		/** SELF. = "\\textsf{Self} "; */
		SELF,
		/** CIRC. = "\\circ " */
		CIRC,
		/** INVERSE = "^- "; */
		INVERSE;
	}
}
