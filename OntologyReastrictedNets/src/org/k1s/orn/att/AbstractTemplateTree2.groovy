package org.k1s.orn.att

import org.k1s.orn.templates.ATTNotOKException;
import org.k1s.orn.templates.Bindings;
import org.pnml.tools.epnk.pnmlcoremodel.Page;

import orn.Arc;
import orn.Place;
import orn.impl.PageImpl;


class AbstractTemplateTree2 {
	
	static final def END_LOP = "EndLop"
	static final def CONTROL_FLOW_PRAGS = ["Loop", "Cond", "Id", END_LOP]
	
	def children = []
	
	String toGraphString(i){
		return "ATT $i"
	}
	/**
	 * Gets the ATT for the given net with the given bindings
	 * @param pn
	 * @param bindings
	 * @return
	 */
	static def attForNet(pn, Bindings bindings){
		def rootPage = pn.page[0]
		def att = new AbstractTemplateTree2()
		
		//find Principals
		rootPage.object.each{ node ->
			if(node.pragmatics != null) println node.pragmatics.structure.name
			if(node instanceof PageImpl && node.pragmatics[0].structure.name == 'Principal') {
				att.children  << attForPrincipal(node, bindings, att)
			}
		}
		return att
	}
	
	static def attForPrincipal(node, bindings, att){
		Principal prin = new Principal()
		prin.correspondingNetElement = node
		prin.templateBindings << bindings.prag2Binding[node.pragmatics[0].structure.name]
		prin.pargmatics << node.pragmatics[0]
		prin.parent = att
		//find Externals
		node.object.each{ subnode ->
			if(subnode instanceof PageImpl && subnode.pragmatics[0].structure.name == 'External') {
				prin.children  << attForExternal(subnode, bindings, att)
			}
		}
		
		return prin
	}
	
	static def attForExternal(node, bindings, principal){
		External ext = new External()
		ext.correspondingNetElement = node
		ext.templateBindings << bindings.prag2Binding[node.pragmatics[0].structure.name]
		ext.pargmatics << node.pragmatics[0]
		ext.parent = ext
		ext.startNode = findExtTransition(node)
		
		
		def block = findNextBlock(ext.startNode, bindings, ext)
		if(block != null)ext.children << block
		while(block && block.end) {
			block = makeNextBlock(block.end, bindings, ext)
			if(block != null) ext.children << block
		}
		//		println "ENDING External: last block: $block"
		//		println "ends with ${block.end}"
		return ext
	}
	
	static def findNextBlock(node, bindings, ext){
		def retval
		
		
		node.out.each { arc ->
			def a = arc.target 
			if(isControlFlow(a) && !isStartOfPrevLoop(a, node, ext)){
				if(retval != null) thrown new ATTNotOKException("Ambiguous control flow at $a")
				retval = makeNextBlock(a, bindings, node)
			}
		}
		
		return retval
	}
	
	/**
	 * 
	 * @param a the suspected start
	 * @param node the current node
	 * @ext the container
	 */
	static def isStartOfPrevLoop(a, node, ext){
		if(ext.children.size() > 0){
			def last = ext.children.last()
			println "1: ${last instanceof Loop}  --- ${ext.children.each{ it.class}}"
			println "2: ${isEndLoop(node)}"
			println "3: ${last.end == node}"
			if(last instanceof Loop && isEndLoop(node) && last.end == node){
				return a == last.start
			}
		}
		return false
	}
	
	static def makeNextBlock(a, bindings, node){
		def retval
		switch(a.pragmatics[0].structure.name){
			case "Loop":
				retval = makeLoop(a, bindings, node)
				break
			case "Cond":
				retval = makeCond(a, bindings, node)
				break
			case END_LOP:
				println "CONTINUING FROM LOOP"
			case "Id":
				retval = makeTransition(a, bindings,node)
				break
		}
		return retval
	}
	
	static def isControlFlow(node){
		if(node instanceof Place){
			if(node.pragmatics.size() > 0){
				return CONTROL_FLOW_PRAGS.contains(node.pragmatics[0].structure.name)
			}
		}
		return false
	}
	
