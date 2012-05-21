package org.k1s.orn.owl;


import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelFactory;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;
import org.pnml.tools.epnk.pnmlcoremodel.serialisation.PNMLFactory;

import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;




import java.net.URL;

import org.junit.Test;
import org.junit.rules.Verifier;
import org.k1s.orn.ORNVerifier;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import orn.Pragmatics;
import orn.impl.OrnFactoryImpl;

//import de.derivo.sparqldlapi.Query;
//import de.derivo.sparqldlapi.QueryEngine;
//import de.derivo.sparqldlapi.QueryResult;

import static org.junit.Assert.*;

class ORNVerifierTest {

	def unparsable = """
	dfgfdgd
	ffdsfsdfsd-fsdfdsf-sdfsdfsddfsFSD=FSas
	"""
	
	def parsable = """
	Ontology: <http://k1s.org/test>
	"""
	
	
//	String logoOntology = """
//	<rdf:RDF
//    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
//    xmlns:owl="http://www.w3.org/2002/07/owl#"
//    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
//  xml:base="http://k1s.org/OntologyReastrictedNets/logo">
//  <owl:Ontology rdf:about="http://k1s.org/OntologyReastrictedNets/logo">
//	<owl:imports rdf:resource="http://t.k1s.org/OntologyReastrictedNets/basic.owl" />
//  </owl:Ontology>
//  <owl:Class rdf:ID="fd">
//	<rdfs:subClassOf rdf:resource="http://k1s.org/OntologyReastrictedNets/basic#Pragmatic"/>
//  </owl:Class>
//  </rdf:RDF>
//"""
//	
//String mch = """
//Prefix: : <http://k1s.org/OntologyReastrictedNets/logo>
//Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
//Prefix: owl: <http://www.w3.org/2002/07/owl#>
//Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>
//
//Ontology: <http://k1s.org/OntologyReastrictedNets/logo>
//
//Import: <http://t.k1s.org/OntologyReastrictedNets/basic.owl>
//
//Class: fd
//	SubClassOf: <http://k1s.org/OntologyReastrictedNets/basic#Pragmatic>
//
//
//"""

	String logoOntologyFunctional = """
 Prefix(:=<http://org.k1s/orn/logo/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/logo/>
   Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
   Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
	Declaration( Class( :fd ) )

	Declaration( Class( :bk ) )
	
	Declaration( Class( :rt ) )
	
	Declaration( Class( :lt ) )
	
	Declaration( Class( :pu ) )
	
	Declaration( Class( :pd ) )
	
	Declaration( Class( :repeat ) )
	
	Declaration( ObjectProperty( :belongsTo ) )
	
	
	
	
	SubClassOf( :fd basic:Pragmatic )
	SubClassOf( :fd ObjectAllValuesFrom( :belongsTo cpn:Transition ) ) 
	
	SubClassOf( :bk basic:Pragmatic )
	SubClassOf( :bk ObjectAllValuesFrom( :belongsTo cpn:Transition ) ) 
	
	SubClassOf( :rt basic:Pragmatic )
	SubClassOf( :rt ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 
	
	SubClassOf( :lt basic:Pragmatic )
	SubClassOf( :lt ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :pu basic:Pragmatic )
	SubClassOf( :pu ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :pd basic:Pragmatic )
	SubClassOf( :pd ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :repeat basic:Pragmatic )
	SubClassOf( :repeat ObjectAllValuesFrom( :belongsTo cpn:Page ) )
	
	
	DisjointClasses( cpn:Place cpn:Transition cpn:Page)
	   
	DisjointClasses( :fd :bk :rt :lt :pu :pd :repeat)
)	
"""
	
//	String logoOntology = """
//Prefix: : <http://org.k1s/orn/logo#>
//Prefix: basic: <http://t.k1s.org/OntologyReastrictedNets/basic#>
//Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>
//
//	
//
//Ontology: <http://org.k1s/orn/logo> 
//	Import: <http://t.k1s.org/OntologyReastrictedNets/basic>
//	
//	Class: fd
//		SubClassOf: basic:Dill
//		
//"""	
//Class: bk
//	Annotations: rdfs:comment "Back"
//	SubClassOf: basic:Pragmatic
//	
//	
//Class: rt
//	Annotations: rdfs:comment "Right turn"
//	SubClassOf: Pragmatic
//	
//	
//Class: lt
//	Annotations: rdfs:comment "Left turn"
//	SubClassOf: Pragmatic
//	
//	
//Class: pu
//	Annotations: rdfs:comment "Pen up"
//	SubClassOf: Pragmatic
//
//Class: pd
//	Annotations: rdfs:comment "Pen down"
//	SubClassOf: Pragmatic
//	
//Class: repeat
//	SubClassOf: Pragmatic
//"""


//		@Test
//	void testNotParsable(){
//		assertFalse(ORNVerifier.isValid(unparsable))
//	}
//	
//	@Test
//	void testParsable(){
//		assertTrue(ORNVerifier.isValid(parsable))
//	}
//	
//	@Test
//	void testTemplate(){
//		assertTrue(ORNVerifier.isValid(ORNConstants.OWL_TEMPLATE))
//	}
//	
	@Test
	void testVerifyLogo(){
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		
//		//OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new URL("http://t.k1s.org/OntologyReastrictedNets/basic.owl").openStream());
//		
//		//println ontology.axioms
//		def source = new StringDocumentSource(logoOntologyFunctional)
//		def logo = manager.loadOntologyFromOntologyDocument(source);
//
//		println logo.axioms
//		println logo.imports
		assertTrue(ORNVerifier.isValid(logoOntologyFunctional))
	}
	
