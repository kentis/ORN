package org.k1s.orn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;

import org.eclipse.jface.text.IDocument;
import org.k1s.orn.owl.FuntionalSyntaxBuilder;
import org.k1s.orn.owl.ORNConstants;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

public class ORNVerifier {

	
	public static boolean isValid(PetriNet pn, File file) {
		String ontology = ORNUtils.file2String(file);
		return isValid(pn, ontology);
	}
	
	
	public static boolean isValid(PetriNet pn, String ontology) {
		String netOnt = pn2FunctionalSyntax(pn, ontology);
		System.out.println(netOnt);
		return isConsistent(netOnt);
	}
	
	public static boolean isConsistent(String ont) {
		long initTime;
		long intitiated;
		long closed;
		long consistent;
		try {
			initTime = System.currentTimeMillis();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			
			File basicFile = new File("/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/basic.fowl");
			SimpleIRIMapper basicMapper = new SimpleIRIMapper(new URI(ORNConstants.BASIC_IRI), IRI.create(basicFile));
			
			File cpnFile = new File("/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/cpn.fowl");
			SimpleIRIMapper cpnMapper = new SimpleIRIMapper(new URI(ORNConstants.CPN_IRI)
															, IRI.create(cpnFile));
			manager.addIRIMapper(basicMapper);
			manager.addIRIMapper(cpnMapper);

			
			
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
			intitiated = System.currentTimeMillis();
			ORNUtils.closeTheWorldForIndiviuals(ontology);
			closed = System.currentTimeMillis();
//			System.out.println();
//			System.out.println();
//			System.out.println(ontology);
//			for(Object o : ontology.getAxioms()){
//				System.out.println(o);
//			}
			OWLReasoner reasoner = new Reasoner(ontology);
			boolean cons = reasoner.isConsistent();
			consistent = System.currentTimeMillis();
			
			System.out.println("inititating: "+ (intitiated-  initTime) );
			System.out.println("closing: "+(closed - intitiated));
			System.out.println("checking: "+ (consistent - closed));

			
			if(!cons){
				printReason(ontology, manager);
				return false;
			} else return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	private static void printReason(OWLOntology ontology,OWLOntologyManager manager) {
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
		
		reasoner.setOntology(ontology);
		
		
		if(!reasoner.isConsistent()){
			System.out.println("INCONSISTENT");
			try{reasoner.getInconsistentClasses();}catch(Exception e){System.out.println("reason: "+e.getMessage());}
		}else{
			System.out.println("CONSISTENT");
		}
		
	}


	public static boolean isValid(String ont) {
	
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
//			File basicFile = new File("OwlModels/basic.owl");
//			System.out.println(basicFile);
//			System.out.println(readFileAsString(basicFile));
//			SimpleIRIMapper basicMapper = new SimpleIRIMapper(new URI(ORNConstants.BASIC_IRI), IRI.create(basicFile));
//			
//			File cpnFile = new File("OwlModels/cpn.owl");
//			SimpleIRIMapper cpnMapper = new SimpleIRIMapper(new URI(ORNConstants.CPN_IRI)
//															, IRI.create(cpnFile));
//			manager.addIRIMapper(basicMapper);
//			manager.addIRIMapper(cpnMapper);
//			
//			OWLOntology basicOntology = manager.loadOntologyFromOntologyDocument(basicFile);
//			OWLOntology cpnOntology = manager.loadOntologyFromOntologyDocument(cpnFile);
//			
//			System.out.println(basicOntology);
//			System.out.println(cpnOntology);
			
			//manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
//	/** @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
//	    */ 
//	    private static String readFileAsString(File filePath)
//	    throws java.io.IOException{
//	        StringBuffer fileData = new StringBuffer(1000);
//	        BufferedReader reader = new BufferedReader(
//	                new FileReader(filePath));
//	        char[] buf = new char[1024];
//	        int numRead=0;
//	        while((numRead=reader.read(buf)) != -1){
//	            String readData = String.valueOf(buf, 0, numRead);
//	            fileData.append(readData);
//	            buf = new char[1024];
//	        }
//	        reader.close();
//	        return fileData.toString();
//	    }
//	    
//	public OWLOntology pn2Ontology(PetriNet pn){
//		return null;
//	}
//	
	
	public String pn2ManchesterSyntax(PetriNet pn){
		StringBuilder sb = new StringBuilder();
		
		sb.append(ORNConstants.OWL_TEMPLATE).append("\n");
		
		Page rootPage = pn.getPage().get(0);
		
		page2ManchesterSyntax(rootPage, sb);
		
		return sb.toString();
	}
	
	private void page2ManchesterSyntax(Page rootPage, StringBuilder sb) {
		sb.append("Individual: ");
		
	}
	
	public static String pn2FunctionalSyntax(PetriNet pn, String ontology){
		StringBuilder netOnt = new StringBuilder();
		
		netOnt.append(ontology.substring(0, ontology.lastIndexOf(')')-1));
		
		new FuntionalSyntaxBuilder().page2FunctionalSyntax(pn.getPage().get(0), netOnt, true);
		
		netOnt.append("\n)");
		
		return netOnt.toString();
	}

	
}
