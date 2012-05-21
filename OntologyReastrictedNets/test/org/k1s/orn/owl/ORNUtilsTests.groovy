package org.k1s.orn.owl;

import org.junit.Test;
import org.k1s.orn.ORNUtils;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;



import static org.junit.Assert.*;

class ORNUtilsTests {
	
	
	def simpleValidOnt = """Prefix(:=<http://org.k1s/orn/ext/>)
  Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
  
 Ontology(<http://org.k1s/orn/ext/>
 
  Declaration( Class( :id ) )
 
  Declaration( Class( :external ) )
  
  DisjointClasses(:id :external) 
  
  SubClassOf( :external ObjectIntersectionOf(
  		ObjectAllValuesFrom( :ids :id) 
  		ObjectMinCardinality( 1 :ids :id)
    )
  )
  Declaration( NamedIndividual( :theId ) )
  ClassAssertion( :id :theId )
  
  Declaration( NamedIndividual( :theExt ) )
  ClassAssertion( :external :theExt )
  
  ObjectPropertyAssertion( :ids :theExt :theId )
  
  
 )"""
	

 def simpleInValidOnt = """Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 
Ontology(<http://org.k1s/orn/ext/>

 Declaration( Class( :id ) )

 Declaration( Class( :external ) )
 
 DisjointClasses(:id :external)
 
 
 
 SubClassOf( :external ObjectIntersectionOf(
		 ObjectAllValuesFrom( :ids :id)
		 ObjectMinCardinality( 1 :ids :id)
   )
 )
 Declaration( NamedIndividual( :theId ) )
 ClassAssertion( :id :theId )
 
 Declaration( NamedIndividual( :theExt ) )
 ClassAssertion( :external :theExt )
 
 
 

)"""
/**
 * 
 * The following is needed to make the invalid ontology inconsistent
 * 
 * NegativeObjectPropertyAssertion(:ids :theExt :theId)
 * SubClassOf(owl:Thing ObjectOneOf(:theId :theExt ))
 * DifferentIndividuals( :theId :theExt)  
 */
 
 	@Test
	void testcloseTheWorldForIndiviualsValid(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(simpleValidOnt));
		ORNUtils.closeTheWorldForIndiviuals ontology
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
			reasoner.setOntology(ontology);
		
		assertTrue(reasoner.isConsistent())
		println "axioms"
		ontology.axioms.each { 
			println it	
		}
		println "-----------------------------------"
	}
	
	
	@Test
	void testcloseTheWorldForIndiviualsInValid(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(simpleInValidOnt));
		ORNUtils.closeTheWorldForIndiviuals ontology
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
			reasoner.setOntology(ontology);
		
		assertFalse(reasoner.isConsistent())
	}
}
