package org.k1s.orn.att;

import javax.xml.bind.attachment.AttachmentMarshaller;

import org.junit.Test;
import org.k1s.orn.ORNImporter;
import org.k1s.orn.templates.ATTTests;
import org.k1s.orn.templates.OntologyDrivenGenerator;
import org.k1s.orn.test.ATTVisualizer;
import org.k1s.orn.test.ORNTestUtils;
import org.k1s.orn.test.ePNKBuilder;
import org.k1s.orn.test.ePNKBuilderTests;

import orn.Ontology;
import orn.OrnFactory;
import orn.impl.OntologyImpl;
import orn.impl.PragmaticsImpl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

class AbstractTemplateTree2Tests {
	
	@Test
	void testAttWithPrincipals(){
		def att = getAttForProtocolModel()
		
		assertThat att.children.size(), is(2)
		assertTrue att.children[0] instanceof Principal
		assertTrue att.children[1] instanceof Principal
	}
	
	@Test
	void testAttWithExternals(){
		def att = getAttForProtocolModel()
		def prin = att.children[0]
		
		assertThat prin.children.size(), is(3)
		assertTrue prin.children[0] instanceof External
		assertTrue prin.children[1] instanceof External
		assertTrue prin.children[2] instanceof External
	}
	
	
	@Test
	void testATTForSimpleProtocol(){
		def pn = ORNTestUtils.getSimpleProtocolPN()
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree2.attForNet(pn, bindings)
		
		assertThat att, is(not(null))
		assertThat att.children.size(), is(2)
	}
	
	@Test
	void testATTForBranchingNet(){
		def pn = getBranchingNet()
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree2.attForNet(pn, bindings)
		
		println att
		
		assertThat att, is(not(null))
		assertThat att.children.size(), is(1)
		println att.children[0]
		println att.children[0].children[0]
		assertThat att.children[0].children.size(), is(1)
		println att.children[0].children[0].children[0]
		assertThat att.children[0].children[0].children.size(), is(1)
		
		println att.children[0].children[0].children[0].children
		assertThat att.children[0].children[0].children[0].children.size(), is(2)
	}
	
	@Test
	void testSimpleLoopingNet(){
		def pn = getLoopingNet()
		def bindings = ORNTestUtils.createGroovyBindings()
		//def att = AbstractTemplateTree2.attForNet(pn, bindings)
		
		
		assertThat pn, is(not(null))
		def file = new OntologyDrivenGenerator().generate2( pn, bindings )
		assertThat pn.page[0].object[1].pragmatics[0], is(not(null))
		println "output: $file"
		assertThat file, is(not(null))
		
		assertThat file[0], containsString("while")
		assertThat file[0], containsString("println")
		assertThat file[0], containsString("outMsg.size() == 0")
		assertThat file[0], containsString("dilldall")
		
	}
	
	@Test
	void testLCVsForSenderReciever(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		
		pn.page << pages[0]
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		assertThat pn, is(not(null))
		
		def file = new OntologyDrivenGenerator().generate2( pn, bindings )
		
		assertThat file[0], containsString("def Idle = true")
		assertThat file[0], containsString("def Open")
	}
	
	@Test
	void testLCVsForSenderRecieverExternals(){
		def pages = new ORNImporter().translate("/home/kent/projects/ws/websocket/codegen/cpnmodel/ProtocolModel.cpn")
		def bindings = ORNTestUtils.createGroovyBindings()
		def pn = ORNTestUtils.getPetriNet()
		
		pn.page << pages[0]
		Ontology ont = new OntologyImpl()
		ont.setText "/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl"
		ont.setStructure(ont.parse(ont.getText()))
		pn.page[0].getOntology() << ont
		
		assertThat pn, is(not(null))
		
		def file = new OntologyDrivenGenerator().generate2( pn, bindings )
		println file
		assertThat file[0], containsString("if(!Idle) throw new Exception('unfulfilled precondition: Idle')")
		assertThat file[0], containsString("if(!Open) throw new Exception('unfulfilled precondition: Open')")
	}
	
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
	
	def getAttForProtocolModel(){
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
		
		return att
	}
	
	static def getBranchingNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		
		def pn = builder.make {
			page(name:'root'){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					page(name: 'doStuff', labels:[pragmatics: "External(a,b,c, server)"]){
						def ext = transition(name:'send', labels:[pragmatics: "External(a,b,c, server)"])
						
						def branch = place(name: 'branch',labels:[pragmatics: "Cond('(c dsadasdas)(t a2)')"])
						arc(ext,branch)
						
						def sendA = transition(name: 'sendA', labels:[pragmatics: "Send(a)"])
						arc(branch, sendA, "dsadasdas")
						
						def sendB = transition(name: 'sendB', labels:[pragmatics: "Send(b)"])
						arc(branch, sendB, "a2")
						
						
						def merge = place(name: 'merge',labels:[pragmatics: "Merge()"])
						arc(sendA, merge)
						arc(sendB, merge)
						
						def	noop = transition(name: 'noop')
					}
				}
			}
		}
	}
	
	
	static def getLoopingNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		
		def pn = builder.make {
			page(name:'root'){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					page(name: "looptiloop", labels:[pragmatics: "External(outMsg)"]){	
						def ext = transition(name:'send', labels:[pragmatics: "External(outMsg)"])
						
						def outMsg = place(name:'outMsg')
						arc(ext, outMsg)
						
						def loopStart = place(name:'loopStart', labels:[pragmatics: "Loop()"])
						arc(ext, loopStart)
						
						def nextMsg = transition(name:'nextMsg', labels:[pragmatics: "RemoveHead(outMsg, msg)"])
						arc(outMsg, nextMsg)
						arc(loopStart, nextMsg)
						
						def waitSend = place(name: 'waitSend', labels:[pragmatics: "Id()"] )
						arc(nextMsg, waitSend)
						
						def send = transition(name: 'send', labels:[pragmatics: "Print(msg)"])
						arc(waitSend, send)
						
						
						def loopEnd = place(name:'loopEnd', labels:[pragmatics: "EndLop(cond: '(eq (sizeOf outMsg) 0)')"])
						arc(send, loopEnd)
						
						def nextLoop = transition(name:'nextLoop')
						arc(loopEnd, nextLoop, 'next')
						arc(nextLoop, loopStart)
						
						def loopFinished = transition(name: 'loopFinished', labels:[pragmatics: "Print('dilldall')"])
						arc(loopEnd, loopFinished, 'break')
						
						def	noop = place(name: 'noop', labels:[pragmatics: "Id()"])
						arc(loopFinished,noop)
						
						def ret = transition(name:'return', labels:[pragmatics: "Return()"])
						arc noop, ret
					}
				}
			}
		}
	}
}
