package org.k1s.orn.owl;




import org.semanticweb.owlapi.reasoner.InferenceType;
import java.util.Properties;

import org.junit.Test;
import org.k1s.orn.ORNUtils;
import org.k1s.orn.ORNVerifier;
import org.mindswap.pellet.PelletOptions;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode;

//import eu.trowl.owl.api3.ReasonerFactory;

import static org.junit.Assert.*;

class ExternalIdTests {

	def validOnt2 = """
	Prefix(:=<http://org.k1s/orn/ext/>)
	Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
	Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
	Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
	Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
	
	Ontology(<http://org.k1s/orn/ext/>
	 Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
	 Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
	  
	 Declaration( Class( :id ) )
   
	 Declaration( Class( :external ) )
	   
	 Declaration( ObjectProperty( :belongsTo ) )
	   
	 SubClassOf( :id basic:Pragmatic )
	 SubClassOf( :id ObjectAllValuesFrom( :belongsTo cpn:Place ) )
   
	 SubClassOf( :external basic:Pragmatic )
	 SubClassOf( :external ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) )
	   
	 InverseObjectProperties( :out cpn:source )
	 InverseObjectProperties( :in cpn:target )
	 InverseObjectProperties( :hasPragmatic :belongsTo )
	 
	 DisjointClasses( :id :external cpn:Place cpn:Transition cpn:Page cpn:Arc)
	 DisjointClasses( :ArcFromId :id :external cpn:Place cpn:Transition cpn:Page)
	 
   
	 
	 SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
			 cpn:Transition
			 ObjectAllValuesFrom( :in :ArcFromId)
		   ObjectExactCardinality( 1 :in)
			 )
		 )
	 
	 SubClassOf( :ArcFromId ObjectIntersectionOf(
			 cpn:Arc
			 ObjectAllValuesFrom( cpn:source :IdPlace)
			 ObjectExactCardinality( 1 cpn:source )
		 )
	 )
   
   
	SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
	 
	 SubClassOf( :IdPlace ObjectIntersectionOf(
	   cpn:Place
		 ObjectAllValuesFrom( :hasPragmatic :id)
		 ObjectExactCardinality( 1 :hasPragmatic :id)
	 )
	 )
   
   
	 
	 Declaration( NamedIndividual( :idPlace ) )
	 ClassAssertion( cpn:Place :idPlace )
	 
	 Declaration( NamedIndividual( :id_prag))
	 ClassAssertion( :id :id_prag  )
	 ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )
	 ObjectPropertyAssertion( :hasPragmatic :idPlace :id_prag)
   
   
	 Declaration( NamedIndividual( :extTransition ) )
	 ClassAssertion( cpn:Transition :extTransition )
	 
	 Declaration( NamedIndividual( :ext_prag))
	 ClassAssertion( :external :ext_prag  )
	 ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )
	 ObjectPropertyAssertion( :hasPragmatic :extTransition :ext_prag)
	 
	 
	 Declaration( NamedIndividual( :theArc))
	 ClassAssertion( cpn:Arc :theArc)
	 ObjectPropertyAssertion( :source :theArc :idPlace )
	 ObjectPropertyAssertion( :target :theArc :extTransition )
	 
	 ObjectPropertyAssertion( :out :idPlace :theArc)
	 ObjectPropertyAssertion( :in :extTransition :theArc)
   
   )
   """
	
