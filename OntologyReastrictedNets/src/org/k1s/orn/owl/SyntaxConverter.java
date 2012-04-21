package org.k1s.orn.owl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class SyntaxConverter {
	
	
	public static String ManchesterToRDF(String manchesterSyntax) throws OWLOntologyCreationException, OWLOntologyStorageException{
		String rdf = null;
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(manchesterSyntax));
		
		StringDocumentTarget target = new StringDocumentTarget(); 
		manager.saveOntology(ontology, new RDFXMLOntologyFormat(), target);
		
		rdf = target.toString();
		return rdf;
	}


	public static String RDFXML2Manchetster(String rdfSyntax) throws OWLOntologyCreationException, OWLOntologyStorageException{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(rdfSyntax));
		
		StringDocumentTarget target = new StringDocumentTarget(); 
		manager.saveOntology(ontology, new ManchesterOWLSyntaxOntologyFormat(), target);
		
		String  manchesterSyntax = target.toString();
		return manchesterSyntax;
		
	}
	
}
