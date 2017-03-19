package dase.wright.edu.ontoViz.OntolologyVisualization;

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
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
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
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
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

public class AxiomEntityVisitor implements OWLObjectVisitor {
	public AxiomEntityVisitor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(IRI iri) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(iri);
		System.out.println("IRI: " + iri);
	}

	@Override
	public void visit(OWLAnnotation node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.println("OWLAnnotation node: " + node);
	}

	@Override
	public void visit(OWLAnnotationAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.println("OWLAnnotationAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLAnnotationProperty property) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(property);
		System.out.println("OWLAnnotationProperty property: " + property);
	}

	@Override
	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.println("OWLAnnotationPropertyDomainAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLAnnotationPropertyRangeAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLAnonymousIndividual individual) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(individual);
		System.out.print("OWLAnonymousIndividual individual: " + individual);
	}

	@Override
	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLAsymmetricObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLClass ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.println("OWLClass ce: " + ce);
	}

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.println("OWLClassAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.println("OWLDataAllValuesFrom ce: " + ce);
	}

	@Override
	public void visit(OWLDataComplementOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDataComplementOf node: " + node);
	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLDataExactCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLDataHasValue ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLDataHasValue ce: " + ce);
	}

	@Override
	public void visit(OWLDataIntersectionOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDataIntersectionOf node: " + node);
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLDataMaxCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLDataMinCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLDataOneOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDataOneOf node: " + node);
	}

	@Override
	public void visit(OWLDataProperty property) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(property);
		System.out.print("OWLDataProperty property: " + property);
	}

	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDataPropertyAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDataPropertyDomainAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDataPropertyRangeAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLDataSomeValuesFrom ce: " + ce);
	}

	@Override
	public void visit(OWLDatatype node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDatatype node: " + node);
	}

	@Override
	public void visit(OWLDatatypeDefinitionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDatatypeDefinitionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDatatypeRestriction node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDatatypeRestriction node: " + node);
	}

	@Override
	public void visit(OWLDataUnionOf node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLDataUnionOf node: " + node);
	}

	@Override
	public void visit(OWLDeclarationAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDeclarationAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDifferentIndividualsAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDisjointClassesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDisjointDataPropertiesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDisjointObjectPropertiesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLDisjointUnionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLEquivalentClassesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLEquivalentDataPropertiesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLEquivalentObjectPropertiesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLFacetRestriction node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLFacetRestriction node: " + node);
	}

	@Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLFunctionalDataPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLFunctionalObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLHasKeyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLHasKeyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLInverseFunctionalObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLInverseObjectPropertiesAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLIrreflexiveObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLLiteral node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("OWLLiteral node: " + node);
	}

	@Override
	public void visit(OWLNamedIndividual individual) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(individual);
		System.out.print("OWLNamedIndividual individual: " + individual);
	}

	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLNegativeDataPropertyAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLNegativeObjectPropertyAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectAllValuesFrom ce: " + ce);
	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectComplementOf ce: " + ce);
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectExactCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectHasSelf ce: " + ce);
	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectHasValue ce: " + ce);
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectIntersectionOf ce: " + ce);
	}

	@Override
	public void visit(OWLObjectInverseOf property) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(property);
		System.out.print("OWLObjectInverseOf property: " + property);
	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectMaxCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectMinCardinality ce: " + ce);
	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectOneOf ce: " + ce);
	}

	@Override
	public void visit(OWLObjectProperty property) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(property);
		System.out.print("OWLObjectProperty property: " + property);
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLObjectPropertyAssertionAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLObjectPropertyDomainAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLObjectPropertyRangeAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectSomeValuesFrom ce: " + ce);
	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ce);
		System.out.print("OWLObjectUnionOf ce: " + ce);
	}

	@Override
	public void visit(OWLOntology ontology) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(ontology);
		System.out.print("OWLOntology ontology: " + ontology);
	}

	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLReflexiveObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSameIndividualAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSameIndividualAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSubAnnotationPropertyOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSubClassOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubDataPropertyOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSubDataPropertyOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSubObjectPropertyOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSubPropertyChainOfAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSubPropertyChainOfAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLSymmetricObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(axiom);
		System.out.print("OWLTransitiveObjectPropertyAxiom axiom: " + axiom);
	}

	@Override
	public void visit(SWRLBuiltInAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLBuiltInAtom node: " + node);
	}

	@Override
	public void visit(SWRLClassAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLClassAtom node: " + node);
	}

	@Override
	public void visit(SWRLDataPropertyAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLDataPropertyAtom node: " + node);
	}

	@Override
	public void visit(SWRLDataRangeAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLDataRangeAtom node: " + node);
	}

	@Override
	public void visit(SWRLDifferentIndividualsAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLDifferentIndividualsAtom node: " + node);
	}

	@Override
	public void visit(SWRLIndividualArgument node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLIndividualArgument node: " + node);
	}

	@Override
	public void visit(SWRLLiteralArgument node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLLiteralArgument node: " + node);
	}

	@Override
	public void visit(SWRLObjectPropertyAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLObjectPropertyAtom node: " + node);
	}

	@Override
	public void visit(SWRLRule node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLRule node: " + node);
	}

	@Override
	public void visit(SWRLSameIndividualAtom node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLSameIndividualAtom node: " + node);
	}

	@Override
	public void visit(SWRLVariable node) {
		// TODO Auto-generated method stub
		OWLObjectVisitor.super.visit(node);
		System.out.print("SWRLVariable node: " + node);
	}
}
