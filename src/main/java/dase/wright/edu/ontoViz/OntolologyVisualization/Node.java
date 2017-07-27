package dase.wright.edu.ontoViz.OntolologyVisualization;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.OWLEntity;

public class Node {
	String entityUri;

	public String getEntityUri() {
		return entityUri;
	}

	public void setEntityUri(String entityUri) {
		this.entityUri = entityUri;
	}

	public EntityType<OWLEntity> getType() {
		
		return type;
	}

	public void setType(EntityType<OWLEntity> type) {
		this.type = type;
	}

	EntityType<OWLEntity> type;
	
}
