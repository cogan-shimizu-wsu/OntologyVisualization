package dase.wright.edu.ontoViz.OntolologyVisualization;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.OWLEntity;

public class Node {
	String entity;
	EntityType<OWLEntity> type;
	int typeIndex;
	
	public int getTypeIndex() {
		return typeIndex;
	}

	public void setTypeIndex(int typeIndex) {
		this.typeIndex = typeIndex;
	}

	public Node(String entityName, EntityType<OWLEntity> etype) {
		this.entity = entityName;
		this.type = etype;
	}
	
	public Node(String entityName, int tI) {
		this.entity = entityName;
		this.typeIndex = tI;
		this.type = null;
	}

	public String getEntityName() {
		return entity;
	}

	public void setEntity(String entityUri) {
		this.entity = entityUri;
	}

	public EntityType<OWLEntity> getType() {

		return type;
	}

	public void setType(EntityType<OWLEntity> type) {
		this.type = type;
	}

}
