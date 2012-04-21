package org.k1s.orn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
//import com.*;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.FBRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class OntologyTest {

	String rules = "[stream: (?c rdf:type http://k1s.org/OntologyReastrictedNets/basicnetworking#Channel), (?c http://k1s.org/OntologyReastrictedNets/basicnetworking#property http://k1s.org/OntologyReastrictedNets/basicnetworking#GuaranteedDelivery), (?c http://k1s.org/OntologyReastrictedNets/basicnetworking#property http://k1s.org/OntologyReastrictedNets/basicnetworking#PreservesOrder) -> (?c rdf:type http://k1s.org/OntologyReastrictedNets/basicnetworking#Stream)]\n"
	 				+ "[datagram: (?c rdf:type http://k1s.org/OntologyReastrictedNets/basicnetworking#Channel) -> (?c rdf:type http://k1s.org/OntologyReastrictedNets/basicnetworking#Datagram)]";
		
	
	public void dill(){
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF);
		
		try {
			System.setProperty("log4j.defaultInitOverride", "true");
			//m.read(new FileInputStream("/home/kent/ws-ePNK/OntologyReastrictedNets/OwlModels/basic.owl"), "http://k1s.org");
			//m.read(new FileInputStream("/home/kent/ws-ePNK/OntologyReastrictedNets/OwlModels/basic_networking.owl"), "http://k1s.org");
			//m.read(new FileInputStream("/home/kent/ws-ePNK/OntologyReastrictedNets/OwlModels/basic_networking.rules"), "http://k1s.org");
			//m.read(new FileInputStream("/home/kent/ws-ePNK/OntologyReastrictedNets/OwlModels/basic_networking_instance.owl"), "http://k1s.org");
			System.out.println(m);
			
			Iterator i = m.listClasses();
			
			while(i.hasNext()){
				System.out.println(i.next());
			}
			
			
			((FBRuleReasoner) m.getReasoner()).addRules(Rule.parseRules(rules));
			
			
			ValidityReport vr = m.validate();
			
			
			System.out.println(vr);
			
			
			System.out.println("valid: "+vr.isValid());
			System.out.println("clean: "+vr.isClean());
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean validate(PetriNet pn){
		
		 //lParser parser = new SadlParser();
		
		
		
		return false;
	}
	
	
	public static void main(String[] args){
		new OntologyTest().dill();
	}
}


