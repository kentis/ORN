package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.ORNUtils;
import org.k1s.orn.templates.ogdVisitors.ODGVisitor;
import org.k1s.orn.templates.ogdVisitors.ParameterVisitor;
import org.k1s.orn.templates.ogdVisitors.TemplateVisitor;
import org.k1s.orn.test.ORNTestUtils;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
class ODGVisitorTest {
	
	@Test
	void testFlatten(){
		
		def att = getPrincipalExternalNetAtt()
		
		def flat = ODGVisitor.flattenAtt(att, [])
		
		assertThat flat.size(), is(3)
		
		println flat
		
	}
	
	@Test
	void testParameterVisitor(){
		Transition external = ORNTestUtils.getTransition("External")
		ORNTestUtils.addPrag "External", "", external
		
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = AbstractTemplateTree.attForNode(external, bindings)
		
		assertThat att.parameters, is(null)

		
		def visitor = new ParameterVisitor()
		visitor.visitElement att
		
		assertThat att.parameters, is(not(null))
		assertThat att.parameters['name'], is(not(null))
		assertThat att.parameters['name'], is("External")
	}
	
	
	@Test
	void testTemplateVisitor(){
		Transition external = ORNTestUtils.getTransition("External")
		ORNTestUtils.addPrag "External", "", external
		
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = AbstractTemplateTree.attForNode(external, bindings)
		
		assertThat att.templateText, is(null)

		
		def paramVisitor = new ParameterVisitor()
		paramVisitor.visitElement att
		
		def visitor = new TemplateVisitor()
		visitor.visitElement att
		
		assertThat att.templateText, is(not(null))
		
		println att.templateText
	}
	
	
	
	def getPrincipalExternalNetAtt(){
		Transition send = ORNTestUtils.getTransition("Send")
		ORNTestUtils.addPrag "Send", "", send
		def external = ORNTestUtils.getTransition("External")
		ORNTestUtils.addPrag "External", "", external
		def place = ORNTestUtils.getPlace()
	
		def arcE2P = ORNTestUtils.getArc(external, place)
		def arcP2E = ORNTestUtils.getArc(place, send)
		
		Page page = ORNTestUtils.getPage()
		page.object.add send
		page.object.add external
		ORNTestUtils.addPrag "Principal", "", page
		
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree.attForNode(page, bindings)
		
		
		
		return att
				
		
	}
	
	
}
