package org.k1s.orn



import org.semanticweb.owlapi.model.IRI;


import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;


class ORNUtils {

	static String file2String(file){
		return file.text
	}
	static int numClosingAxioms =  0
	static void closeTheWorldForIndiviuals(OWLOntology ontology){
		numClosingAxioms =  0
		def individs = []
		Set individuals = ontology.getIndividualsInSignature()
		
		individuals.each { OWLNamedIndividual individual ->
//			println individual.getIRI().toString().substring( individual.getIRI().toString().lastIndexOf("/") + 1)
			def name = individual.getIRI().toString().substring( individual.getIRI().toString().lastIndexOf("/") + 1)
			individs << new Individual(name: name,
									   fullName: individual.getIRI().toString(),
									   namedIndividual: individual )
			 
		}
		
		def manager = ontology.getOWLOntologyManager()
		OWLDataFactoryImpl factory = manager.getOWLDataFactory()
		addThingSuperClass(ontology, factory, individuals)
		addNegativeProperties(ontology, factory, individuals)
		
		
		println "added $numClosingAxioms axioms"
	}

	private static void addNegativeProperties(OWLOntology ontology, OWLDataFactoryImpl factory, individuals){
		//For each property
		def propertyAssertions = ontology.getObjectPropertiesInSignature()
		propertyAssertions.each{ OWLObjectPropertyImpl oop ->
			//for each individual
			individuals.each{ OWLNamedIndividual ind ->
				//check each individual is connected by proptery, if not, add negative assertion
				def propsForInd = getObjectPropertiesForIndividual(ontology, ind)
				def nonConnectedInds =  []
				individuals.each {OWLNamedIndividual innerInd ->
						def isConnected = false
						propsForInd.each { opa-> 
							if(opa.getProperty().equals(oop) && innerInd.equals(opa.getObject())) {
								isConnected = true
							}
						}
						if(!isConnected) nonConnectedInds << innerInd
				}
				nonConnectedInds.each { 
					def axiom = factory.getOWLNegativeObjectPropertyAssertionAxiom(oop, ind, it)
					//println "addning neg axiom: $axiom"
					numClosingAxioms++
					ontology.getOWLOntologyManager().addAxiom(ontology, axiom)
				}
			}
			
//			println oop
//			println oop.class
		}
				
//		individuals.each{ OWLNamedIndividual ind ->
//			println ind.getProperties()
//			println ontology.getObjectPropertiesInSignature()
//			
//			
//			getObjectPropertiesForIndividual(ontology, ind)
//		}
	}
	
	private static def getObjectPropertiesForIndividual(OWLOntology ontology, OWLIndividual individual){
		//println "opa: "+ontology.getObjectPropertyAssertionAxioms(individual)
		return ontology.getObjectPropertyAssertionAxioms(individual)
		
//		Set properties = ontology.getObjectPropertiesInSignature()
//		def retval = []
//		properties.each{ org.semanticweb.owlapi.model.OWLObjectProperty prop ->
//			println "*********************"
//			println "prop: "+prop
//			println "donains: "+prop.getDomains(ontology)
//			println "referencing axioms: "+ prop.getReferencingAxioms(ontology)
//			println "ranges: "+prop.getRanges(ontology)
			
//			println "*********************"
//		}
	}
	
	private static void addThingSuperClass(ontology, factory, individuals){
		def oneOf = factory.getOWLObjectOneOf(individuals)
		
		OWLClassImpl owlThing = factory.getOWLClass(IRI.create("http://www.w3.org/2002/07/owl#Thing"))
		
		def subClassOf = factory.getOWLSubClassOfAxiom( owlThing, oneOf)
		numClosingAxioms++
		ontology.getOWLOntologyManager().addAxiom(ontology, subClassOf)
		
		println "closing thing: $subClassOf"
	}
		
}

class Individual {
	String name
	String fullName
	OWLNamedIndividual namedIndividual
}