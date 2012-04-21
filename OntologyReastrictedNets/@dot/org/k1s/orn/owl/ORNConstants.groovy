package org.k1s.orn.owl

class ORNConstants {
	
	public static String BASIC_IRI = "http://t.k1s.org/OntologyReastrictedNets/basic.owl";
	public static String CPN_IRI = "http://t.k1s.org/OntologyReastrictedNets/cpn.owl";
	
	public static String OWL_TEMPLATE = 
"""Prefix: : <http://org.k1s/orn/customontology>
Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>
Prefix: owl: <http://www.w3.org/2002/07/owl#>
Prefix: basic: <http://k1s.org/OntologyReastrictedNets/basic>
Prefix: cpn: <http://hib.no/SADLTest/cpn>

Ontology: <http://org.k1s/orn/customontology>

Import: <http://k1s.org/OntologyReastrictedNets/basic.owl>

Import: <http://hib.no/SADLTest/cpn.owl>


	"""
}
