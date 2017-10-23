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
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
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
public class OntologyVisualization
{
	public static OWLOntologyManager	manager			= OWLManager.createOWLOntologyManager();
	public static File					ontologyFile	= new File(
	        "src/resources/" + "ontologiesProvidedByPascal/timeindexedpersonrole" + ".owl");


	public static OWLOntology			ontology;
	public static OntologyVisualization	ontoViz			= new OntologyVisualization();

	// static String GEOLINKONTOLOGY = "geolinkMain";
	static String						AGENTROLE		= "agentrole";
	static String						CHESSGAME		= "chessgame";
	static String						CHESSSHORTCUT	= "chessgameshortcuts";
	static String						CRUISE			= "cruise";
	static String						TRAJECTORY		= "trajectory";

	public static HashMap<String, HashMap<PropertyNode, String>> visualizer = new HashMap<>();

	public static void main(String[] args)
	{

		init(manager, ontologyFile);
		Visualizer.visualization(visualizer);
	}

	/*
	 * loads the ontology, classifies the axioms and populates the dataStructure
	 * to be visualized
	 */
	private static void init(OWLOntologyManager manager, File fullOntology)
	{
		OWLOntology ontology;
		try
		{
			ontology = manager.loadOntologyFromOntologyDocument(fullOntology);
			/*
			 * Force silent import errors. This is important for older
			 * ontologies not that Purl.org is broken
			 */
			manager.setOntologyLoaderConfiguration(manager.getOntologyLoaderConfiguration()
			        .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT));
			/* Load Ontology */

			/* Create Tree for each Axiom */
			ontology.classesInSignature().forEach(cls -> {
				populateVisualizer(cls, sortAxioms(ontology.axioms(cls, Imports.INCLUDED)));
			});

		}
		catch(OWLOntologyCreationException e)
		{
			/* TODO Auto-generated catch block */
			e.printStackTrace();
		}
	}

	/**
	 * This method takes each logical axiom, processes it and puts in the
	 * visualizer in <class, property, filler> triple format
	 */
	protected static void populateVisualizer(OWLEntity cls, Collection<? extends OWLAxiom> axioms)
	{
		if(axioms.size() > 0)
		{
			for(Iterator<? extends OWLAxiom> it = axioms.iterator(); it.hasNext();)
			{
				OWLAxiom axiom = it.next();
				// ArrayList<String> aLStack = new ArrayList<>();
				ArrayList<Node> aLStack = new ArrayList<>();
				if(axiom.isLogicalAxiom())
				{
					aLStack.clear();
					AxiomEntityVisitor visitor = new AxiomEntityVisitor(aLStack);
					axiom.accept(visitor);
				}

				Node first = null;

				// if (!containsNot(aLStack)) {
				// for (Iterator<String> iterator = aLStack.iterator();
				// iterator.hasNext();) {
				Iterator<Node> iterator = aLStack.iterator();
				first = (Node) iterator.next();
				String firstEntity = first.getEntityName();
				if(isbasicSCDef(aLStack, first))
				{
					populatingSCDefAxiomToViz(aLStack);
				}
				else if(isSCOAxiom(aLStack, first))
				{
					populatingSCOAxiomToViz(aLStack);
				}
				else if(firstEntity.equalsIgnoreCase("equivalent"))
				{
					Node classNode = iterator.next();
					String className = classNode.getEntityName();
					while(iterator.hasNext())
					{
						Node cur = iterator.next();
						String curEntity = cur.getEntityName();
						ArrayList<Node> subStack = new ArrayList<>();
						while(!curEntity.equalsIgnoreCase("and") && !curEntity.equalsIgnoreCase("or")
						        && !curEntity.equalsIgnoreCase("endOfEquivalentClassList"))
						{
							subStack.add(cur);
							if(iterator.hasNext())
							{
								cur = iterator.next();
								curEntity = cur.getEntityName();
							}
						}
						// if (isbasicEquivDef(subStack)) {
						populatingEquivDefAxiomToViz(className, subStack);
						// }else {
						// populatingEuivAxiomToViz(subStack);
						// }
					}
				}

				// }
				// }
			}

		}
	}

	private static void populatingEquivDefAxiomToViz(String className, ArrayList<Node> subStack)
	{
		if(subStack.size() == 1)
		{
			String propertyName = "equivalent";
			PropertyNode propNode = new PropertyNode(false, propertyName);
			String filler = subStack.get(0).getEntityName();
			if(visualizer.containsKey(className))
			{
				HashMap<PropertyNode, String> retrievedMap = visualizer.get(className);
				retrievedMap.put(propNode, filler);
				visualizer.put(className, retrievedMap);
			}
			else
			{
				HashMap<PropertyNode, String> map = new HashMap<>();
				map.put(propNode, filler);
				visualizer.put(className, map);
			}
		}
		else
		{
			Node cur, filler;
			String propName;
			boolean isReverse = false, isNot = false;
			Iterator<Node> iterator = subStack.iterator();
			cur = (Node) iterator.next();
			String curStr = cur.getEntityName();
			while(isStackWord(curStr))
			{
				if(curStr.equalsIgnoreCase("reverse") || curStr.equalsIgnoreCase("owlobjectinverseOf"))
					isReverse = true;
				if(curStr.equalsIgnoreCase("not"))
					isNot = true;
				cur = (Node) iterator.next();
				curStr = cur.getEntityName();
			}

			if(isNot)
			{
				propName = "¬" + curStr;
			}
			else
			{
				propName = curStr;
			}

			filler = (Node) iterator.next();
			String fillerName = filler.getEntityName();
			PropertyNode propNode;
			if(isReverse)
			{
				propNode = new PropertyNode(true, propName);
			}
			else
			{
				propNode = new PropertyNode(false, propName);
			}
			if(visualizer.containsKey(className))
			{
				HashMap<PropertyNode, String> retrievedMap = visualizer.get(className);
				retrievedMap.put(propNode, fillerName);
				visualizer.put(className, retrievedMap);
			}
			else
			{
				HashMap<PropertyNode, String> map = new HashMap<>();
				map.put(propNode, fillerName);
				visualizer.put(className, map);
			}
		}

	}

	// private static boolean containsNot(ArrayList<Node> aLStack) {
	// for (int i = 0; i < aLStack.size(); i++) {
	// String cur = aLStack.get(i).getEntityName();
	// if(cur.equalsIgnoreCase("not"))
	// return true;
	// }
	// return false;
	// }

	// private static void populatingEquivDefAxiomToViz(boolean b,
	// ArrayList<Node> subStack) {
	// PropertyNode propNode = ontoViz.createPropertyNode(false, "equivalent");
	// if (visualizer.containsKey(subStack.get(1).getEntityName())) {
	// HashMap<PropertyNode, String> retrievedMap =
	// visualizer.get(subStack.get(1).getEntityName());
	// String filler = subStack.get(2).getEntityName();
	// retrievedMap.put(propNode, filler);
	// String className = subStack.get(1).getEntityName();
	// visualizer.put(className, retrievedMap);
	// } else {
	// HashMap<PropertyNode, String> map = new HashMap<>();
	// String filler = subStack.get(2).getEntityName();
	// map.put(propNode, filler);
	// String className = subStack.get(1).getEntityName();
	// visualizer.put(className, map);
	// }
	// if (visualizer.containsKey(subStack.get(1).getEntityName())) {
	// HashMap<PropertyNode, String> retrievedMap =
	// visualizer.get(subStack.get(1).getEntityName());
	// String filler = subStack.get(3).getEntityName();
	// retrievedMap.put(propNode, filler);
	// String className = subStack.get(1).getEntityName();
	// visualizer.put(className, retrievedMap);
	// } else {
	// HashMap<PropertyNode, String> map = new HashMap<>();
	// String filler = subStack.get(3).getEntityName();
	// map.put(propNode, filler);
	// String className = subStack.get(1).getEntityName();
	// visualizer.put(className, map);
	// }
	// }

	private static boolean isSCOAxiom(ArrayList<Node> aLStack, Node first)
	{
		String firstEntity = first.getEntityName();
		return (firstEntity.equalsIgnoreCase("subclass") && aLStack.size() > 3);
	}

	private static boolean isbasicSCDef(ArrayList<Node> aLStack, Node first)
	{
		String firstEntity = first.getEntityName();
		return (firstEntity.equalsIgnoreCase("subclass") && aLStack.size() <= 3);
	}

	// private static boolean isbasicEquivDef(ArrayList<Node> subStack) {
	// Iterator<Node> iterator = subStack.iterator();
	// Node classObj = iterator.next();
	// String className = classObj.getEntityName();
	//
	//
	//
	// return false;
	// }

	private static void populatingSCDefAxiomToViz(ArrayList<Node> aLStack)
	{
		PropertyNode propNode = new PropertyNode(false, "subclass");
		if(visualizer.containsKey(aLStack.get(1).getEntityName()))
		{
			HashMap<PropertyNode, String> retrievedMap = visualizer.get(aLStack.get(1).getEntityName());
			String filler = aLStack.get(2).getEntityName();
			retrievedMap.put(propNode, filler);
			visualizer.put(aLStack.get(1).getEntityName(), retrievedMap);
		}
		else
		{
			HashMap<PropertyNode, String> map = new HashMap<>();
			String filler = aLStack.get(2).getEntityName();
			map.put(propNode, filler);
			visualizer.put(aLStack.get(1).getEntityName(), map);
		}
	}

	private static void populatingSCOAxiomToViz(ArrayList<Node> aLStack)
	{
		Node cur;
		String propName;
		Node filler;
		Node classObj;
		String className;
		boolean isReverse = false;
		boolean isNot = false;
		Iterator<Node> iterator = aLStack.iterator();
		classObj = (Node) iterator.next();
		className = classObj.getEntityName();
		cur = (Node) iterator.next();
		String curStr = cur.getEntityName();
		while(isStackWord(curStr))
		{
			if(curStr.equalsIgnoreCase("reverse") || curStr.equalsIgnoreCase("owlobjectinverseOf"))
				isReverse = true;
			if(curStr.equalsIgnoreCase("not"))
				isNot = true;
			cur = (Node) iterator.next();
			curStr = cur.getEntityName();
		}

		if(isNot)
		{
			propName = "¬" + curStr;
		}
		else
		{
			propName = curStr;
		}

		filler = (Node) iterator.next();
		String fillerName = filler.getEntityName();
		PropertyNode propNode;
		if(isReverse)
		{
			propNode = new PropertyNode(true, propName);
		}
		else
		{
			propNode = new PropertyNode(false, propName);
		}
		if(visualizer.containsKey(className))
		{
			HashMap<PropertyNode, String> retrievedMap = visualizer.get(className);
			HashMap<PropertyNode, String> retrievedReversedMap = visualizer.get(fillerName);
			if(!containsSameEdge(retrievedMap, propNode, fillerName)
			        && !containsReverseEdge(retrievedReversedMap, propNode, className, isReverse))
			{
				retrievedMap.put(propNode, fillerName);
				visualizer.put(className, retrievedMap);
			}
		}
		else
		{
			HashMap<PropertyNode, String> map = new HashMap<>();
			map.put(propNode, fillerName);
			visualizer.put(className, map);
		}
	}

	private static boolean isStackWord(String cur)
	{
		return cur.equalsIgnoreCase("not") || cur.equalsIgnoreCase("reverse") || (cur.equalsIgnoreCase("inverse"))
		        || cur.equalsIgnoreCase("some") || cur.equalsIgnoreCase("all") || cur.equalsIgnoreCase("and")
		        || cur.equalsIgnoreCase("or") || cur.equalsIgnoreCase("equivalent")
		        || cur.equalsIgnoreCase("end of equivalent class list") || cur.equalsIgnoreCase("owlobjectinverseOf")
		        || cur.equalsIgnoreCase("OWLDataExactCardinality") || cur.equalsIgnoreCase("OWLDataMaxCardinality")
		        || cur.equalsIgnoreCase("OWLDataMinCardinality") || cur.equalsIgnoreCase("OWLObjectExactCardinality")
		        || cur.equalsIgnoreCase("OWLObjectMaxCardinality") || cur.equalsIgnoreCase("OWLObjectMinCardinality")
		        || cur.matches("[0-9]");
	}

	private static boolean containsSameEdge(HashMap<PropertyNode, String> retrievedMap, PropertyNode propNode,
	        String filler)
	{
		Iterator<Map.Entry<PropertyNode, String>> it = retrievedMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<PropertyNode, String> entry = it.next();
			PropertyNode pnToMatch = entry.getKey();
			String propNameToMatch = pnToMatch.getPropertyName();
			propNameToMatch = propNameToMatch.replaceAll("[^\\w\\s]", "");
			String propNameToCheck = propNode.getPropertyName();
			propNameToCheck = propNameToCheck.replaceAll("[^\\w\\s]", "");
			if(propNameToMatch.equalsIgnoreCase(propNameToCheck))
			{
				String fillerToMatch = entry.getValue();
				fillerToMatch = fillerToMatch.replaceAll("[^\\w\\s]", "");
				String fillerToCheck = filler.replaceAll("[^\\w\\s]", "");
				if(fillerToMatch.equalsIgnoreCase(fillerToCheck))
				{
					return true;
				}
			}

		}
		return false;
	}

	private static boolean containsReverseEdge(HashMap<PropertyNode, String> retrievedReversedMap,
	        PropertyNode propNode, String className, boolean negation)
	{
		if(retrievedReversedMap != null && !retrievedReversedMap.isEmpty())
		{
			Iterator<Map.Entry<PropertyNode, String>> it = retrievedReversedMap.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<PropertyNode, String> entry = it.next();
				PropertyNode pnToMatch = entry.getKey();
				String propNameToMatch = pnToMatch.getPropertyName();
				propNameToMatch = propNameToMatch.replaceAll("[^\\w\\s]", "");
				String propNameToCheck = propNode.getPropertyName();
				propNameToCheck = propNameToCheck.replaceAll("[^\\w\\s]", "");
				if(propNameToMatch.equalsIgnoreCase(propNameToCheck))
				{
					String fillerToMatch = entry.getValue();
					fillerToMatch = fillerToMatch.replaceAll("[^\\w\\s]", "");
					String fillerToCheck = className.replaceAll("[^\\w\\s]", "");
					boolean reverseEdgeExists = (pnToMatch.isReverse != negation);
					if(fillerToMatch.equalsIgnoreCase(fillerToCheck) && reverseEdgeExists)
					{
						return true;
					}
				}

			}
		}
		return false;
	}

	private static Collection<? extends OWLAxiom> sortAxioms(Stream<? extends OWLAxiom> axioms)
	{
		return asList(axioms.sorted());
	}

}
