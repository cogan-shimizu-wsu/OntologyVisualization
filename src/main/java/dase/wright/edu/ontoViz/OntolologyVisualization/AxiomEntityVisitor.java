package dase.wright.edu.ontoViz.OntolologyVisualization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class AxiomEntityVisitor implements OWLObjectVisitor {
	/*private final ShortFormProvider shortFormProvider = new SimpleShortFormProvider();*/
	/*public final OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);*/
	
	ArrayList<String> stack;

	public AxiomEntityVisitor(ArrayList<String> st) {
		this.stack = st;
	}

	@Override
	public void visit(IRI iri) {
		OWLObjectVisitor.super.visit(iri);
	}

	@Override
	public void visit(
			OWLAnonymousIndividual individual) {/*
												 * blank-nodes, how to deal with
												 * them?
												 */
		OWLObjectVisitor.super.visit(individual);
		System.out.println("OWLAnonymousIndividual individual: " + individual);
	}

	@Override
	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getProperty().accept(this);
		stack.add("subclass");
		stack.add("not");
		axiom.getProperty().accept(this);
		stack.add("inverse");
	}

	@Override
	public void visit(OWLClass ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add(ce.toStringID());
	}

	@Override
	public void visit(
			OWLClassAssertionAxiom axiom) { /*
											 * Should I ever reach here if I
											 * only check logical axioms?
											 */
		OWLObjectVisitor.super.visit(axiom);
		axiom.getIndividual().accept(this);
		axiom.getClassExpression().accept(this);
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("all");
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLDataComplementOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLDataComplementOf node: " + node);
	}

	@Override
	public void visit(OWLDataHasValue ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("some");
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLDataIntersectionOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLDataIntersectionOf node: " + node);
	}

	@Override
	public void visit(OWLDataOneOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLDataOneOf node: " + node);
	}

	@Override
	public void visit(OWLDataProperty property) {
		OWLObjectVisitor.super.visit(property);
		stack.add(property.toStringID());
	}

	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
		axiom.getObject().accept(this);
	}

	//@Override
	/*public void visit(OWLDataPropertyDomainAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		dataFactory.getOWLDataSomeValuesFrom(axiom.getProperty(), dataFactory.getTopDatatype())
				.accept(this);// why and how and do I even need this method? //
		stack.add("SUBCLASS");
		axiom.getDomain().accept(this);
	}*/

	//@Override
	/*public void visit(OWLDataPropertyRangeAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TOP");
		stack.add("SUBCLASS");
		dataFactory.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
	}*/

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("some");
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLDatatype node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		/*System.out.println("OWLDatatype node: " + node);*/
		stack.add(node.toStringID());
	}

	@Override
	public void visit(OWLDatatypeDefinitionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getDatatype().accept(this);
		stack.add("OWLDatatypeDefinitionAxiom");
		axiom.getDataRange().accept(this);
	}

	@Override
	public void visit(OWLDatatypeRestriction node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLDatatypeRestriction node: " + node);
	}

	@Override
	public void visit(OWLDataUnionOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLDataUnionOf node: " + node);
	}

	@Override
	public void visit(OWLDeclarationAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("DECLARATION");
		axiom.getEntity().accept(this);
	}

	@Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {/* needed? */
		OWLObjectVisitor.super.visit(axiom);
		for (Iterator<OWLIndividual> it = axiom.individuals().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("NOT_EQUAL");
			} else {
				stack.add("End of List");
			}
		}
	}

	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		List<OWLClassExpression> classExpressions = org.semanticweb.owlapi.util.OWLAPIStreamUtils
				.asList(axiom.classExpressions());
		stack.add("AllDisjoint");
		for (Iterator<OWLClassExpression> it = classExpressions.iterator(); it.hasNext();) {
			it.next().accept(this);
			if (!it.hasNext())
				stack.add("End of disjoint class list");
		}
	}

	@Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		for (Iterator<OWLDataPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("DisjointDataProperty");
			} else
				stack.add("End Of List");
		}
	}

	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("DISJOINTObjectProperty");
		for (Iterator<OWLObjectPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (!it.hasNext()) {
				stack.add("End Of List");
			}
		}
	}

	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("DisjointClasses");
		for (Iterator<OWLClassExpression> it = axiom.classExpressions().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (!it.hasNext()) {
				stack.add("End Of List");
			}
		}
	}

	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		List<OWLClassExpression> classExpressions = org.semanticweb.owlapi.util.OWLAPIStreamUtils
				.asList(axiom.classExpressions());
		stack.add("Equivalent");
		for (Iterator<OWLClassExpression> it = classExpressions.iterator(); it.hasNext();) {
			it.next().accept(this);
			if (!it.hasNext())
				stack.add("endOfEquivalentClassList");
		}
	}

	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		/*for (Iterator<OWLDataPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("Equivalent");
			} else {
				stack.add("End of list");
			}
		}*/
	}

	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		/*for (Iterator<OWLObjectPropertyExpression> it = axiom.properties().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("EQUIVALENT");
			} else {
				stack.add("End of List");
			}
		}*/
	}

	@Override
	public void visit(OWLFacetRestriction node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLFacetRestriction node: " + node);
	}

	/*@Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TOP");
		stack.add("SUBCLASS");
		dataFactory.getOWLDataMaxCardinality(1, axiom.getProperty()).accept(this);
	}
*/
	/*@Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TOP");
		stack.add("SUBCLASS");
		dataFactory.getOWLObjectMaxCardinality(1, axiom.getProperty()).accept(this);
	}*/

	@Override
	public void visit(OWLHasKeyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.println("OWLHasKeyAxiom axiom: " + axiom);
	}

	/*@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TOP");
		stack.add("SUBCLASS");
		OWLObjectPropertyExpression property = axiom.getProperty();
		if (property.isAnonymous())  What is anonymous property 
		{
			dataFactory.getOWLObjectMaxCardinality(1, property).accept(this);
		} else {
			OWLObjectPropertyExpression prop = dataFactory.getOWLObjectInverseOf(property.asOWLObjectProperty());
			dataFactory.getOWLObjectMaxCardinality(1, prop).accept(this);
		}
	}*/

	@Override
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		/*axiom.getFirstProperty().accept(this);
		stack.add("EQUIVALENT");
		axiom.getSecondProperty().accept(this);
		stack.add("INVERSE");*/
	}

	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("IrreflexiveObjectProperty");
		axiom.getProperty().accept(this);
	}

	@Override
	public void visit(OWLLiteral node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		//System.out.println("OWLLiteral node: " + node);
		stack.add(node.getLiteral());
	}

	@Override
	public void visit(OWLNamedIndividual individual) {
		OWLObjectVisitor.super.visit(individual);
		stack.add(individual.toStringID());
	}

	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("not");
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
		axiom.getObject().accept(this);
	}

	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("not");
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
		axiom.getObject().accept(this);
	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("all");
		ce.getProperty().accept(this);
		getNestedFillers(ce.getFiller());
	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("not");
		getNestedFillers(ce.getOperand());
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("some");
		ce.getProperty().accept(this);
		stack.add("SELF");
	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("some");
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {

		OWLObjectVisitor.super.visit(ce);
		for (Iterator<? extends OWLClassExpression> it = ce.operands().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("AND");
			}
		}
	}

	@Override
	public void visit(OWLObjectInverseOf property) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(property);
		//System.out.println("OWLObjectInverseOf property: " + property);
		stack.add("OWLObjectInverseOf");
		property.getNamedProperty().accept(this);
		//stack.add(property.toString());
	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		OWLObjectVisitor.super.visit(ce);
		for (Iterator<? extends OWLIndividual> it = ce.individuals().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("OR");
			}
		}
	}

	@Override
	public void visit(OWLObjectProperty property) {
		OWLObjectVisitor.super.visit(property);
		stack.add(property.toStringID());
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
		axiom.getObject().accept(this);
	}

	/*@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		dataFactory.getOWLObjectSomeValuesFrom(axiom.getProperty(), dataFactory.getOWLThing()).accept(this);
		stack.add("SUBCLASS");
		axiom.getDomain().accept(this);
	}*/

	/*@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TOP");
		stack.add("SUBCLASS");
		dataFactory.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
	}*/

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("some");
		ce.getProperty().accept(this);
		getNestedFillers(ce.getFiller());
	}

	private void getNestedFillers(OWLClassExpression ce) {
		ce.accept(this);
	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		OWLObjectVisitor.super.visit(ce);
		for (Iterator<? extends OWLClassExpression> it = ce.operands().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("OR");
			}
		}
	}

	@Override
	public void visit(OWLOntology ontology) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ontology);
	}

	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("ReflexiveProperty");
		axiom.getProperty().accept(this);
	}

	@Override
	public void visit(OWLSameIndividualAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		for (Iterator<OWLIndividual> it = axiom.individuals().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("OWLSameIndividualAxiom");
			} else {

			}
		}
	}

	@Override
	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.println("OWLSubAnnotationPropertyOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		/* for SubClass */
		stack.add("Subclass");
		axiom.getSubClass().accept(this);
		/* Push into Stack */
		/* for SuperClass */
		axiom.getSuperClass().accept(this);
	}

	@Override
	public void visit(OWLSubDataPropertyOfAxiom axiom) {/* needed? */
		OWLObjectVisitor.super.visit(axiom);
		axiom.getSubProperty().accept(this);
		stack.add("SUBCLASS");
		axiom.getSuperProperty().accept(this);
	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getSubProperty().accept(this);
		stack.add("SUBCLASS");
		axiom.getSuperProperty().accept(this);
	}

	@Override
	public void visit(OWLSubPropertyChainOfAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it.hasNext();) {
			it.next().accept(this);
			if (it.hasNext()) {
				stack.add("CIRCULAR");
			} else {
				stack.add("End of list");
			}
		}
		stack.add("SUBCLASS");
		axiom.getSuperProperty().accept(this);
	}

	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		axiom.getProperty().accept(this);
		stack.add("EQUIVALENT");
		axiom.getProperty().accept(this);
		stack.add("INVERSE");
	}

	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
		stack.add("TransitiveProperty");
		axiom.getProperty().accept(this);
	}

	/*****************SWRL*******************/
	@Override
	public void visit(SWRLBuiltInAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLBuiltInAtom node: " + node);
	}

	@Override
	public void visit(SWRLClassAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLClassAtom node: " + node);
	}

	@Override
	public void visit(SWRLDataPropertyAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLDataPropertyAtom node: " + node);
	}

	@Override
	public void visit(SWRLDataRangeAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLDataRangeAtom node: " + node);
	}

	@Override
	public void visit(SWRLDifferentIndividualsAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLDifferentIndividualsAtom node: " + node);
	}

	@Override
	public void visit(SWRLIndividualArgument node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLIndividualArgument node: " + node);
	}

	@Override
	public void visit(SWRLLiteralArgument node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLLiteralArgument node: " + node);
	}

	@Override
	public void visit(SWRLObjectPropertyAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLObjectPropertyAtom node: " + node);
	}

	@Override
	public void visit(SWRLRule node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLRule node: " + node);
	}

	@Override
	public void visit(SWRLSameIndividualAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLSameIndividualAtom node: " + node);
	}

	@Override
	public void visit(SWRLVariable node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("SWRLVariable node: " + node);
	}
	/*****************SWRL*******************/


	@Override
	public void visit(OWLAnnotation node) {
		OWLObjectVisitor.super.visit(node);
	}

	@Override
	public void visit(
			OWLAnnotationAssertionAxiom axiom) {/* not needed, most likely */
		OWLObjectVisitor.super.visit(axiom);
		stack.add("Annotation");
		axiom.getSubject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getValue().accept(this);
	}

	@Override
	public void visit(OWLAnnotationProperty property) {
		OWLObjectVisitor.super.visit(property);
	}

	@Override
	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
	}

	@Override
	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
		OWLObjectVisitor.super.visit(axiom);
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLObjectExactCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLDataExactCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
		ce.getFiller().accept(this);
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLDataMaxCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLDataMinCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLObjectMaxCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
		getNestedFillers(ce.getFiller());
	}

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		OWLObjectVisitor.super.visit(ce);
		stack.add("OWLObjectMinCardinality");
		stack.add(Integer.toString(ce.getCardinality()));
		ce.getProperty().accept(this);
		getNestedFillers(ce.getFiller());
	}
}