package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.IPort;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.input.GraphEditorInputMode;

/**
 * This Project is supposed to produce an interactive visualization for ontologies for ensuring better understandability
 * @author: Nazifa Karima
 */
public class OntologyVisualization {

	public static OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	public static File fullOntology = new File("src/resources/chessgame.owl");
	
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

		//System.out.println("Filepath: " + fullOntology.getAbsolutePath());
		init(manager, fullOntology, classes, prop, dataProp);

		 Runnable doHelloWorld = new Runnable() { public void run() { /*create a component for displaying and editing a graph.*/
		 GraphComponent graphComponent = new GraphComponent(); /*Enabling user-interaction*/
		 graphComponent.setInputMode(new GraphEditorInputMode());
		
		 IGraph graph = graphComponent.getGraph(); 
		 INode node1 = graph.createNode(new RectD(30, 30, 30, 30)); 
		 INode node2 = graph.createNode(new RectD(100, 30, 30, 30));
		
		 IEdge edge1 = graph.createEdge(node1, node2);
		
		 IPort portAtNode1 = graph.addPort(node1);
		
		 ILabel ln1 = graph.addLabel(node1, "n1"); 
		 ILabel ln2 = graph.addLabel(node2, "n2");
		
		 /*create a top-level window and add the graph component.*/ 
		 JFrame frame = new JFrame("Ontology Visualization");
		
		 frame.setSize(500, 500); frame.setLocationRelativeTo(null);
		 frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 frame.add(graphComponent, BorderLayout.CENTER);
		 frame.setVisible(true); } };
		 SwingUtilities.invokeLater(doHelloWorld);

	}

	/**/
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

			for (OWLClass cls : ontology.getClassesInSignature()) {
				classes.add(cls);
				/*
				 * System.out.println("Class: " + classes.get(classes.size() -
				 * 1));
				 */
				for (OWLAxiom op : ontology.getAxioms()) {
					if (op.isLogicalAxiom()) {

					//	System.out.println("Anon indiv: " + op.getAnonymousIndividuals() + "\n" + "type: "
						//		+ op.getAxiomType() + "\n" + op.getSignature());

					}

				}
			}
			writeHashMapintoFile(objPropAxioms, "ChessGame");
			for (OWLObjectProperty obp : ontology.getObjectPropertiesInSignature()) {
				prop.add(obp);
				// System.out.println("Object Property:" + prop.get(prop.size()
				// - 1));
			}
			for (OWLDataProperty dp : ontology.getDataPropertiesInSignature()) {
				dataProp.add(dp);
				// System.out.println("Data Properties " +
				// dataProp.get(dataProp.size() - 1));
			}

			showAllAxioms(objPropAxioms);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeHashMapintoFile(HashMap<OWLClass, ArrayList<OWLObjectProperty>> objPropAxioms,
			String string) {
		// File file = new File("/Users/nazifakarima/Desktop", "chessgame");
		//
		// //Blank workbook
		// XSSFWorkbook workbook = new XSSFWorkbook();
		//
		// //Create a blank sheet
		// XSSFSheet sheet = workbook.createSheet("ChessGame");
		//
		// Iterator it = objPropAxioms.entrySet().iterator();
		// while (it.hasNext()) {
		// Map.Entry pair = (Map.Entry)it.next();
		// System.out.println(pair.getKey() + " = " + pair.getValue());
		// }
		BufferedWriter writer = null;
		File chessGame = new File("/visualizations/chessGame");
		try {
			System.out.println(chessGame.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void showAllAxioms(HashMap<OWLClass, ArrayList<OWLObjectProperty>> objPropAxioms2) {
		// for (Iterator it:objPropAxioms2) {

		// }
	}
}
