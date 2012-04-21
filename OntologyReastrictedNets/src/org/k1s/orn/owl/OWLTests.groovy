package org.k1s.orn.owl;

import java.net.URI;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import com.hp.hpl.jena.ontology.Ontology;

import static org.junit.Assert.*;

class OWLTests {

	@Test
	void checkImportsForOWLTeplate(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File basicFile = new File("OwlModels/basic.owl");
		
		assertTrue(basicFile.exists())
		SimpleIRIMapper basicMapper = new SimpleIRIMapper(new URI(ORNConstants.BASIC_IRI), IRI.create(basicFile))
		
		File cpnFile = new File("OwlModels/cpn.owl");
		assertTrue(cpnFile.exists())
		
		SimpleIRIMapper cpnMapper = new SimpleIRIMapper(new URI(ORNConstants.CPN_IRI)
														, IRI.create(cpnFile))
		manager.addIRIMapper(basicMapper)
		manager.addIRIMapper(cpnMapper)
		
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
			new StringDocumentSource(ORNConstants.OWL_TEMPLATE));
		println ontology.getImports()
		println ontology.getImports().each { OWLOntology ont ->
			println ont.getClassesInSignature()
	    }
		
		assertEquals(2,  ontology.getImports().size())
		
	}	
}