	String logoNetOntologyFunctional = """
	Prefix(:=<http://org.k1s/orn/logoNet/>)
	Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
	Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
	Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
	Prefix(cpn:=<http://hib.no/SADLTest/cpn/>)
	Prefix(logo:=<http://org.k1s/orn/logo/>)
	Ontology(<http://org.k1s/orn/logoNet/>
	  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
	  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
	  
	   Declaration( NamedIndividual( :fd1 ) )
   
	   ClassAssertion( logo:fd :fd1 )
	   
	   Declaration( NamedIndividual( :bk1 ) )
   
	   ClassAssertion( logo:bk :bk1 )
	   
	   Declaration( NamedIndividual( :trans1 ) )
   
	   
	   ClassAssertion( cpn:Transition :trans1)

	   Declaration( NamedIndividual( :place1 ) )
   
	   ClassAssertion( cpn:Place :place1)
	
			
	   
	   DisjointClasses( cpn:Place cpn:Transition)
	   
	   DisjointClasses( logo:fd logo:bk)
	   
	   
	   
	   
	   SubClassOf( logo:fd ObjectAllValuesFrom( logo:belongsTo cpn:Transition ) )
	   
	   ObjectPropertyAssertion( logo:belongsTo :fd1 :place1 )
	   
	   
	   
	   
   )
   """
	
	@Test
	void testInvalidNet(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			
		//manager.loadOntologyFromOntologyDocument(new StringDocumentSource(logoOntologyFunctional))
		OWLOntology logoNet = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(logoNetOntologyFunctional))
		
