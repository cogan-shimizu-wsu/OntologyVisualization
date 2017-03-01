package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.yworks.yfiles.view.GraphComponent;

/**
 * Hello world!
 *
 */
public class OntologyVisualization {

	public OntologyVisualization() {
		// create a component for displaying and editing a graph.
		GraphComponent graphComponent = new GraphComponent();
		// create a top-level window and add the graph component.
		JFrame frame = new JFrame("Hello, yFiles for Java");

		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(graphComponent, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	public static File fullOntology = new File("src/resources/chessgame.owl");

	public static OWLOntology ontology;
	public static ArrayList<OWLClass> classes = new ArrayList<OWLClass>();
	public static ArrayList<OWLObjectProperty> prop = new ArrayList<OWLObjectProperty>();
	public static ArrayList<OWLDataProperty> dataProp = new ArrayList<OWLDataProperty>();
	public static HashMap<OWLClass, OWLObjectProperty> objPropAxioms = new HashMap<>();
	// prop = (ArrayList<OWLObjectProperty>)
	// ontology.getObjectPropertiesInSignature();
	// dataProp = (ArrayList<OWLDataProperty>)
	// ontology.getDataPropertiesInSignature();
	// individuals = ontology.getIndividualsInSignature();

	public static void main(String[] args) {

		Runnable doHelloWorld = new Runnable() {
		     public void run() {
		         System.out.println("Hello World on " + Thread.currentThread());
		     }
		 }; 
		SwingUtilities.invokeLater(doHelloWorld);

		init(manager, fullOntology, classes, prop, dataProp);
	}


	private static void init(OWLOntologyManager manager, File fullOntology, ArrayList<OWLClass> classes,
			ArrayList<OWLObjectProperty> prop, ArrayList<OWLDataProperty> dataProp) {
		OWLOntology ontology;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(fullOntology);
			for (OWLClass cls : ontology.getClassesInSignature()) {
				classes.add(cls);
				//System.out.println("Class: " + classes.get(classes.size() - 1));
				for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
					if (op.getDomain().equals(cls)) {
						objPropAxioms.put(cls, (OWLObjectProperty) op.getProperty());
					}
				}
			}
			//writeHashMapintoFile(HashMap<K, V>, File path);
			for (OWLObjectProperty obp : ontology.getObjectPropertiesInSignature()) {
				prop.add(obp);
				//System.out.println("Object Property:" + prop.get(prop.size() - 1));
			}
			for (OWLDataProperty dp : ontology.getDataPropertiesInSignature()) {
				dataProp.add(dp);
				//System.out.println("Data Properties " + dataProp.get(dataProp.size() - 1));
			}
			
			showAllAxioms();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void showAllAxioms() {
		for(OWLClass cls: classes){
			
		}
	}
}
