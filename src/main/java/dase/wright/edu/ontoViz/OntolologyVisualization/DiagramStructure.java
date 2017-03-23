package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.util.stream.Stream;

public class DiagramStructure {
	Stream<Node> leaves;
	public DiagramStructure(Stream<Node> nodes) {
		this.leaves = nodes; /*TODO create the tree from these nodes here*/
	}
}
