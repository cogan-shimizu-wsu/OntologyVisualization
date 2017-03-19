package dase.wright.edu.ontoViz.OntolologyVisualization;

public class Node {
	Node left;
	Node right;
	boolean root;
	Values value;

	public Node(Node l, Node R, boolean r, Values val) {

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