		//OWLReasoner reasoner = new StructuralReasonerFactory().createReasoner(logoNet)
		
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
			reasoner.setOntology(ontology);
//		
//		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
//		reasoner.setOntology(logoNet)
//		
		
		
		//reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS,InferenceType.OBJECT_PROPERTY_ASSERTIONS);
		
		
//		QueryEngine engine = QueryEngine.create(manager, reasoner, true);
//		
//		Query q = Query.create("SELECT * WHERE {Type(?x, <http://hib.no/SADLTest/cpn#Place>)}")
//		
//		QueryResult res = engine.execute( q)
//		
//		println res
		println reasoner.isConsistent()
		//println reasoner.unsatisfiableClasses
		assertFalse(reasoner.isConsistent())
		
	}
	
	@Test
	void testPetiAugumentedNet(){
		ORNVerifier verifier = new ORNVerifier()
		PetriNet pn = getSimpleLogoPetriNet()
		String ont = verifier.pn2FunctionalSyntax(pn, logoOntologyFunctional)
		
		assertTrue(verifier.isValid(ont))
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		

		OWLOntology logoNet = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont))
	

	
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
			reasoner.setOntology(ontology);
		assertTrue(reasoner.isConsistent())
		
	}
	
	
	
	
	
	
	def getSimpleLogoPetriNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage()
		
		
		Transition trans = OrnFactoryImpl.eINSTANCE.createTransition()
		trans.setName getName("trans1")
		addPrag("fd", "10", trans)
		page.getObject().add(trans)
		
		Place place = OrnFactoryImpl.eINSTANCE.createPlace()
		place.setName getName("place1")
		addPrag("pd", "", place)
		page.getObject().add(place)
		
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc.setName getName("arc1")
		arc.setSource place
		arc.setTarget trans
		page.getObject().add(arc)
		
		
		pn.getPage().add(page)
		
		return pn
	}
	def addPrag(String name, String params, node){
		Pragmatics prag = OrnFactoryImpl.eINSTANCE.createPragmatics();
		prag.setText("${name}(${params})")
		prag.setStructure(prag.parse(prag.getText()))
		node.getPragmatics().add(prag);
		
	}
	def getPage(){
		Page p = PnmlcoremodelFactory.eINSTANCE.createPage()
		p.setName(getName("Testpage"))
		return p
	}
	
	
	def getName(text){
		Name n =  PnmlcoremodelFactory.eINSTANCE.createName()
		n.setText text
		return n
	}
	

	@Test
	void testRealInvalidNet(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			
		
		OWLOntology logoNet = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(realLogoOnt))
		
		//OWLReasoner reasoner = new StructuralReasonerFactory().createReasoner(logoNet)
		
		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager);
			reasoner.setOntology(ontology);
		
//
//		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
//		reasoner.setOntology(logoNet)
//
		
		
		//reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS,InferenceType.OBJECT_PROPERTY_ASSERTIONS);
		
		
