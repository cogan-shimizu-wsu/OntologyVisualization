package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.awt.BorderLayout;
import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.IRIComparator;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
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
	public static ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	public static OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);

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
					if (!labels.containsKey(
							className)) { /*
											 * checks if the same classname has
											 * been drawn before
											 */
						node1 = graph.createNode(new RectD(coOrdX, coOrdY, height, width));
						labels.put(className, node1);
						String clsString = getReadableClassLabel(className);					
						graph.addLabel(node1, clsString);
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

						if (!propName.toLowerCase().contains("cardinal")
								&& !propName.toLowerCase().contains("instant")) {
							String connectedNodeName = secondaryEntry.getValue();
							leString = propName;
							
							boolean visualizedbefore = checkIfVisualizedBefore(visualized, className, leString,
									connectedNodeName);
							if (!visualizedbefore) {
								System.out.print(propName + " , ");
								if (connectedNodeName.equalsIgnoreCase("OR")
										&& connectedNodeName.equalsIgnoreCase("AND")
										&& connectedNodeName.matches("[0-9]")) {
									continue;
								}
								if (!simpleDataType(connectedNodeName)) {
									if (connectedNodeName.equalsIgnoreCase("SELF")) {
										System.out.println("self");
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

								if (!connectedNodeName.equalsIgnoreCase("SELF")
										&& !connectedNodeName.equalsIgnoreCase(className)) {			
									String connectedNodeNameString = getReadableClassLabel(connectedNodeName);
									graph.addLabel(node2, connectedNodeNameString);
									System.out.print(connectedNodeName + ".\n");
								} else {
									System.out.print(className + " , " + connectedNodeName + ".\n");
								}
								IPort portAtNode1 = graph.addPort(node1);
								IPort portAtNode2 = graph.addPort(node2, FreeNodePortLocationModel.NODE_LEFT_ANCHORED);
								IEdge edgeAtPorts = graph.createEdge(portAtNode1, portAtNode2);
								
								leString = getReadablePropertyLabel(propName);
								if (pn.isNot()) {
									leString = leString + "-";
								} else {
									leString = leString;
								}
								graph.addLabel(edgeAtPorts, leString);

								SimpleEntry<String, String> sE = new SimpleEntry<String, String>(leString,
										connectedNodeName);
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

	protected static boolean checkIfVisualizedBefore(HashMap<String, SimpleEntry<String, String>> visualized,
			String className, String leString, String connectedNodeName) {
		Iterator<Map.Entry<String, SimpleEntry<String, String>>> it = visualized.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, SimpleEntry<String, String>> entry = it.next();
			String nd1 = entry.getKey();
			IRI nd1IRI = IRI.create(nd1);
			IRI classIRI = IRI.create(className);
			//IRIShortFormProvider iriShortFormProvider = null;
			//IRIComparator iriComparator = new IRIComparator(iriShortFormProvider);
			if (nd1.equalsIgnoreCase(className) || (nd1IRI.compareTo(classIRI) == 0)) {
				SimpleEntry<String, String> se1 = entry.getValue();
				String propname = se1.getKey();
				IRI propnameIRI = IRI.create(propname);
				IRI leStringIRI = IRI.create(leString);
				//leString.compareTo(propname);
				if (propname.equalsIgnoreCase(leString) || (propnameIRI.compareTo(leStringIRI) == 0)) {
					String fillername = se1.getValue();
					IRI fillernameIRI = IRI.create(fillername);
					IRI connectedNodeNameIRI = IRI.create(connectedNodeName);
					if (fillername.equalsIgnoreCase(connectedNodeName) || (fillernameIRI.compareTo(connectedNodeNameIRI) == 0)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean simpleDataType(String connectedNodeName) {
		return (connectedNodeName.equalsIgnoreCase("string")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#string")
				|| connectedNodeName.equalsIgnoreCase("dateTime")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#dateTime")
				|| connectedNodeName.equalsIgnoreCase("integer")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#integer")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#boolean")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#float")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#double")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#decimal")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#dateTime")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#duration")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#hexBinary")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#base64Binary")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#anyURI")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#IDREF")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ENTITY")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NOTATION")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#normalizedString")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#token")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#language")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#IDREFS")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ENTITIES")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NMTOKEN")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NMTOKENS")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#Name")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#QName")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NCName")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#integer")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#nonNegativeInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#positiveInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#nonPositiveInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#negativeInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#byte")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#int")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#long")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#short")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedByte")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedInt")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedLong")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedShort")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#date")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#time")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gYearMonth")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gYear")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gMonthDay")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gDay")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gMonth")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ID")
				);
	}
	
	public static String escapeName(String name) {
		return name.replace("_", "\\_").replace("#", "\\#");
	}
	
	public static String getReadableClassLabel(String className) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		IRI iri = IRI.create(className);
		OWLEntity cls = factory.getOWLEntity(EntityType.CLASS, iri);
		String clsString = (escapeName(shortFormProvider.getShortForm(cls)));
		return clsString;
	}
	public static String getReadablePropertyLabel(String propertyName) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		IRI iri = IRI.create(propertyName);
		OWLEntity cls = factory.getOWLEntity(EntityType.OBJECT_PROPERTY, iri);
		String clsString = (escapeName(shortFormProvider.getShortForm(cls)));
		return clsString;
	}
	
}
