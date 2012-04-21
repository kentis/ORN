package org.k1s.owleditors.fowl;

public class FunctionalConstants {
	public static String BASIC_IRI = "http://k1s.org/OntologyReastrictedNets/basic.owl";
	public static String CPN_IRI = "http://hib.no/SADLTest/cpn.owl";
	
	public static String OWL_TEMPLATE = " Prefix(:=<http://org.k1s/orn/ext/>)\n"+
 "Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)\n"+
 "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"+
 "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"+
 "Prefix(cpn:=<http://hib.no/SADLTest/cpn>)\n"+
	" \n"+
 "Ontology(<http://org.k1s/orn/ext/>\n"+
  "Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )\n"+
  "Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )\n"+
	 " \n"+
  "DisjointClasses(cpn:Place cpn:Transition cpn:Arc cpn:Page basic:Pragmatic)\n"+ 
" \n)\n";


}