	def validOnt = """
 Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  
  SubClassOf( :Id basic:Pragmatic )
  SubClassOf( :Id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :External basic:Pragmatic )
  SubClassOf( :External ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 


  SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
    SubClassOf( :IdPlace ObjectIntersectionOf(
      cpn:Place
  	  ObjectAllValuesFrom( :hasPragmatic :Id) 
  	  ObjectExactCardinality( 1 :hasPragmatic )
    )
  )


SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )
 
 SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		ObjectExactCardinality( 1 :in)
  		)
  	)


  Declaration( NamedIndividual( :idPlace ) )
  ClassAssertion( :IdPlace :idPlace )

  Declaration( NamedIndividual( :id_prag))
  ClassAssertion( :Id :id_prag  ) 
  ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )
  ObjectPropertyAssertion( :hasPragmatic :idPlace :id_prag)

  Declaration( NamedIndividual( :extTransition ) )
  ClassAssertion( cpn:Transition :extTransition )
  ClassAssertion( :TransitionConnectedToId :extTransition )
  
  Declaration( NamedIndividual( :ext_prag))
  ClassAssertion( :External :ext_prag  ) 
  ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )
  


  Declaration( NamedIndividual( :theArc))
  ClassAssertion( :ArcFromId :theArc)
  ClassAssertion( cpn:Arc :theArc) 
  ObjectPropertyAssertion( cpn:source :theArc :idPlace )
  ObjectPropertyAssertion( cpn:target :theArc :extTransition )
  ObjectPropertyAssertion( :out :idPlace :theArc)
  ObjectPropertyAssertion( :in :extTransition :theArc)
)
"""

def inValidOnt  = """
 Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  
  SubClassOf( :Id basic:Pragmatic )
  SubClassOf( :Id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :External basic:Pragmatic )
  SubClassOf( :External ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 


  SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
    SubClassOf( :IdPlace ObjectIntersectionOf(
      cpn:Place
  	  ObjectAllValuesFrom( :hasPragmatic :Id) 
  	  ObjectExactCardinality( 1 :hasPragmatic )
    )
  )


SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )
 
 SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		ObjectExactCardinality( 1 :in)
  		)
  	)


  Declaration( NamedIndividual( :idPlace ) )
  ClassAssertion( :IdPlace :idPlace )

  Declaration( NamedIndividual( :id_prag))
  ClassAssertion( :Id :id_prag  ) 
  ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )
  ObjectPropertyAssertion( :hasPragmatic :idPlace :id_prag)

  Declaration( NamedIndividual( :extTransition ) )
  ClassAssertion( cpn:Transition :extTransition )
  ClassAssertion( :TransitionConnectedToId :extTransition )
  
  Declaration( NamedIndividual( :ext_prag))
  ClassAssertion( :External :ext_prag  ) 
  ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )

)
"""

def inValidOnt4 = """
 Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  Declaration( Class( :id ) )

  Declaration( Class( :external ) )
	
  Declaration( ObjectProperty( :belongsTo ) )
	
  SubClassOf( :id basic:Pragmatic )
  SubClassOf( :id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :external basic:Pragmatic )
  SubClassOf( :external ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 
	
  InverseObjectProperties( :out cpn:source ) 
  InverseObjectProperties( :in cpn:target )
  InverseObjectProperties( :hasPragmatic :belongsTo )
  
  DisjointClasses( :id :external cpn:Place cpn:Transition cpn:Page cpn:Arc :noneClass)
  DisjointClasses( :ArcFromId :id :external cpn:Place cpn:Transition cpn:Page :noneClass)
  



  
  SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		ObjectExactCardinality( 1 :in)
  		)
  	)
  
  
  
  
  
  
  SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )


SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
  SubClassOf( :IdPlace ObjectIntersectionOf(
    cpn:Place
  	ObjectAllValuesFrom( :hasPragmatic :id) 
  	ObjectExactCardinality( 1 :hasPragmatic :id)
  )
  )


  
  Declaration( NamedIndividual( :idPlace ) )
  ClassAssertion( cpn:Place :idPlace )
  
  Declaration( NamedIndividual( :id_prag))
  ClassAssertion( :id :id_prag  ) 
  ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )



  Declaration( NamedIndividual( :extTransition ) )
  ClassAssertion( cpn:Transition :extTransition )
  ClassAssertion( :TransitionConnectedToId :extTransition )
  
  Declaration( NamedIndividual( :ext_prag))
  ClassAssertion( :external :ext_prag  ) 
  ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )
  
  
  
  
  
  
)
"""


def inValidOnt3 = """
  Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  Declaration( Class( :id ) )

  Declaration( Class( :notId ) )

  Declaration( Class( :external ) )
	
  Declaration( ObjectProperty( :belongsTo ) )
	
  SubClassOf( :id basic:Pragmatic )
  SubClassOf( :id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :notId basic:Pragmatic )
  SubClassOf( :notId ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 


  SubClassOf( :external basic:Pragmatic )
  SubClassOf( :external ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 
	
  InverseObjectProperties( :out cpn:source ) 
  InverseObjectProperties( :in cpn:target )
  InverseObjectProperties( :hasPragmatic :belongsTo )
  
  DisjointClasses( :notId :id :external cpn:Place cpn:Transition cpn:Page cpn:Arc)
  DisjointClasses( :notId :ArcFromId :id :external cpn:Place cpn:Transition cpn:Page)
  



  
  SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		ObjectExactCardinality( 1 :in) 	
  		)
  	)
  
  
  
  
  
  
  SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )


SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
  SubClassOf( :IdPlace ObjectIntersectionOf(
    cpn:Place
  	ObjectAllValuesFrom( :hasPragmatic :id) 
  	ObjectExactCardinality( 1 :hasPragmatic :id)
  )
  )


  
  Declaration( NamedIndividual( :idPlace ) )
  ClassAssertion( cpn:Place :idPlace )
  
  Declaration( NamedIndividual( :id_prag))
  ClassAssertion( :notId :id_prag  ) 
  ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )



  Declaration( NamedIndividual( :extTransition ) )
  ClassAssertion( cpn:Transition :extTransition )
  
  Declaration( NamedIndividual( :ext_prag))
  ClassAssertion( :external :ext_prag  ) 
  ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )
  
  
  
  Declaration( NamedIndividual( :theArc))
  ClassAssertion( cpn:Arc :theArc) 
  ObjectPropertyAssertion( :source :theArc :idPlace )
  ObjectPropertyAssertion( :target :theArc :extTransition )
  
  EquivalentClasses(owl:Thing
  	ObjectOneOf(:idPlace :id_prag  :extTransition :ext_prag :theArc )
  )
  
  DifferentIndividuals( :idPlace :id_prag  :extTransition :ext_prag :theArc ) 
)
"""


def inValidOnt2 = """
 Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  Declaration( Class( :id ) )

  Declaration( Class( :external ) )
	
  Declaration( ObjectProperty( :belongsTo ) )
	
  SubClassOf( :id basic:Pragmatic )
  SubClassOf( :id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :external basic:Pragmatic )
  SubClassOf( :external ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 
	
  InverseObjectProperties( :out cpn:source ) 
  InverseObjectProperties( :in cpn:target )
  InverseObjectProperties( :hasPragmatic :belongsTo )
  
  DisjointClasses( :id :external cpn:Place cpn:Transition cpn:Page cpn:Arc)
  DisjointClasses( :ArcFromId :id :external cpn:Place cpn:Transition cpn:Page)
  DisjointClasses( :ArcFromId :id :external cpn:Transition cpn:Page :IdPlace)



  
  SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		ObjectExactCardinality( 1 :in ) 	
  		)
  	)
  
  
  
  
  
  Declaration( Class( :ArcFromId ) )
  SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )

Declaration( Class( :IdPlace ) )
SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
  SubClassOf( :IdPlace ObjectIntersectionOf(
    cpn:Place
  	ObjectAllValuesFrom( :hasPragmatic :id) 
  	ObjectExactCardinality( 1 :hasPragmatic :id)
  )
  )


  
  Declaration( NamedIndividual( :idPlace ) )
  ClassAssertion( cpn:Place :idPlace )
  
  Declaration( NamedIndividual( :id_prag))
  ClassAssertion( :id :id_prag  ) 
  ObjectPropertyAssertion( :belongsTo :id_prag :idPlace )



  Declaration( NamedIndividual( :extTransition ) )
  ClassAssertion( cpn:Transition :extTransition )
  
  ObjectPropertyAssertion( :in :extTransition :id_prag)
  
  Declaration( NamedIndividual( :ext_prag))
  ClassAssertion( :external :ext_prag  ) 
  ObjectPropertyAssertion( :belongsTo :ext_prag :extTransition )
  
  
  
  
)
"""


def validFromNet = """

	Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  
  SubClassOf( :Id basic:Pragmatic )
  SubClassOf( :Id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :External basic:Pragmatic )
  SubClassOf( :External ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 


  SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
    SubClassOf( :IdPlace ObjectIntersectionOf(
      cpn:Place
  	  ObjectAllValuesFrom( :hasPragmatic :Id) 
  	  ObjectExactCardinality( 1 :hasPragmatic )
    )
  )


SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )
 
 SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		
  		)
  	)
  	



Declaration( NamedIndividual( :start))
ClassAssertion( cpn:Place :start) 


Declaration( NamedIndividual( :Id_1212695978))
ClassAssertion( :Id :Id_1212695978  ) 
ObjectPropertyAssertion( :belongsTo :Id_1212695978 :start )
ObjectPropertyAssertion( :hasPragmatic :start :Id_1212695978)

Declaration( NamedIndividual( :extern))
ClassAssertion( cpn:Transition :extern) 


Declaration( NamedIndividual( :External_674653555))
ClassAssertion( :External :External_674653555  ) 
ObjectPropertyAssertion( :belongsTo :External_674653555 :extern )

Declaration( NamedIndividual( :arc))
 
ClassAssertion( cpn:Arc :theArc)
ObjectPropertyAssertion( cpn:source :arc :start )
ObjectPropertyAssertion( cpn:target :arc :extern )
ObjectPropertyAssertion( :out  :start :arc )
ObjectPropertyAssertion( :in  :extern :arc)



)
"""

