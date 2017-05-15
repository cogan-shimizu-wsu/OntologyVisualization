package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.IPort;
import com.yworks.yfiles.graph.portlocationmodels.FreeNodePortLocationModel;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.input.GraphEditorInputMode;

import dase.wright.edu.ontoViz.OntolologyVisualization.OntologyVisualization.PropertyNode;

public class Visualizer {

	static void visualization(HashMap<String, HashMap<PropertyNode, String>> visualizer) {
		Runnable drawClassDiagram = new Runnable() {
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

				double coOrdX = 30;
				double coOrdY = 30;
				double height = 100;
				double width = 30;
				
				/* iterate over the hashmap */				
				Iterator<Map.Entry<String, HashMap<PropertyNode, String>>> iterator = visualizer.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, HashMap<PropertyNode, String>> entry = iterator.next();
					System.out.println(entry.getKey() + " : " + entry.getValue());
					
					INode node1 = graph.createNode(new RectD(coOrdX, coOrdY, height, width));
					String className = entry.getKey();
					ILabel ln1 = graph.addLabel(node1, className);
					HashMap<PropertyNode, String> values = entry.getValue();
					Iterator<Map.Entry<PropertyNode, String>> it = values.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<PropertyNode, String> secondaryEntry = it.next();
						
						INode node2 = graph.createNode(new RectD(coOrdX+75, coOrdY, height, width));
						IPort portAtNode1 = graph.addPort(node1);
						IPort portAtNode2 = graph.addPort(node2, FreeNodePortLocationModel.NODE_LEFT_ANCHORED);
						IEdge edgeAtPorts = graph.createEdge(portAtNode1, portAtNode2);
						String connectedNode = secondaryEntry.getValue();
						ILabel ln3 = graph.addLabel(node2, connectedNode);
						ILabel le3 = graph.addLabel(edgeAtPorts, "Edge at Ports");
						}

					coOrdX+=45;
					coOrdY+=45;

				}

				/* create a top-level window and add the graph component. */
				JFrame frame = new JFrame("Ontology Visualization");

				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.add(graphComponent, BorderLayout.CENTER);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(drawClassDiagram);
	}

}
