package org.k1s.orn.templates;
import groovy.lang.GroovyShell;

import org.junit.Test;
import org.k1s.orn.ORNVerifier;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class BindingsDSLTests {

	
	@Test
	void testgroovy(){
		def bindings =  new File("./plattforms/groovy.bindings").text
		def sh = new GroovyShell()
		def closure = sh.evaluate("{ -> \n$bindings }")

				
		def fowl = BindingsDSL.makeOWL(closure)
		
		assertTrue fowl.contains("Declaration( NamedIndividual( :ClassTemplate))")
		assertTrue fowl.contains("DataPropertyAssertion(:dependencies :Recieve \"channels\"^^xsd:string)")
	}
	
	@Test
	void testgroovyPlattformIsValid(){
		def bindings =  new File("./plattforms/groovy.bindings").text
		def sh = new GroovyShell()
		def closure = sh.evaluate("{ -> \n$bindings }")
		
		def fowl = BindingsDSL.makeOWL(closure)
		
//		println fowl 
		ORNVerifier ornVerifier = new ORNVerifier()
		
		assertTrue(ornVerifier.isConsistent(fowl))
		
	}
	
	@Test
	void testgroovyForBindings(){
		def bindings =  new File("./plattforms/groovy.bindings").text
		def sh = new GroovyShell()
		def closure = sh.evaluate("{ -> \n$bindings }")

				
		def binding = BindingsDSL.makeBindings(closure)
		
		assertThat(binding, instanceOf(Bindings.class))
	}
	
	
	@Test
	void testgroovyForBindingsContent(){
		def bindings =  new File("./plattforms/groovy.bindings").text
		def sh = new GroovyShell()
		def closure = sh.evaluate("{ -> \n$bindings }")

		def binding = BindingsDSL.makeBindings(closure)
		
		assertThat( binding.bindings['ClassTemplate'], is(not(null)))
		assertThat( binding.bindings['ClassTemplate'].pragmatic, is("Principal"))
		assertThat( binding.bindings['ClassTemplate'].template, is("mainClass.tmpl"))
		
		
		
	}
}