	@Test
	void testValid(){
		ORNVerifier verifier = new ORNVerifier();
		
		if(!verifier.isConsistent(validOnt)){
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(validOnt));
			
			//OWLReasoner reasoner = new Reasoner(ontology);
			
			com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
			ORNUtils.closeTheWorldForIndiviuals(ontology);
			reasoner.setOntology(ontology)
			
			
			if(!reasoner.isConsistent()){
				println "VALID IS INCONSISTENT"
				println reasoner.getInconsistentClasses()
			}else{
			println "VALID IS CONSISTENT"
			}
		}
		
		assertTrue verifier.isConsistent(validOnt)
	}
	
	@Test
	void testValidFromNet(){
		ORNVerifier verifier = new ORNVerifier();
		
		if(!verifier.isConsistent(validFromNet)){
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(validFromNet));
			
			//OWLReasoner reasoner = new Reasoner(ontology);
			
			com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
			ORNUtils.closeTheWorldForIndiviuals(ontology);
			reasoner.setOntology(ontology)
			
			
			if(!reasoner.isConsistent()){
				println "VALID IS INCONSISTENT"
				println reasoner.getInconsistentClasses()
			}else{
			println "VALID IS CONSISTENT"
			}
		}
		
		assertTrue verifier.isConsistent(validOnt)
	}
	