//		QueryEngine engine = QueryEngine.create(manager, reasoner, true);
//
//		Query q = Query.create("SELECT * WHERE {Type(?x, <http://hib.no/SADLTest/cpn#Place>)}")
//
//		QueryResult res = engine.execute( q)
//
//		println res
		println reasoner.isConsistent()
		//println reasoner.unsatisfiableClasses
		assertFalse(reasoner.isConsistent())
		
	}

		
	def realLogoOnt ="""
Prefix(:=<http://org.k1s/orn/logo/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/logo/>
   Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
   Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
	Declaration( Class( :fd ) )

	Declaration( Class( :bk ) )
	
	Declaration( Class( :rt ) )
	
	Declaration( Class( :lt ) )
	
	Declaration( Class( :pu ) )
	
	Declaration( Class( :pd ) )
	
	Declaration( Class( :repeat ) )
	
	Declaration( ObjectProperty( :belongsTo ) )
	
	
	
	
	SubClassOf( :fd basic:Pragmatic )
	SubClassOf( :fd ObjectAllValuesFrom( :belongsTo cpn:Transition ) ) 
	
	SubClassOf( :bk basic:Pragmatic )
	SubClassOf( :bk ObjectAllValuesFrom( :belongsTo cpn:Transition ) ) 
	
	SubClassOf( :rt basic:Pragmatic )
	SubClassOf( :rt ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 
	
	SubClassOf( :lt basic:Pragmatic )
	SubClassOf( :lt ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :pu basic:Pragmatic )
	SubClassOf( :pu ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :pd basic:Pragmatic )
	SubClassOf( :pd ObjectAllValuesFrom( :belongsTo cpn:Place ) )
	
	SubClassOf( :repeat basic:Pragmatic )
	SubClassOf( :repeat ObjectAllValuesFrom( :belongsTo cpn:Page ) )
	
	
	DisjointClasses( cpn:Place cpn:Transition cpn:Page)
	   
	DisjointClasses( :fd :bk :rt :lt :pu :pd :repeat)
Declaration( NamedIndividual( :dill ) )
ClassAssertion( cpn:Page :dill )


Declaration( NamedIndividual( :Place_1674465449))
ClassAssertion( cpn:Place :Place_1674465449) 


Declaration( NamedIndividual( :pd_1783559459))
ClassAssertion( :pd :pd_1783559459  ) 
ObjectPropertyAssertion( :belongsTo :pd_1783559459 :Place_1674465449 )


Declaration( NamedIndividual( :Transition_206364542))
ClassAssertion( cpn:Transition :Transition_206364542) 


Declaration( NamedIndividual( :pu_1707082587))
ClassAssertion( :pu :pu_1707082587  ) 
ObjectPropertyAssertion( :belongsTo :pu_1707082587 :Transition_206364542 )


Declaration( NamedIndividual( :Place_871572413))
ClassAssertion( cpn:Place :Place_871572413) 


Declaration( NamedIndividual( :pu_555515789))
ClassAssertion( :pu :pu_555515789  ) 
ObjectPropertyAssertion( :belongsTo :pu_555515789 :Place_871572413 )


Declaration( NamedIndividual( :Transition_531589104))
ClassAssertion( cpn:Transition :Transition_531589104) 


Declaration( NamedIndividual( :fd_1571470755))
ClassAssertion( :fd :fd_1571470755  ) 
ObjectPropertyAssertion( :belongsTo :fd_1571470755 :Transition_531589104 )


Declaration( NamedIndividual( :Place_2021141958))
ClassAssertion( cpn:Place :Place_2021141958) 


Declaration( NamedIndividual( :pd_1442452214))
ClassAssertion( :pd :pd_1442452214  ) 
ObjectPropertyAssertion( :belongsTo :pd_1442452214 :Place_2021141958 )


Declaration( NamedIndividual( :Transition_1457428868))
ClassAssertion( cpn:Transition :Transition_1457428868) 


Declaration( NamedIndividual( :fd_351469715))
ClassAssertion( :fd :fd_351469715  ) 
ObjectPropertyAssertion( :belongsTo :fd_351469715 :Transition_1457428868 )


Declaration( NamedIndividual( :Arc_819227541))
ClassAssertion( cpn:Arc :Arc_819227541) 
ObjectPropertyAssertion( :source :Arc_819227541 :Place_1674465449 )
ObjectPropertyAssertion( :target :Arc_819227541 :Transition_206364542 )


Declaration( NamedIndividual( :Arc_1239620670))
ClassAssertion( cpn:Arc :Arc_1239620670) 
ObjectPropertyAssertion( :source :Arc_1239620670 :Transition_206364542 )
ObjectPropertyAssertion( :target :Arc_1239620670 :Place_871572413 )


Declaration( NamedIndividual( :Arc_1565741143))
ClassAssertion( cpn:Arc :Arc_1565741143) 
ObjectPropertyAssertion( :source :Arc_1565741143 :Place_871572413 )
ObjectPropertyAssertion( :target :Arc_1565741143 :Transition_531589104 )


Declaration( NamedIndividual( :Arc_146780063))
ClassAssertion( cpn:Arc :Arc_146780063) 
ObjectPropertyAssertion( :source :Arc_146780063 :Transition_531589104 )
ObjectPropertyAssertion( :target :Arc_146780063 :Place_2021141958 )


Declaration( NamedIndividual( :Arc_1621292085))
ClassAssertion( cpn:Arc :Arc_1621292085) 
ObjectPropertyAssertion( :source :Arc_1621292085 :Place_2021141958 )
ObjectPropertyAssertion( :target :Arc_1621292085 :Transition_1457428868 )


)	
""" 
	
}
