package org.k1s.orn;



import org.junit.Test;
import org.k1s.orn.att.AbstractTemplateTree2;
import org.k1s.orn.templates.OntologyDrivenGenerator
import org.k1s.orn.templates.ProtcolRun
import org.k1s.orn.test.ATTVisualizer
import org.k1s.orn.test.ORNTestUtils;

import orn.Ontology
import orn.impl.OntologyImpl


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
class EvaluationTests {
	@Test
	void testVisualizeATTForSenderReciever(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		
		pn.page << pages[0]
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		assertThat pn, is(not(null))
		
		new OntologyDrivenGenerator().addExtraPragmatics(pn)
		
		def att = AbstractTemplateTree2.attForNet(pn, bindings)
		
		
		new ATTVisualizer().writeATTImage att, "protomodel.png"
		
	}

	@Test
	void testMessageSendingProtocolFilesHasToken(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		pn.page << pages[0]
		assertThat pn, is(not(null))
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		assertThat file[0], is(not(null))
		
		assertThat file[0], containsString("__TOKEN__")
		assertThat file[0], containsString("def __TOKEN__")
		assertThat file[0], containsString("__TOKEN__ = ")
	}

	
		
	@Test
	void testMessageSendingProtocolFiles(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		pn.page << pages[0]
		assertThat pn, is(not(null))
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		assertThat file[0], is(not(null))
		
		assertThat file[0], containsString("__TOKEN__")
		
		assertThat file[0], containsString("LOOP_VAR == __TOKEN__[0] == 1")
		
	}
	
	
	
	@Test
	void testMessageSendingProtocol(){
		fail "NYW"
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		pn.page << pages[0]
		assertThat pn, is(not(null))
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		def file = new OntologyDrivenGenerator().generate( pn, bindings )
		println file
		assertThat file, is(not(null))
		
		def output = []
		def client = new ProtcolRun().run(file[0], "new Sender()")
		
		def server = new ProtcolRun().run(file[1], "new Receiver()")
		
		//server.metaClass.println { String str -> output << str }
		
		
		def buf = new ByteArrayOutputStream()
		def newOut = new PrintStream(buf)
		def saveOut = System.out
		
		
		System.out = newOut
		
		
		def t = Thread.start { 
				//server.start(31337)
			server.ReceiverInit()
			server.ReceiverReceive()
		}
		
		//client.start("the quick brown fox jumps over the lazy dog",[port: 31337, host:'localhost'])
		client.SenderOpen()
		client.SenderSend()
		client.SenderClose()
		
		Thread.sleep 1000
		t.stop()
		
		System.out = saveOut
		println buf.toString().trim()
		assertEquals("the quick brown fox jumps over the lazy dog", buf.toString().trim() )
	}
	
	
}
