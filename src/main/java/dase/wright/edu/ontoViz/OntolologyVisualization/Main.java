package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.io.File;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Main
{
	public static void main(String[] args)
	{
		OntologyVisualizer ov = new OntologyVisualizer();
		
		String directory = "src/resources/";
		String extension = ".owl";
		String filename = directory + "ontologiesProvidedByPascal/timeindexedpersonrole" + extension;
		File file = new File(filename);
		ArrayList<File> files = new ArrayList<>();
		
		files.add(file);
		
		for(File f : files)
		{
			try
			{
				// Load the Ontology
				OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

				// Force silent import errors. (ESP wrt purl.org)
				manager.setOntologyLoaderConfiguration(manager.getOntologyLoaderConfiguration()
				        .setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT));

				// Load Ontology
				IRI iri = IRI.create(f.toURI());

				OWLOntology ontology = manager.loadOntologyFromOntologyDocument(iri);

				// Create the visualization
				ov.createVisualization(ontology);

			}
			catch(OWLOntologyCreationException e)
			{
				System.out.println("Could not create ontology from: " + file);
			}
		}
		
//		Visualizer.visualization(visualizer);
	}
}
