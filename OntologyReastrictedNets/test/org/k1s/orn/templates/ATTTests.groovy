package org.k1s.orn.templates;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.junit.Test;
import org.k1s.orn.ORNImporter;
import org.k1s.orn.test.ATTVisualizer;
import org.k1s.orn.test.ORNTestUtils;
import org.k1s.orn.test.ePNKBuilder;
import org.pnml.tools.epnk.pnmlcoremodel.Graphics;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.ToolInfo;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.Ontology;
import orn.OntologyStructure;
import orn.OrnFactory;
import orn.impl.OntologyImpl;
import orn.impl.PragmaticsImpl;

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
	
	
	@Test
	void testATTForBranchingNet(){
		def pn = getBranchingNet()
		def bindings = ORNTestUtils.createGroovyBindings()
		
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		
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
	void testhandleMultiContainer(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		def pn = builder.make {
			page(name:'root'){
				def branch = place(name: 'branch',labels:[pragmatics: "Cond('(c a1)(t a2)')"])
				
				
				def sendA = transition(name: 'sendA', labels:[pragmatics: "Send(a)"])
				arc(branch, sendA, "a1")
				
				def sendB = transition(name: 'sendB', labels:[pragmatics: "Send(b)"])
				arc(branch, sendB, "a2")
				
				
				def merge = place(name: 'merge',labels:[pragmatics: "Id(closeCond: true)"])
				arc(sendA, merge)
				arc(sendB, merge)
			}
		}
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = new AbstractTemplateTree()
		def node = pn.page[0].object[0]
		def binding = bindings.prag2Binding[node.pragmatics[0].structure.name]
		def parent = pn.page[0]
		att.templateBinding = binding
		att.parent = parent
		att.correspondingNetElement = node
		att.isMultiContainer = binding.isMultiContainer
		
		assertThat att.isMultiContainer, is(true)
		
		AbstractTemplateTree.handleMultiContainer(att, node, bindings, parent, [])
		println att.children
		assertThat att.children.size(), is(2)
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
		
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		
		
		new ATTVisualizer().writeATTImage att, "protomodel.png"
		
	}
	
	static def getBranchingNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		
		def pn = builder.make {
			page(name:'root'){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					
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
	
	static def getDoubleBranchingNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		
		def pn = builder.make {
			page(name:'root'){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					
					def ext = transition(name:'send', labels:[pragmatics: "External(a,b,c, server)"])
					
					def branch = place(name: 'branch',labels:[pragmatics: "Cond('(c a1)(t a2)')"])
					arc(ext,branch)
					
					def branch2 = transition(name: 'sendA', labels:[pragmatics: "Cond('(b b1)(t b2)')"])
					arc(branch, branch2, "a1")
					
					
					def sendA1 = transition(name: 'sendA2', labels:[pragmatics: "Send(a)"])
					arc(branch2, sendA1, "b1")
					def sendB1 = transition(name: 'sendB2', labels:[pragmatics: "Send(b)"])
					arc(branch2, sendB1, "b2")
					def merge2 = place(name: 'merge2',labels:[pragmatics: "Merge()"])
					arc(sendB1,merge2)
					arc(sendA1,merge2)
					
					def sendB = transition(name: 'sendB', labels:[pragmatics: "Send(b)"])
					arc(branch, sendB, "a2")
					
					
					def merge = place(name: 'merge',labels:[pragmatics: "Merge()"])
					arc(merge2, merge)
					arc(sendB, merge)
					
					def	noop = transition(name: 'noop')
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
					page(name: 'Sned', labels:[pragmatics: "External(outMsg)"]){
					
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
						arc(loopFinished,noop, 'break')
						
						def noopTrans = transition(name: 'noopTrans')
						arc(noop, noopTrans)
					}
				}
			}
		}
	}
	
	
	static def getImplicitLoopingNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class])
		
		def pn = builder.make {
			page(name:'root'){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					page(name: 'Send', labels:[pragmatics: "External(outMsg)"]){
					
						def ext = transition(name:'send', labels:[pragmatics: "External(outMsg)"])
						
						def outMsg = place(name:'outMsg')
						arc(ext, outMsg)
						
						def loopStart = place(name:'loopStart', labels:[pragmatics: "Id()"])
						arc(ext, loopStart)
						
						def nextMsg = transition(name:'nextMsg', labels:[pragmatics: "RemoveHead(outMsg, msg)"])
						arc(outMsg, nextMsg)
						arc(loopStart, nextMsg)
						
						def waitSend = place(name: 'waitSend', labels:[pragmatics: "Id()"] )
						arc(nextMsg, waitSend)
						
						def send = transition(name: 'send', labels:[pragmatics: "Print(msg)"])
						arc(waitSend, send)
						
						
						def loopEnd = place(name:'loopEnd', labels:[pragmatics: "Id()"])
						arc(send, loopEnd)
						
						def nextLoop = transition(name:'nextLoop')
						arc(loopEnd, nextLoop, 'next')
						arc(nextLoop, loopStart)
						
						def loopFinished = transition(name: 'loopFinished', labels:[pragmatics: "Print('dilldall')"])
						arc(loopEnd, loopFinished, 'break')
						
						def	noop = place(name: 'noop', labels:[pragmatics: "Id()"])
						arc(loopFinished,noop, 'break')
						
						def noopTrans = transition(name: 'noopTrans')
						arc(noop, noopTrans)
					}
				}
			}
		}
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