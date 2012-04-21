package org.k1s.owleditors.mch;

public class MCHConstants {
	public static String BASIC_IRI = "http://k1s.org/OntologyReastrictedNets/basic.owl";
	public static String CPN_IRI = "http://hib.no/SADLTest/cpn.owl";
	
	public static String OWL_TEMPLATE = "Prefix: : <http://org.k1s/orn/customontology>\n"+
"Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
"Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"+
"Prefix: basic: <http://k1s.org/OntologyReastrictedNets/basic>\n"+
"Prefix: cpn: <http://hib.no/SADLTest/cpn>\n"+
	"\n"+
"Ontology: <http://org.k1s/orn/customontology>\n"+
	"\n"+
"Import: <http://k1s.org/OntologyReastrictedNets/basic.owl>\n"+
	"\n"+
"Import: <http://hib.no/SADLTest/cpn.owl>\n"+
	"\n";


}
