package dase.wright.edu.ontoViz.OntolologyVisualization;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * This Project is supposed to produce an interactive visualization for
 * ontologies for ensuring better understandability
 * 
 * @author: Nazifa Karima
 */
public class OntologyVisualization {
	
	public static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	public static File ontologyFile = new File("src/resources/cruise.owl");

	public static OWLOntology ontology;
	public static OntologyVisualization ontoViz = new OntologyVisualization();

	public static ArrayList<OWLClass> classes = new ArrayList<OWLClass>();
	public static ArrayList<OWLObjectProperty> prop = new ArrayList<OWLObjectProperty>();
	public static ArrayList<OWLDataProperty> dataProp = new ArrayList<OWLDataProperty>();
	

	public class PropertyNode {
		boolean not;
		String propertyName;

		public boolean isNot() {
			return not;
		}

		public void setNot(boolean not) {
			this.not = not;
		}

		public PropertyNode(Boolean val, String name) {
			this.not = val;
			this.propertyName = name;
		}

		public String getPropertyName() {
			return propertyName;
		}

		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
	}

	public PropertyNode createPropertyNode(Boolean b, String property) {
		return new PropertyNode(b, property);
	}

	public static HashMap<String, HashMap<PropertyNode, String>> visualizer = new HashMap<>();

	public class link {
		String arrowLabel;
		String boxLabel;
		String arrowLabelPrefix;
		String arrowLabelCardinality;

		public link(String aL, String bL, String aLP, String aLC) {
			this.arrowLabel = aL;
			this.boxLabel = bL;
			this.arrowLabelPrefix = aLP;
			this.arrowLabelPrefix = aLC;
		}

		public String getArrowLabel() {
			return arrowLabel;
		}

		public String getBoxLabel() {
			return boxLabel;
		}

		public String getArrowLabelPrefix() {
			return arrowLabelPrefix;
		}

		public String getArrowLabelCardinality() {
			return arrowLabelCardinality;
		}
	}

	public static void main(String[] args) {

		init(manager, ontologyFile, classes, prop, dataProp);
		Visualizer.visualization(visualizer);
	}

	/*
	 * initializes the ontology and produces a tree structure for each class
	 * with it's properties
	 */
	private static void init(OWLOntologyManager manager, File fullOntology, ArrayList<OWLClass> classes,
			ArrayList<OWLObjectProperty> prop, ArrayList<OWLDataProperty> dataProp) {
		OWLOntology ontology;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(fullOntology);
			/*
			 * Force silent import errors. This is important for older
			 * ontologies not that Purl.org is broken
			 */
			manager.setOntologyLoaderConfiguration(manager.getOntologyLoaderConfiguration()
					.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT));
			/* Load Ontology */
			IRI iri = IRI.create(ontologyFile.toURI());

			AxiomEntityVisitor nodeCreator;