	static def isEndLoop(node){
		if(node instanceof Place){
			if(node.pragmatics.size() > 0){
				return node.pragmatics[0].structure.name == END_LOP
			}
		}
		return false
	}
	
	
	static def makeCond(node, bindings, parent){
		Choice c = new Choice()
		c.start = node
		c.correspondingNetElement = node
		c.templateBindings = bindings.prag2Binding[node.pragmatics[0].structure.name]
		c.pargmatics = node.pragmatics[0]
		c.parent = parent
		
		node.out.each{
			Sequence seq = new Sequence()
			
			c.children << seq
		}
		
		return c
	}
	
	static def makeLoop(node, bindings, parent){
		Loop l = new Loop()
		l.correspondingNetElement = node
		l.start = node
		l.templateBindings << bindings.prag2Binding[node.pragmatics[0].structure.name]
		l.pargmatics << node.pragmatics[0]
		l.parent = parent
		
		def block = makeTransition(node, bindings,parent)
		l.children << block 
		
		while(block.end && block.end.pragmatics[0].structure.name != END_LOP ){
			block = makeNextBlock(block.end, bindings, node)
			l.children << block
		}
		
		l.end = l.children.last().end
		
		return l
	}
	
	
	
	static def makeTransition(node, bindings, parent){
		Transition t = new Transition()
		t.start = node
		
		t.correspondingNetElement = getOperationalTransition(node, parent)
		if(t.correspondingNetElement.pragmatics.size() > 0){
			//Add prags
			t.correspondingNetElement.pragmatics.each{
				
				t.templateBindings << bindings.prag2Binding[it.structure.name]
				t.pargmatics << it
			}
			//Add prags from outging Arc
			
		}
		t.parent = parent
		
		t.end = getNextControlFlow(t.correspondingNetElement)
		
		return t
	}
	
	static def makeFirstTransition(transition, bindings, parent){
		Transition t = new Transition()
		
		
		t.correspondingNetElement = transition
		
		t.templateBindings << bindings.prag2Binding[t.correspondingNetElement.pragmatics[0].structure.name]
		t.pargmatics = t.correspondingNetElement.pragmatics[0]
		t.parent = parent
		
		t.end = getNextControlFlow(t.correspondingNetElement)
		
		return t
	}
	
	static def getOperationalTransition(node, parent = null){
		if(node.out.size() == 1){
			return node.out[0].target
		} else if(node.out.size() > 1){
			def retval = []
			node.out.each { arc -> 
				if(leadsToControlFlow(arc.target)){
					println parent
					if(parent)
						println "checking $arc.target.name.text"
						println "isStartOfPrevLoop: ${isStartOfPrevLoop(getNextControlFlow(arc.target), node,parent)}"
						println "a: ${getNextControlFlow(arc.target).name}"
						println "node: ${node.name.text}"
						println "parent: ${parent}"
						println "parent-last: ${parent.children.last().start.name.text}"
						if(isStartOfPrevLoop(getNextControlFlow(arc.target), node,parent)){
							println "identified backlink"
						}else {
							retval << arc.target
						}
				}
			}
			if(retval.size() > 1)throw new ATTNotOKException("Ambiguous conrolflow at : $node.name")
			if(retval.size() < 1)throw new ATTNotOKException("No controlflow found")
			return retval[0]
		} else {
			throw new ATTNotOKException("No transition for atomic transition: ${node.name.text}")
		}
	}
	
	static def leadsToControlFlow(node){
		def leadsToControlFlow = false
		node.out.each {arc -> 
			if(isControlFlow(arc.target)) leadsToControlFlow = true
		}
		return leadsToControlFlow
	}
	
	static def getNextControlFlow(transition){
		def cflow = null
		transition.out.each {arc ->
			if(isControlFlow(arc.target)) cflow = arc.target
		}
		return cflow
	}
	
	/**
	 * Find the external for the page.
	 * @param page
	 * @return
	 */
	static def findExtTransition(page){
		println "finding EXT on page ${page.name.text}"
		def ext
		page.object.each{ node ->
			if(!(node instanceof Arc)){
				if(node.pragmatics != null && node.pragmatics.size() > 0){
					println "AAAAAAAA ${node.pragmatics.structure.name}    ${node.name.text}"
					if(node.pragmatics.structure.name.contains('External')){
						if(ext != null) throw new ATTNotOKException("more than one External in Page")
						ext = node
					}
				}
			}
		}
		if(ext == null) throw new ATTNotOKException("No externals found on page: "+page.name.text)
		return ext
	}
}
