package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.IPort;
import com.yworks.yfiles.graph.LayoutUtilities;
import com.yworks.yfiles.graph.portlocationmodels.FreeNodePortLocationModel;
import com.yworks.yfiles.layout.organic.OrganicLayout;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.input.GraphEditorInputMode;

import dase.wright.edu.ontoViz.OntolologyVisualization.OntologyVisualization.PropertyNode;

public class Visualizer {
	private final ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	public final OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);
	  
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

				HashMap<String, INode> labels = new HashMap<>();
				HashMap<String, SimpleEntry<String, String>> visualized = new HashMap<>();
				/* iterate over the hashmap */
				Iterator<Map.Entry<String, HashMap<PropertyNode, String>>> iterator = visualizer.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, HashMap<PropertyNode, String>> entry = iterator.next();

					System.out.print(entry.getKey() + " : ");

					String className = entry.getKey();
					INode node1;
					if (!labels.containsKey(className)) { /*checks if the same classname has been drawn before */
						node1 = graph.createNode(new RectD(coOrdX, coOrdY, height, width));
						labels.put(className, node1);
						graph.addLabel(node1, className);
					} else {
						node1 = labels.get(className);
					}
					

					HashMap<PropertyNode, String> values = entry.getValue();
					Iterator<Map.Entry<PropertyNode, String>> it = values.entrySet().iterator();
					INode node2 = null;
					String leString = null;
					while (it.hasNext()) {
						Map.Entry<PropertyNode, String> secondaryEntry = it.next();
						PropertyNode pn = secondaryEntry.getKey();
						String propName = pn.getPropertyName();
						if (pn.isNot()) {
							leString = propName + "-";
						} else {
							leString = propName;
						}

						if (!propName.toLowerCase().contains("cardinal")
								&& !propName.toLowerCase().contains("instant")) {
							String connectedNodeName = secondaryEntry.getValue();
							boolean visualizedbefore = checkIfVisualizedBefore(visualized, className, leString,
									connectedNodeName);
							if (!visualizedbefore) {
								System.out.print(propName + " , ");
								if (connectedNodeName.equalsIgnoreCase("OR")
										&& connectedNodeName.equalsIgnoreCase("AND")
										&& connectedNodeName.matches("[0-9]")) {
									continue;
								}
								if (!connectedNodeName.equalsIgnoreCase("string")
										&& !connectedNodeName.equalsIgnoreCase("dateTime")
										&& !connectedNodeName.equalsIgnoreCase("integer")) {
									if (connectedNodeName.equalsIgnoreCase("SELF")) {
										// System.out.println("self");
										node2 = node1;
									} else if (labels.containsKey(connectedNodeName)) {
										node2 = labels.get(connectedNodeName);
									} else {
										node2 = graph.createNode(new RectD(coOrdX + 125, coOrdY, height, width));
										labels.put(connectedNodeName, node2);
									}
								} else {
									node2 = graph.createNode(new RectD(coOrdX + 125, coOrdY, height, width));
								}

								if (!connectedNodeName.equalsIgnoreCase("SELF") || !connectedNodeName.equalsIgnoreCase(className)) {
									graph.addLabel(node2, connectedNodeName);
									System.out.print(connectedNodeName + "\n");
								}else {
									System.out.print(className + "\n");
								}
								IPort portAtNode1 = graph.addPort(node1);
								IPort portAtNode2 = graph.addPort(node2, FreeNodePortLocationModel.NODE_LEFT_ANCHORED);
								IEdge edgeAtPorts = graph.createEdge(portAtNode1, portAtNode2);
								graph.addLabel(edgeAtPorts, leString);
								
								
								SimpleEntry<String, String> sE = new SimpleEntry<String, String>(leString, connectedNodeName);
								visualized.put(className, sE);
							}
						}

					}

					coOrdX += 75;
					coOrdY += 50;

				}

				OrganicLayout layout = new OrganicLayout();
				layout.setConsiderNodeSizes(true);
				layout.setMinimumNodeDistance(50);
				LayoutUtilities.morphLayout(graphComponent, layout, Duration.ofMillis(500), null);

				/* create a top-level window and add the graph component. */
//				JFrame frame = new JFrame("Ontology Visualization");
//
//				frame.setSize(500, 500);
//				frame.setLocationRelativeTo(null);
//				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//				frame.add(graphComponent, BorderLayout.CENTER);
//				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(drawClassDiagram);
	}

	protected static boolean checkIfVisualizedBefore(HashMap<String, SimpleEntry<String, String>> visualized, String className,
			String leString, String connectedNodeName) {
		Iterator<Map.Entry<String, SimpleEntry<String, String>>> it = visualized.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, SimpleEntry<String, String>> entry = it.next();
			String nd1 = entry.getKey();
			if (nd1.equalsIgnoreCase(className)) {
				SimpleEntry<String, String> se1 = entry.getValue();
				String propname = se1.getKey();			
				if (propname.equalsIgnoreCase(leString)) {
					String fillername = se1.getValue();
					if (fillername.equalsIgnoreCase(connectedNodeName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