			/* Create Tree for each Axiom */
			ontology.classesInSignature().forEach(cls -> {
				createAxiomNode(cls, sortAxioms(ontology.axioms(cls, Imports.INCLUDED)));
			});

		} catch (OWLOntologyCreationException e) {
			/* TODO Auto-generated catch block */
			e.printStackTrace();
		}
	}

	/**
	 * This method visits every entity of an axiom and creates a node in the
	 * tree
	 */
	protected static void createAxiomNode(OWLEntity cls, Collection<? extends OWLAxiom> axioms) {
		if (axioms.size() > 0) {
			for (Iterator<? extends OWLAxiom> it = axioms.iterator(); it.hasNext();) {
				OWLAxiom axiom = it.next();
				ArrayList<String> aLStack = new ArrayList<>();
				if (axiom.isLogicalAxiom()) {
					aLStack.clear();
					AxiomEntityVisitor visitor = new AxiomEntityVisitor(aLStack);
					axiom.accept(visitor);
				}

//				for (Iterator<String> iterator = aLStack.iterator(); iterator.hasNext();) {
//					System.out.print(iterator.next() + " ");
//				}
//				System.out.println();

				String first, cur, propName = null, filler = null, className = "";
				boolean negation = false;

				for (Iterator<String> iterator = aLStack.iterator(); iterator.hasNext();) {
					first = (String) iterator.next();
					if (first.equalsIgnoreCase("subclass") && aLStack.size() <= 3) {
						PropertyNode propNode = ontoViz.createPropertyNode(false, "subclass");
						if (visualizer.containsKey(aLStack.get(1))) {
							HashMap<PropertyNode, String> retrievedMap = visualizer.get(aLStack.get(1));
							retrievedMap.put(propNode, aLStack.get(2));
							visualizer.put(aLStack.get(1), retrievedMap);
						} else {
							HashMap<PropertyNode, String> map = new HashMap<>();
							map.put(propNode, aLStack.get(2));
							visualizer.put(aLStack.get(1), map);
						}
					} else if (first.equalsIgnoreCase("subclass") || first.equalsIgnoreCase("equivalent")) {
						className = (String) iterator.next();
						cur = (String) iterator.next();
						while ((cur.equalsIgnoreCase("not") || cur.equalsIgnoreCase("some")
								|| cur.equalsIgnoreCase("all") || cur.equalsIgnoreCase("OWLDataExactCardinality")
								|| cur.equalsIgnoreCase("OWLDataMaxCardinality")
								|| cur.equalsIgnoreCase("OWLDataMinCardinality")
								|| cur.equalsIgnoreCase("OWLObjectExactCardinality")
								|| cur.equalsIgnoreCase("OWLObjectMaxCardinality")
								|| cur.equalsIgnoreCase("OWLObjectMinCardinality") || cur.matches("[0-9]"))) {
							if(cur.equalsIgnoreCase("not")) negation = true;		
							cur = (String) iterator.next();
						}
						if (cur.equalsIgnoreCase("OWLObjectInverseOf")) {
							negation = true;
							cur = (String) iterator.next();
						}
						propName = cur;
						/*System.out.println("Property name: " + propName);*/
						filler = (String) iterator.next();
						PropertyNode propNode;
						if (negation) {
							propNode = ontoViz.createPropertyNode(true, propName);
						} else {
							propNode = ontoViz.createPropertyNode(false, propName);
						}
						if (visualizer.containsKey(className)) {
							HashMap<PropertyNode, String> retrievedMap = visualizer.get(className);
							if (!containsSameVisualization(retrievedMap, propNode, filler)) {
								retrievedMap.put(propNode, filler);
								visualizer.put(className, retrievedMap);
							}
						} else {
							HashMap<PropertyNode, String> map = new HashMap<>();
							map.put(propNode, filler);
							visualizer.put(className, map);
						}
					}

				}
			}

		}
	}

	private static boolean containsSameVisualization(HashMap<PropertyNode, String> retrievedMap, PropertyNode propNode, String filler) {
		Iterator<Map.Entry<PropertyNode, String>> it = retrievedMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<PropertyNode, String> entry = it.next();
			PropertyNode pnToMatch = entry.getKey();
			String propNameToMatch = pnToMatch.getPropertyName();
			propNameToMatch = propNameToMatch.replaceAll("[^\\w\\s]","");
			String propNameToCheck = propNode.getPropertyName();
			propNameToCheck=propNameToCheck.replaceAll("[^\\w\\s]","");
			//System.out.println("PropertyName" + ": " + propNameToMatch + " comparing to: " + propNameToCheck);
			if (propNameToMatch.equalsIgnoreCase(propNameToCheck)) {
				String fillerToMatch = entry.getValue();
				fillerToMatch = fillerToMatch.replaceAll("[^\\w\\s]","");
				String fillerToCheck = filler.replaceAll("[^\\w\\s]","");

				//System.out.println("FillerName" + ": " + fillerToMatch + " comparing to: " + fillerToCheck);
				if (fillerToMatch.equalsIgnoreCase(fillerToCheck)) {
					//System.out.println("Returning true");
					return true;
				}
			}
			
		}
		return false;
	}

	private static Collection<? extends OWLAxiom> sortAxioms(Stream<? extends OWLAxiom> axioms) {
		return asList(axioms.sorted());
	}

	private static <T extends OWLEntity> Collection<T> sortEntities(Stream<T> entities) {
		return asList(entities.sorted());
	}

}
