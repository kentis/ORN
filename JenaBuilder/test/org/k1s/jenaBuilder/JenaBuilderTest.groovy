package org.k1s.jenaBuilder;

import junit.framework.Test;
import groovy.util.GroovyTestCase;

class JenaBuilderTest extends GroovyTestCase {

	
	void testSimple(){
		def builder =  new JenaBuilder()
		
		def a = builder.model(uri: "http://t.k1s.org/orn"){
			klass(uri: "hei") 
		}
		
		println a
	}
	
}
