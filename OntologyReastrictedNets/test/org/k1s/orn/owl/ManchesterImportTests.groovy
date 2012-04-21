package org.k1s.orn.owl;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import static org.junit.Assert.*;

class ManchesterImportTests {
	String mch1 = """
	Prefix: : <http://k1s.org/thesuperont/>
	Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	Prefix: owl: <http://www.w3.org/2002/07/owl#>
	Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>
	
	Ontology: <http://k1s.org/thesuperont>
		
	Class: Thesuperclass
	
	"""
	
	String mch2 = """
	Prefix: : <http://k1s.org/thesubont/>
	Prefix: su: <http://k1s.org/thesuperont/>
	Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	Prefix: owl: <http://www.w3.org/2002/07/owl#>
	Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>
	
	Ontology: <http://k1s.org/thesubont>
	
	Import: <http://t.k1s.org/thesuperont.owl>
	
	Class: Thesubclass
		SubClassOf: su:Thesuperclass

	"""
	
	
	@Test
	void testRemoteIsParseable(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager()
		OWLOntology ontology = manager.loadOntology(IRI.create("http://t.k1s.org/thesuperont.owl"))
		assertEquals( 1, ontology.getAxioms().size())
	}
	
	@Test
	void testImports(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager()
		
		def source = new StringDocumentSource(mch2)
		
		try{
			def ontology = manager.loadOntologyFromOntologyDocument(source)
		}catch(Exception e){
			e.printStackTrace()
			fail()
		}

	}
	
}
