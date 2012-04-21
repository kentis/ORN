package org.k1s.jenaBuilder

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import groovy.util.BuilderSupport;

class JenaBuilder extends BuilderSupport {

	OntModel model;
	
	def createNode(Object name){
		createNode(name, null, null)
	}
	
	def createNode(Object name, Map attributes){
		createNode(name, attributes, null)
	}
	
	
	
	def createNode(Object name, Object value){
		createNode(name, null, value)
	}
	
	
	def createNode(Object name, Map attributes, Object value){
		def node
		switch(name){
			case "model":
				node = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
				node.createOntology attributes.uri
				this.model = node
			break
			
			case "klass":
				OntClass klass = model.createClass(attributes.uri)
				node=klass
			break
					
			
		}
		return node
	}
	
	void setParent(parent, child){
		
		println "setParent \"$parent\" \"$child\""
	}
	
	
	void nodeCompleted(parent, node) {
		println "nodeCompleted \"$parent\" \"$node\""
	}
}
