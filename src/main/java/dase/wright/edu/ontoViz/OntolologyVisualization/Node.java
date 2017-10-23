package dase.wright.edu.ontoViz.OntolologyVisualization;

import org.semanticweb.owlapi.model.EntityType;

public class Node
{
	private String			entity;
	private EntityType<?>	type;
	private int				typeIndex;

	public int getTypeIndex()
	{
		return typeIndex;
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
