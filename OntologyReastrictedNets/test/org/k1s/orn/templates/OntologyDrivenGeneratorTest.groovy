package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ORNTestUtils;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
class OntologyDrivenGeneratorTest {
	
	
	
	
	@Test
	void testGenerate(){
		def pn = getPrincipalExternalNet()
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		assertTrue file.size() > 0
		
		assertTrue file.contains("class")
		assertTrue file.contains("def External")
		assertTrue file.contains("send")
		
		println file
	}
	
	@Test
	void testSimpleProtocol(){
		def pn = ORNTestUtils.getSimpleProtocolPN()
		def bindings = ORNTestUtils.createGroovyBindings()
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		def output = []
		def client = new ProtcolRunner().run(file[0], "new Client()")
		
		def server = new ProtcolRunner().run(file[1], "new Server()")
		
		//server.metaClass.println { String str -> output << str }
		
		
		def buf = new ByteArrayOutputStream()
		def newOut = new PrintStream(buf)
		def saveOut = System.out
		
		
		System.out = newOut
		
		
		def t = Thread.start { server.start(31337) }
		
		client.start("hallo",[port: 31337, host:'localhost'])
		
		
		Thread.sleep 1000
		
		
		System.out = saveOut
		println buf.toString().trim()
		assertEquals( "hallo", buf.toString().trim() )
	}
	
	
	def getPrincipalExternalNet(){
		def pn = ORNTestUtils.getPetriNet()
		
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
		
		
		
		pn.page << page
		
		
		
		return pn
				
		
	}
	
}
