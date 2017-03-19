package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.IPort;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.input.GraphEditorInputMode;

public class Visualizer {

	static void dummyVisualization() {
		Runnable doHelloWorld = new Runnable() {
			public void run() { /*
								 * create a component for displaying and editing
								 * a graph.
								 */
				GraphComponent graphComponent = new GraphComponent(); /*
																		 * Enabling
																		 * user-
																		 * interaction
																		 */
				graphComponent.setInputMode(new GraphEditorInputMode());
	
				IGraph graph = graphComponent.getGraph();
				INode node1 = graph.createNode(new RectD(30, 30, 30, 30));
				INode node2 = graph.createNode(new RectD(100, 30, 30, 30));
	
				IEdge edge1 = graph.createEdge(node1, node2);
	
				IPort portAtNode1 = graph.addPort(node1);
	
				ILabel ln1 = graph.addLabel(node1, "n1");
				ILabel ln2 = graph.addLabel(node2, "n2");
	
				/* create a top-level window and add the graph component. */
				JFrame frame = new JFrame("Ontology Visualization");
	
				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.add(graphComponent, BorderLayout.CENTER);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(doHelloWorld);
	}

}
