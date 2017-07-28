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
import com.yworks.yfiles.layout.LayoutOrientation;
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

				GraphComponent graphComponent = new GraphComponent(); 
				graphComponent.setInputMode(new GraphEditorInputMode());

				IGraph graph = graphComponent.getGraph();

				double coOrdX = 30;
				double coOrdY = 30;
				double height = 100;
				double width = 30;

				HashMap<String, INode> labels = new HashMap<>();
				HashMap<String, SimpleEntry<String, String>> visualized = new HashMap<>();
				/* iterate over the hashmap which contains the <node,property, filler> triples to visualize*/
				Iterator<Map.Entry<String, HashMap<PropertyNode, String>>> iterator = visualizer.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, HashMap<PropertyNode, String>> entry = iterator.next();

					String className = entry.getKey();
					if (!getReadableClassLabel(className).equalsIgnoreCase("Things")) {
						INode node1;
						if (!labels.containsKey(className)) {
							node1 = graph.createNode(new RectD(coOrdX, coOrdY, height, width));
							labels.put(className, node1);
							String clsString = getReadableClassLabel(className);
							graph.addLabel(node1, clsString);
							System.out.print("Class: " + clsString + " is connected to ");
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

								
									if (connectedNodeName.equalsIgnoreCase("OR")
											&& connectedNodeName.equalsIgnoreCase("AND")
											&& connectedNodeName.matches("[0-9]")) {
										continue;
									}
									if (!simpleDataType(connectedNodeName)) {
										if (connectedNodeName.equalsIgnoreCase("SELF")) {
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
										System.out.print(connectedNodeName + " through ");
									} else {
										System.out.print(connectedNodeName + " through ");
									}
									IPort portAtNode1 = graph.addPort(node1);
									IPort portAtNode2 = graph.addPort(node2,
											FreeNodePortLocationModel.NODE_LEFT_ANCHORED);
									IEdge edgeAtPorts = graph.createEdge(portAtNode1, portAtNode2);

									System.out.print(leString + "\n");
									leString = getReadablePropertyLabel(propName);
									if (pn.isReverse) {
										leString = leString + "-";
									}
									graph.addLabel(edgeAtPorts, leString);

									SimpleEntry<String, String> sE = new SimpleEntry<String, String>(leString,
											connectedNodeName);
									visualized.put(className, sE);
							}

						}
						coOrdX += 75;
						coOrdY += 50;
					}

				}

				OrganicLayout layout = new OrganicLayout();
				layout.setNodeSizeConsiderationEnabled(true);
				layout.setNodeLabelConsiderationEnabled(true);
				layout.setComponentLayoutEnabled(true);
				layout.setSmartComponentLayoutEnabled(true);
				layout.setMinimumNodeDistance(50);
				layout.setAvoidingNodeEdgeOverlapsEnabled(true);
				layout.setParallelEdgeRouterEnabled(true);
				//layout.setSelfLoopRouterEnabled(true);

				LayoutUtilities.morphLayout(graphComponent, layout, Duration.ofMillis(500));

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
			nd1 = nd1.replaceAll("[^\\w\\s]","");
			className=className.replaceAll("[^\\w\\s]","");
			//System.out.println(nd1 + " comparing to: " + className);
			if (nd1.equalsIgnoreCase(className)) {
				SimpleEntry<String, String> se1 = entry.getValue();
				String propname = se1.getKey();
				propname = propname.replaceAll("[^\\w\\s]","");
				leString = leString.replaceAll("[^\\w\\s]","");
				//System.out.println(propname + " comparing to: " + leString);
				if (propname.equalsIgnoreCase(leString)) {
					String fillername = se1.getValue();
					fillername = fillername.replaceAll("[^\\w\\s]","");
					connectedNodeName = connectedNodeName.replaceAll("[^\\w\\s]","");
					//System.out.println(fillername + " comparing to: " + connectedNodeName);
					if (fillername.equalsIgnoreCase(connectedNodeName)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean simpleDataType(String connectedNodeName) {
		connectedNodeName = getReadableClassLabel(connectedNodeName);
		return (connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#string")
				||connectedNodeName.toLowerCase().contains("string")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#dateTime")
				|| connectedNodeName.toLowerCase().contains("dateTime")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#integer")				
				|| connectedNodeName.toLowerCase().contains("integer")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#boolean")
				|| connectedNodeName.toLowerCase().contains("boolean")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#float")
				|| connectedNodeName.toLowerCase().contains("float")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#double")
				|| connectedNodeName.toLowerCase().contains("double")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#decimal")
				|| connectedNodeName.toLowerCase().contains("decimal")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#duration")
				|| connectedNodeName.toLowerCase().contains("duration")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#hexBinary")
				|| connectedNodeName.toLowerCase().contains("hexBinary")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#base64Binary")
				|| connectedNodeName.toLowerCase().contains("base64Binary")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#anyURI")
				|| connectedNodeName.toLowerCase().contains("anyURI")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#IDREF")
				|| connectedNodeName.toLowerCase().contains("IDREF")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ENTITY")
				|| connectedNodeName.toLowerCase().contains("ENTITY")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NOTATION")
				|| connectedNodeName.toLowerCase().contains("NOTATION")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#normalizedString")
				|| connectedNodeName.toLowerCase().contains("normalizedString")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#token")
				|| connectedNodeName.toLowerCase().contains("token")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#language")
				|| connectedNodeName.toLowerCase().contains("language")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#IDREFS")
				|| connectedNodeName.toLowerCase().contains("IDREFS")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ENTITIES")
				|| connectedNodeName.toLowerCase().contains("ENTITIES")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NMTOKEN")
				|| connectedNodeName.toLowerCase().contains("NMTOKEN")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#Name")
				|| connectedNodeName.toLowerCase().contains("Name")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#QName")
				|| connectedNodeName.toLowerCase().contains("QName")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#NCName")
				|| connectedNodeName.toLowerCase().contains("NCName")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#nonNegativeInteger")
				|| connectedNodeName.toLowerCase().contains("nonNegativeInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#positiveInteger")
				|| connectedNodeName.toLowerCase().contains("positiveInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#nonPositiveInteger")
				|| connectedNodeName.toLowerCase().contains("nonPositiveInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#negativeInteger")
				|| connectedNodeName.toLowerCase().contains("negativeInteger")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#byte")
				|| connectedNodeName.toLowerCase().contains("byte")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#int")
				|| connectedNodeName.toLowerCase().contains("int")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#long")
				|| connectedNodeName.toLowerCase().contains("long")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#short")
				|| connectedNodeName.toLowerCase().contains("short")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedByte")
				|| connectedNodeName.toLowerCase().contains("unsignedByte")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedInt")
				|| connectedNodeName.toLowerCase().contains("unsignedInt")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedLong")
				|| connectedNodeName.toLowerCase().contains("unsignedLong")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#unsignedShort")
				|| connectedNodeName.toLowerCase().contains("unsignedShort")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#date")
				|| connectedNodeName.toLowerCase().contains("date")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#time")
				|| connectedNodeName.toLowerCase().contains("time")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gYearMonth")
				|| connectedNodeName.toLowerCase().contains("gYearMonth")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gYear")
				|| connectedNodeName.toLowerCase().contains("gYear")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gMonthDay")
				|| connectedNodeName.toLowerCase().contains("gMonthDay")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gDay")
				|| connectedNodeName.toLowerCase().contains("gDay")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#gMonth")
				|| connectedNodeName.toLowerCase().contains("gMonth")
				|| connectedNodeName.equalsIgnoreCase("http://www.w3.org/2001/XMLSchema#ID")
				|| connectedNodeName.toLowerCase().contains("ID")
				|| connectedNodeName.toLowerCase().contains("attribute")
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
		System.out.println(shortFormProvider.getShortForm(cls));
		String clsString = (escapeName(shortFormProvider.getShortForm(cls)));
		if (clsString.contains("#")) {
			clsString = clsString.split("#")[1];
		}
		return clsString.replaceAll("[^\\w\\s]","");
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