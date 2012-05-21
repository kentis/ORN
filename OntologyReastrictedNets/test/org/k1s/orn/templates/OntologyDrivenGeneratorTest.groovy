package org.k1s.orn.templates;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;
import org.k1s.orn.ORNImporter;
import org.k1s.orn.test.ORNTestUtils;
import org.k1s.orn.test.ePNKBuilder;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelPackage;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;
import org.pnml.tools.epnk.pnmlcoremodel.impl.PetriNetDocImpl;
import org.pnml.tools.epnk.pnmlcoremodel.serialisation.PNMLResource;
import org.pnml.tools.epnk.pnmlcoremodel.serialisation.PNMLResourceFactory;

import orn.OrnFactory;
import orn.OrnPackage;
import orn.impl.PragmaticsImpl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.matchers.JUnitMatchers.*;

class OntologyDrivenGeneratorTest {
	
	
	
	
//	@Test
//	void testGenerate(){
//		def pn = getPrincipalExternalNet()
//		def bindings = ORNTestUtils.createGroovyBindings()
//		
//		def file = new OntologyDrivenGenerator().generate( pn, bindings )
//		println file
//		assertThat file, is(not(null))
//		assertTrue file.size() > 0
//		
//		assertTrue file.contains("class")
//		assertTrue file.contains("def External")
//		assertTrue file.contains("send")
//		
//		println file
//	}
	
	@Test
	void testSimpleProtocol(){
		def pn = ORNTestUtils.getSimpleProtocolPN()
		def bindings = ORNTestUtils.createGroovyBindings()
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		def output = []
		def client = new ProtcolRun().run(file[0], "new Client()")
		
		def server = new ProtcolRun().run(file[1], "new Server()")
		
		//server.metaClass.println { String str -> output << str }
		
		
		def buf = new ByteArrayOutputStream()
		def newOut = new PrintStream(buf)
		def saveOut = System.out
		
		
		System.out = newOut
		
		
		def t = Thread.start { server.start(31337) }
		
		client.start("hei",[port: 31337, host:'localhost'])
		
		
		Thread.sleep 1000
		
		
		System.out = saveOut
		println buf.toString().trim()
		assertEquals( "hei", buf.toString().trim() )
	}
	
//	@Test
//	void testWSNet() {
//		def pn = getWebSocetNet()
//		def bindings = ORNTestUtils.createGroovyBindingsTCP()
//		assertThat pn, is(not(null))
//		def file = new OntologyDrivenGenerator().generate( pn, bindings )
//		println file
//		assertThat file, is(not(null))
//		
//		fail("nyi")
//	}
	
	
	@Test
	void testSimpleBranchingNet(){
		def pn = ATTTests.getBranchingNet()
		
		def bindings = ORNTestUtils.createGroovyBindings()
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		assertThat pn.page[0].object[1].pragmatics[0], is(not(null))
		println "output: $file"
		assertThat file, is(not(null))
		
		assertThat file[0], containsString("if")
	}
	
	@Test
	void testSimpleDoubleBranchingNet(){
		def pn = ATTTests.getDoubleBranchingNet()
		
		def bindings = ORNTestUtils.createGroovyBindings()
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		assertThat pn.page[0].object[1].pragmatics[0], is(not(null))
		println "output: $file"
		assertThat file, is(not(null))
		
		assertThat file[0], containsString("if")
		assertThat file[0], containsString("if( b )")
	}
	
	@Test
	void testSimpleLoopingNet(){
		def pn = ATTTests.getLoopingNet()
		
		def bindings = ORNTestUtils.createGroovyBindings()
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		assertThat pn.page[0].object[1].pragmatics[0], is(not(null))
		println "output: $file"
		assertThat file, is(not(null))
		
		assertThat file[0], containsString("while")
		assertThat file[0], containsString("println")
		
	}
	
	@Test
	void testSimpleLoopingBranchingNet(){
		fail("nyi")
	}
	
	@Test
	void testMessageSendingProtocol(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		pn.page << pages[0]
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		def output = []
		def client = new ProtcolRun().run(file[0], "new Sender()")
		
		def server = new ProtcolRun().run(file[1], "new Reciever()")
		
		//server.metaClass.println { String str -> output << str }
		
		
		def buf = new ByteArrayOutputStream()
		def newOut = new PrintStream(buf)
		def saveOut = System.out
		
		
		System.out = newOut
		
		
		def t = Thread.start { server.start(31337) }
		
		client.start("the quick brown fox jumps over the lazy dog",[port: 31337, host:'localhost'])
		
		
		Thread.sleep 1000
		
		
		System.out = saveOut
		println buf.toString().trim()
		assertEquals("the quick brown fox jumps over the lazy dog", buf.toString().trim() )
	}
	
	def getWebSocetNet(){
		def ornPackage = OrnPackage.eINSTANCE
		def pnmlPackage = PnmlcoremodelPackage.eINSTANCE
		ResourceSet rss = new ResourceSetImpl()
		
//		rss.getResourceFactoryRegistry().getExtensionToFactoryMap().put("pnml", new PNMLResourceFactory())
//		
//		rss.getPackageRegistry().put(ornPackage.getNsURI(), ornPackage)
		//rss.getPackageRegistry().put(pnmlPackage.getNsURI(), pnmlPackage)
		def uri = URI.createURI("file://home/kent/runtime-EclipseApplication/ornTest/model/ORNws.pnml")
		//org.k1s.orn.templates.PNMLResource res = new org.k1s.orn.templates.PNMLResource(uri)
//		println res.getAllContents()
//		println res
		Resource resource = rss.getResource(uri, true)
		assertThat resource, is(not(null))
//		resource.contents.each { 
//			println "res: $it"	
//		}
		PetriNetDocImpl da
		return  resource.contents[0].net[0]
		//new PNMLSlurper().slurp new File('/home/kent/ws-ePNK/OntologyReastrictedNets/model/ORNws.pnml')
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
class ProtcolRun {
	
		def run(String code, String starter){
			return new GroovyShell().evaluate("${code}\n${starter}")
		}
		
	}
	