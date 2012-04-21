package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ORNTestUtils;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
class ATTTests {

	
//	
//	@Test
//	void testOnePrincipalOneExtenalNoExtra(){
//		def pn = ORNTestUtils.createOnePrincipalOneExtenalNoExtraNet()
//		def bindings = ORNTestUtils.createGroovyBindings()
//		fail("nyi")
////		def att = new OntologyDrivenGenerator().makeATT(pn, bindings)
////		
////		assertEquals( "ROOT", att.pragmaticName)
////		assertEquals(1, att.children.size())
////		
////		att = att.children[0]
////		
////		assertEquals( "Principal", att.pragmaticName)
////		assertEquals(1, att.children.size())
////		
////		att = att.children[0]
////		
////		assertEquals("External", att.pragmaticName)
////		assertEquals(0, att.children.size())
//	}
//	
//	
//	@Test
//	void testAttBuilder(){
//		fail("nyi")
//	}
	
	
	
	@Test
	void testAttBuilderBuildPrincipal(){
		Transition send = getSendTransition()
		def external = getExternalTransition()
		def place = getPlace()
	
		def arcE2P = ORNTestUtils.getArc(external, place)
		def arcP2E = ORNTestUtils.getArc(place, send)
		
		Page page = ORNTestUtils.getPage()
		page.object.add send
		page.object.add external
		ORNTestUtils.addPrag "Principal", "", page
		
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree.attForNode(page, bindings)
		
		assertThat att, is(not(null))
		
		
		assertThat att.getCorrespondingNetElement(), is(page)
		assertThat att.getTemplateBinding(), is(bindings.prag2Binding['Principal'])
		
		assertThat att.children.size(), is(1)
		
		def extAtt = att.children[0]
		
		assertThat extAtt.getCorrespondingNetElement(), is(external)
		
		
		
		assertThat extAtt.children.size(), is(1)
		assertThat extAtt.children[0].correspondingNetElement, is(send)
		
		
	}
	@Test
	void testAttBuilderBuildExternal(){
		Transition send = getSendTransition()
		def external = getExternalTransition()
		def place = getPlace()
	
		def arcE2P = ORNTestUtils.getArc(external, place)
		def arcP2E = ORNTestUtils.getArc(place, send)
		
		Page page = ORNTestUtils.getPage()
		
		page.object.add send
		page.object.add external
		
		def bindings = ORNTestUtils.createGroovyBindings()
		def binding = bindings.prag2Binding['External']
		
		//AbstractTemplateTree  att = new AbstractTemplateTree()
		def att = AbstractTemplateTree.attForNode(external, bindings, page)
		
		assertThat att, is(not(null))
		
		
		assertThat att.getCorrespondingNetElement(), is(external)
		assertThat att.getTemplateBinding(), is(binding)
		
		
		assertThat att.children.size(), is(1)
		assertThat att.children[0].correspondingNetElement, is(send)
	}
	
	@Test
	void testFindNextnode(){
		Transition send = getSendTransition()
		def external = getExternalTransition()
		def place = getPlace()
	
		def arcE2P = ORNTestUtils.getArc(external, place)
		def arcP2E = ORNTestUtils.getArc(place, send)
		
		Page page = ORNTestUtils.getPage()
		
		page.object.add send
		page.object.add external
		
		def nextNode = AbstractTemplateTree.findNextNode(external, page)
	
		assertThat nextNode, is(place)
		
		nextNode = AbstractTemplateTree.findNextNode(place, page)
		assertThat nextNode, is(send)
	}
	
	@Test
	void testAttBuilderBuildSend(){
		def transition = getSendTransition()
		def bindings = ORNTestUtils.createGroovyBindings()
		def binding = bindings.prag2Binding['Send']
		AbstractTemplateTree  att = new AbstractTemplateTree()
		att = att.attForNode(transition, bindings)
		
		assertThat att, is(not(null))
		assertThat att.children.size(), is(0)
		
		assertThat att.getCorrespondingNetElement(), is(transition)
		assertThat att.getTemplateBinding(), is(binding)
	}
	
	
	
	@Test
	void testATTForSimpleProtocol(){
		def pn = ORNTestUtils.getSimpleProtocolPN()
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		
		assertThat att, is(not(null))
		assertThat att.children.size(), is(2)
	}
	
	def getSendTransition(){
		def transition = ORNTestUtils.getTransition("sender")
		ORNTestUtils.addPrag "Send", "", transition
		return transition
	}
	
	def getExternalTransition(){
		def transition = ORNTestUtils.getTransition("ext")
		ORNTestUtils.addPrag "External", "", transition
		return transition
	}
	
	def getPlace(){
		def place = ORNTestUtils.getPlace("place")
		return place
	}
	
	
}