//	@Test
//	void queryInvalid(){
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(inValidOnt));
//		
//		OWLReasoner reasoner = new Reasoner(ontology);
//		reasoner.precomputeInferences reasoner.precomputableInferenceTypes.toArray(new InferenceType[0])
////		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
////		reasoner.setOntology(ontology)
////		
//		
//		if(!reasoner.isConsistent()){
//			println "INCONSISTENT"
//			//println reasoner.getInconsistentClasses()
//		}else{
//		println "CONSISTENT"
//		}
//		
//		//reasoner.precomputeInferences(new InferenceType[0])
//		System.out.println("\n\nClasses: ");
//		OWLClass transitionConnectedToIdClass = null;
//		for(OWLClass c : ontology.getClassesInSignature()){
//			System.out.println(c);
//			if("<http://org.k1s/orn/ext/TransitionConnectedToId>".equals(c.toString())){
//				transitionConnectedToIdClass = c
//			}
//			if("<http://org.k1s/orn/ext/ArcFromId>".equals(c.toString())){
//				
//				println "ARCFROMID: ${reasoner.getInstances(c, true).getNodes()}"
//			}
//		}
//		
//		NodeSet set = reasoner.getInstances(transitionConnectedToIdClass, true)
//		System.out.println(set.getNodes())
//		
//		OWLNamedIndividualNode extTransition = set.getNodes().toArray()[0]
//		OWLNamedIndividual extTransitionI = extTransition.getEntities().toArray()[0]
//		println extTransitionI.getReferencingAxioms(ontology)
//		
//		
//		println reasoner.isConsistent()
//	}
	
	@Test
	void testInvalid(){
		ORNVerifier verifier = new ORNVerifier();
		assertFalse verifier.isConsistent(inValidOnt)
	}
	
//	@Test
//	void testInvalidClosedPellet(){
//		
//		assertFalse isConsistentClosedPellet(inValidOnt)
//	}
	
//	def isConsistentTrowl(ont){
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
//	
//		
//		OWLReasonerFactory reasonerFactory = new ReasonerFactory();
//		OWLReasoner trowl = reasonerFactory.createReasoner(ontology)
//		
//		return trowl.isConsistent()
//		
//	}
//	
//	def isConsistentPellet(ont){
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
//		
//
//		
//		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
//		PelletOptions.setOptions Proper
//		reasoner.setOntology(ontology)
//
//		
//		if(!reasoner.isConsistent()){
//			println "INCONSISTENT"
//			//println reasoner.getInconsistentClasses()
//		}else{
//		println "CONSISTENT"
//		}
//		
//		return reasoner.isConsistent()
//	}
//	
//	def isConsistentClosedPellet(ont){
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ont));
//		
//
//		
//		com.clarkparsia.pellet.owlapiv3.Reasoner reasoner = new com.clarkparsia.pellet.owlapiv3.Reasoner(manager)
//		PelletOptions.USE_UNIQUE_NAME_ASSUMPTION = true
//		reasoner.setOntology(ontology)
//
//		
//		if(!reasoner.isConsistent()){
//			println "INCONSISTENT"
//			//println reasoner.getInconsistentClasses()
//		}else{
//		println "CONSISTENT"
//		}
//		
//		return reasoner.isConsistent()
//	}
}
