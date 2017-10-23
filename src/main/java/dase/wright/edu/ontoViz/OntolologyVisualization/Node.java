package dase.wright.edu.ontoViz.OntolologyVisualization;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class Node
{

	String					entity;
	EntityType<?>	type;
	int						typeIndex;

	public int getTypeIndex()
	{
		return typeIndex;
	}

	public static ShortFormProvider		shortFormProvider	= new SimpleShortFormProvider();
	public static OWLEntityComparator	entityComparator	= new OWLEntityComparator(shortFormProvider);

	public String getShortForm()
	{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		IRI iri = IRI.create(entity);
		OWLEntity cls = factory.getOWLEntity(EntityType.CLASS, iri);
		String label = (escapeName(shortFormProvider.getShortForm(cls)));
		return label;
	}

	public static String escapeName(String name)
	{
		return name.replace("_", "\\_").replace("#", "\\#");
	}

	public void setTypeIndex(int typeIndex)
	{
		this.typeIndex = typeIndex;
	}

	public Node(String entityName, EntityType<?> etype)
	{
		this.entity = entityName;
		this.type = etype;
	}

	public Node(String entityName, int tI)
	{
		this.entity = entityName;
		this.typeIndex = tI;
		this.type = null;
	}

	public String getEntityName()
	{
		return entity;
	}

	public void setEntity(String entityUri)
	{
		this.entity = entityUri;
	}

	public EntityType<?> getType()
	{

		return type;
	}

	public void setType(EntityType<?> type)
	{
		this.type = type;
	}

}
