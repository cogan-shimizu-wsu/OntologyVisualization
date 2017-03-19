package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.util.stream.Stream;

public class FullTree {
	Stream<Node> leaves;
	public FullTree(Stream<Node> nodes) {
		this.leaves = nodes; /*TODO create the tree from these nodes here*/
	}
}
