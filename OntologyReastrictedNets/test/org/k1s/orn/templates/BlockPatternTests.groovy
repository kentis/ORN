package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ATTVisualizer;
import org.k1s.orn.test.ORNTestUtils;
import org.k1s.orn.test.ePNKBuilder;
import org.k1s.orn.test.ePNKBuilderTests;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.Ontology;
import orn.OrnFactory;
import orn.impl.OntologyImpl;
import orn.impl.PragmaticsImpl;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class BlockPatternTests {

	
	@Test
	void testFindLoop(){
		def pn = getLoopNet()
		new OntologyDrivenGenerator().addExtraPragmatics(pn)
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		println att
		new ATTVisualizer().writeATTImage att, "dill.png"
		
		assertThat att.children[0].children[0].children[0].correspondingNetElement.pragmatics[0].structure.name, is('Loop')
		assertThat att.children[0].children[0].children[0].children[0].children[1].correspondingNetElement.pragmatics[0].structure.name, is('Id')
		
		assertThat att.children[0].children[0].children.size(), is(3)
	}
	
	@Test
	void testFindNestedLoops(){
		def pn = getNestedLoopNet()
		new OntologyDrivenGenerator().addExtraPragmatics(pn)
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		println att
		new ATTVisualizer().writeATTImage att, "dill.png"
		
		assertThat att.children[0].children[0].children[0].correspondingNetElement.pragmatics[0].structure.name, is('Loop')
		assertThat att.children[0].children[0].children[0].children[0].children[1].correspondingNetElement.pragmatics[0].structure.name, is('Loop')
		
	}
	
	
	@Test
	void testFindOverlappingLoops(){
		try{
		def pn = getOverlappingLoopNet()
		new OntologyDrivenGenerator().addExtraPragmatics(pn)
		def bindings = ORNTestUtils.createGroovyBindings()
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		println att
		new ATTVisualizer().writeATTImage att, "dillWTF.png"
		
		assertThat att.children[0].children[0].children[0].correspondingNetElement.pragmatics[0].structure.name, is('Loop')
		assertThat att.children[0].children[0].children[0].children[0].children[1].correspondingNetElement.pragmatics[0].structure.name, is('Loop')
		fail("should not be acceptable")
		}catch(ATTNotOKException e){
			//AOK
			e.printStackTrace()
		}
	}
	static def getLoopNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class, ontology: OntologyImpl.class])
		
		def pn = builder.make {
			page(name:'root', labels:[ontology: '/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl']){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					
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
					
					def noopTrans = transition(name: 'noopTranition',labels:[pragmatics: "Print(msg)"])
					arc(loopEnd, noopTrans, 'break')
					
					def	noop = place(name: 'noop', labels:[pragmatics: "Id()"])
					
					arc(noopTrans, noop)
					
				}
			}
		}
	}
	
	
	static def getNestedLoopNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class, ontology: OntologyImpl.class])
		
		def pn = builder.make {
			page(name:'root', labels:[ontology: '/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl']){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					
					def ext = transition(name:'send', labels:[pragmatics: "External(outMsg)"])
					
					def outMsg = place(name:'outMsg')
					arc(ext, outMsg)
					
					def loopStart = place(name:'loopStart', labels:[pragmatics: "Id()"])
					arc(ext, loopStart)
					
					def nextMsg = transition(name:'nextMsg', labels:[pragmatics: "RemoveHead(outMsg, msg)"])
					arc(outMsg, nextMsg)
					arc(loopStart, nextMsg)
					
					def innerLoopStart = place(name: 'innerLoopStart', labels:[pragmatics: "Id()"] )
					arc(nextMsg, innerLoopStart)
					
					def prnt = transition(name: 'send', labels:[pragmatics: "Print(msg)"])
					arc(innerLoopStart, prnt)
					
					def innerLoopEnd = place(name: 'innerLoopEnd', labels:[pragmatics: "Id()"])
					 arc(prnt, innerLoopEnd)
					
					def nextInner = transition(name: 'nextInner')
					 arc(innerLoopEnd, nextInner)
					 arc(nextInner, innerLoopStart)
					 
					 def endOuter = transition(name:'goToLoopEnd')
					 arc(innerLoopEnd, endOuter)
					 
					def loopEnd = place(name:'loopEnd', labels:[pragmatics: "Id()"])
					arc(endOuter, loopEnd)
					
					def nextLoop = transition(name:'nextLoop')
					arc(loopEnd, nextLoop, 'next')
					arc(nextLoop, loopStart)
					
					def	noop = place(name: 'noop')
					arc(loopEnd,noop, 'break')
					
				}
			}
		}
		return pn
	}
	
	static def getOverlappingLoopNet(){
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class, ontology: OntologyImpl.class])
		
		def pn = builder.make {
			page(name:'root', labels:[ontology: '/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl']){
				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
				
				page(name: 'Client', labels:[pragmatics: "Principal()"]){
					
					def ext = transition(name:'send', labels:[pragmatics: "External(outMsg)"])
					
					def outMsg = place(name:'outMsg')
					arc(ext, outMsg)
					
					def loopStart = place(name:'loopStart', labels:[pragmatics: "Id()"])
					arc(ext, loopStart)
					
					def nextMsg = transition(name:'nextMsg', labels:[pragmatics: "RemoveHead(outMsg, msg)"])
					arc(outMsg, nextMsg)
					arc(loopStart, nextMsg)
					
					def innerLoopStart = place(name: 'innerLoopStart', labels:[pragmatics: "Id()"] )
					arc(nextMsg, innerLoopStart)
					
					def prnt = transition(name: 'send', labels:[pragmatics: "Print(msg)"])
					arc(innerLoopStart, prnt)
					
					def wait = place(name: "wait", labels:[pragmatics: "Id()"])
					arc(prnt, wait) 
					
					
					 def endOuter = transition(name:'goToLoopEnd')
					 arc(wait, endOuter)
					 
					def loopEnd = place(name:'loopEnd', labels:[pragmatics: "Id()"])
					arc(endOuter, loopEnd)
					
					def nextLoop = transition(name:'nextLoop')
					arc(loopEnd, nextLoop, 'next')
					arc(nextLoop, loopStart)
					
					def noopTrans = transition(name: 'noopTrans')
					arc(loopEnd, noopTrans, 'break')
					def	noop = place(name: 'noop', labels:[pragmatics: "Id()"])
					arc(noopTrans,noop, 'break')
					
					def endInner = transition(name:'endInner')
					arc(noop, endInner)
					
					def innerLoopEnd = place(name: 'innerLoopEnd', labels:[pragmatics: "Id('innerEnd')"])
					arc(endInner, innerLoopEnd)
									   
				   def nextInner = transition(name: 'nextInner')
					arc(innerLoopEnd, nextInner)
					arc(nextInner, innerLoopStart)
				   
					def quit = transition(name:'quit', labels:[pragmatics: "Print(quit)"])
					arc(innerLoopEnd, quit)
				}
			}
		}
		return pn
	}
