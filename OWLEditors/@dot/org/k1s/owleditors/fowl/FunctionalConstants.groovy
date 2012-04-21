package org.k1s.owleditors.fowl



class FunctionalConstants {
	
	public static String BASIC_IRI = "http://k1s.org/OntologyReastrictedNets/basic.owl";
	public static String CPN_IRI = "http://hib.no/SADLTest/cpn.owl";
	
	public static String OWL_TEMPLATE = 
""" Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)

 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )

  DisjointClasses(cpn:Place cpn:Transition cpn:Arc cpn:Page basic:Pragmatic) 

)
"""
}
