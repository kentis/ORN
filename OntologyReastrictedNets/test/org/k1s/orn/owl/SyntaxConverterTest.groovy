package org.k1s.orn.owl;

import static org.junit.Assert.*;

import org.junit.Test;

class SyntaxConverterTest {

String manchesterSyntax = """
Prefix: : <http://example.com/owl/families/>
Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>
Prefix: owl: <http://www.w3.org/2002/07/owl#>
Ontology: <http://example.com/owl/families>

Class: Person
  Annotations:  rdfs:comment "Represents the set of all people."


Class: Woman
  SubClassOf: Person



Class: Man
  SubClassOf: Person

DisjointClasses: Woman, Man
"""





String rdfXmlSyntax = """
<!DOCTYPE rdf:RDF [
    <!ENTITY owl "http://www.w3.org/2002/07/owl#" >
    <!ENTITY xsd "http://www.w3.org/2001/XMLSchema#" >
    <!ENTITY rdfs "http://www.w3.org/2000/01/rdf-schema#" >
]>
 
<rdf:RDF xml:base="http://example.com/owl/families/"
   xmlns="http://example.com/owl/families/"
   xmlns:owl="http://www.w3.org/2002/07/owl#"
   xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
   xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
   xmlns:xsd="http://www.w3.org/2001/XMLSchema#">
 
   <owl:Ontology rdf:about="http://example.com/owl/families">
   </owl:Ontology>
 
   <owl:Class rdf:about="Woman">
     <rdfs:subClassOf rdf:resource="Person"/>
   </owl:Class>

   <owl:Class rdf:about="Person">
     <rdfs:comment>Represents the set of all people.</rdfs:comment>
   </owl:Class>

   <owl:Class rdf:about="Man">
     <rdfs:subClassOf rdf:resource="Person"/>
   </owl:Class>
   <owl:Axiom>
     <owl:annotatedSource rdf:resource="Man"/>
     <owl:annotatedProperty rdf:resource="&rdfs;subClassOf"/>
     <owl:annotatedTarget rdf:resource="Person"/>
     <rdfs:comment>States that every man is a person.</rdfs:comment>
   </owl:Axiom>
 
   <owl:AllDisjointClasses>
     <owl:members rdf:parseType="Collection">
       <owl:Class rdf:about="Woman"/>
       <owl:Class rdf:about="Man"/>
     </owl:members>
   </owl:AllDisjointClasses>
 
</rdf:RDF>
"""
	
	
	@Test
	public void testManchesterToRDF() {
		String rdf = SyntaxConverter.ManchesterToRDF(manchesterSyntax)
		assertTrue(rdf.contains("<rdf:RDF"));
		
		assertTrue(rdf.contains("rdf:about"));
		assertTrue(rdf.contains("Man"));
		assertTrue(rdf.contains("Woman"));
		assertTrue(rdf.contains("Person"));
		
	}

	@Test
	public void testRDFXMLToManchester() {
		String manchester = SyntaxConverter.RDFXML2Manchetster(rdfXmlSyntax)
		println manchester
		assertTrue(manchester.contains("Class:"));
		
		
		
		assertTrue(manchester.contains("Man"));
		assertTrue(manchester.contains("Woman"));
		assertTrue(manchester.contains("Person"));
		
	}
}
