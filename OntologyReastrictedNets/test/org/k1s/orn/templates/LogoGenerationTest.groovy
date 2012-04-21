package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ORNTestUtils;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;

import static org.junit.Assert.*;

class LogoGenerationTest {

	@Test 
	void testFindExternal(){
		PetriNet pn = ORNTestUtils.getSimpleLogoPetriNet()
		
		def topPage = pn.getPage().get(0)
		
		def external = new Generator().findExternal(topPage)
		
		assertNotNull(external)
		
		println 		external.getPragmatics().get(0).getText()
		assertTrue("External()".equals(external.getPragmatics().get(0).getText()))
		
	}
	
	@Test
	void testGenerateLogoNet(){
		PetriNet pn = ORNTestUtils.getSimpleLogoPetriNet()
		
		Generator gen = new Generator()
		gen.templateManager = new TemplateManager()
		gen.templateManager.templateFinder = new LogoTemplateFinder()
		String res = gen.generate(pn, "null")
		
		assertEquals("pd \nfd 10\n", res)
	}
	
	
	@Test
	void testGenerateLogoNetWithRepeat(){
		PetriNet pn = ORNTestUtils.getLogoPetriNetWithRepeat()
		
		Generator gen = new Generator()
		gen.templateManager = new TemplateManager()
		gen.templateManager.templateFinder = new LogoTemplateFinder()
		String res = gen.generate(pn, "null")
		
		assertEquals("repeat 2 (pd \nfd 10\n)", res)
	}
	
}

class LogoTemplateFinder {
	
	def propertyMissing(String name) { "${name} \$params\n".toString() }
	
	def repeat = "repeat \$params (%body%)"
}