//		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, , [pragmatics: PragmaticsImpl.class, ontology: OntologyImpl.class])
//		
//		def pn = builder.make {
//			page(name:'root', labels:[ontology: '/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/nppn.fowl']){
//				def ch = place(name:'channel', labels:[pragmatics: "Channel()"])
//				
//				page(name: 'Client', labels:[pragmatics: "Principal()"]){
//					
//					def ext = transition(name:'send', labels:[pragmatics: "External(outMsg)"])
//					
//					def outMsg = place(name:'outMsg')
//					arc(ext, outMsg)
//					
//					def loopStart = place(name:'loopStart', labels:[pragmatics: "Id()"])
//					arc(ext, loopStart)
//					
//					def nextMsg = transition(name:'nextMsg', labels:[pragmatics: "RemoveHead(outMsg, msg)"])
//					arc(outMsg, nextMsg)
//					arc(loopStart, nextMsg)
//					
//					def innerLoopStart = place(name: 'innerLoopStart', labels:[pragmatics: "Id()"] )
//					arc(nextMsg, innerLoopStart)
//					
//					def prnt = transition(name: 'send', labels:[pragmatics: "Print(msg)"])
//					arc(innerLoopStart, prnt)
//					
//					def innerLoopEnd = place(name: 'innerLoopEnd', labels:[pragmatics: "Id()"])
//					 arc(prnt, innerLoopEnd)
//					 
//					
//					 
//					 def endOuter = transition(name:'goToLoopEnd')
//					 arc(innerLoopEnd, endOuter)
//					 
//					def loopEnd = place(name:'loopEnd', labels:[pragmatics: "Id()"])
//					arc(endOuter, loopEnd)
//					
//					def nextLoop = transition(name:'nextLoop')
//					arc(loopEnd, nextLoop, 'next')
//					arc(nextLoop, loopStart)
//					
//					def	noop = place(name: 'noop', labels:[pragmatics: "Id()"])
//					arc(loopEnd,noop, 'break')
//					
//					def nextInner = transition(name: 'nextInner')
//					arc(noop, nextInner)
//					arc(nextInner, innerLoopStart)
//					
//				}
//			}
//		}
//	}
}
