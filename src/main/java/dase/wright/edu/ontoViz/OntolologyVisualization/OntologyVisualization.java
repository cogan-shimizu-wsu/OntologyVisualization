package dase.wright.edu.ontoViz.OntolologyVisualization;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * This Project is supposed to produce an interactive visualization for
 * ontologies for ensuring better understandability
 * 
 * @author: Nazifa Karima
 */
public class OntologyVisualization {

	public static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	public static File ontologyFile = new File("src/resources/chessgame.owl");

	public static OWLOntology ontology;

	public static ArrayList<OWLClass> classes = new ArrayList<OWLClass>();
	public static ArrayList<OWLObjectProperty> prop = new ArrayList<OWLObjectProperty>();
	public static ArrayList<OWLDataProperty> dataProp = new ArrayList<OWLDataProperty>();
	public static HashMap<OWLClass, ArrayList<OWLObjectProperty>> objPropAxioms = new HashMap<OWLClass, ArrayList<OWLObjectProperty>>();
	// prop = (ArrayList<OWLObjectProperty>)
	// ontology.getObjectPropertiesInSignature();
	// dataProp = (ArrayList<OWLDataProperty>)
	// ontology.getDataPropertiesInSignature();
	// individuals = ontology.getIndividualsInSignature();
	

	public static void main(String[] args) {

		init(manager, ontologyFile, classes, prop, dataProp);

		/*Visualizer.dummyVisualization();*/
		/*TODO provide the visualizer class with the tree*/

	}

	/*initializes thr ontology and produces a tree structure for each class with it's properties*/
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
			/*Load Ontology*/
			IRI iri = IRI.create(ontologyFile.toURI());
			
			AxiomEntityVisitor nodeCreator = new AxiomEntityVisitor();
			
			/* Create Tree for each Axiom */
						ontology.classesInSignature().forEach(cls -> {
							createEntityNode(nodeCreator, cls, sortAxioms(ontology.axioms(cls)));
							/* TODO add treeCreator object as parameter later */						
							});
			
		} catch (OWLOntologyCreationException e) {
			/* TODO Auto-generated catch block*/
			e.printStackTrace();
		}
	}
	
/**
 * This method visits every entity of an axiom and creates a node in the tree
 * */
	protected static void createEntityNode(AxiomEntityVisitor nodeCreator, OWLEntity cls,
	        Collection<? extends OWLAxiom> axioms)
	{
		if(axioms.size() > 0){
			System.out.println(cls);
			for(Iterator<? extends OWLAxiom> it = axioms.iterator(); it.hasNext();)
			{
				OWLAxiom axiom = it.next();
				/*System.out.println("Type of Axiom: " + axiom.isLogicalAxiom() + ": " + axiom);*/
				axiom.accept(nodeCreator);
			}
		}
	}
	
	private static Collection<? extends OWLAxiom> sortAxioms(Stream<? extends OWLAxiom> axioms)
	{
		return asList(axioms.sorted());
	}

	private static <T extends OWLEntity> Collection<T> sortEntities(Stream<T> entities) {
		return asList(entities.sorted());
	}

	
}
