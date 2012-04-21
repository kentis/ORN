package org.k1s.orn.templates

import javax.management.RuntimeErrorException;
import javax.print.attribute.standard.PDLOverrideSupported;

import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.Pragmatics;

class LinearGenerator {
	
	def templateManager
	
	PetriNet pn
	
	public LinearGenerator(pn){
		this.pn = pn
	}
	
	def generateNode(node, result = new StringBuffer()){
		checkNumOutgoing(node)
		//println node
		
		if(!(node instanceof RefPlace)){
		node.getPragmatics().each { Pragmatics prag ->
			//println(prag.getText())
			switch(prag.structure.name){
				case "External()":
					//noop
				break
				case ["fd","bk","rt","lt","pu","pd"]:
					result.append( templateManager.findAndRunTemplate(prag.structure.name, 
									prag.structure.arguments == null? "" : prag.structure.arguments) )
				break
				case "repeat":
					throw new RuntimeException("Repeat not supported");
				break
				
			}
		}
		}
		
		if(node instanceof RefPlace){
			RefPlace ref = (RefPlace) node
			def page = getPageForNode(ref.getRef())
			if(page == null) throw new RuntimeException("unable to find page for node")
			println "found page ${page.name.text}"
			def prag = page.pragmatics.size() > 0 ? page.pragmatics.get(0) : null
			if(prag != null){
				//page has a containing pragmatic
				//the subpage should be generated in the context of the training prag
				def containing = templateManager.findAndRunTemplate(prag.structure.name,
					prag.structure.arguments == null? "" : prag.structure.arguments)
				//Arc outgoing = node.getOut().get(0)
				//def outNode = outgoing.getTarget()
				def outNode = node.getRef()
				StringBuffer sb = new StringBuffer()
				def contained = generateNode(outNode, sb)
				containing.replace "%body%", contained
				result.append(containing.replace( "%body%", contained))
				
				return result.toString()
			}
			
		}else if(node.getOut().size() > 0){
			Arc outgoing = node.getOut().get(0)
			def outNode = outgoing.getTarget()
			return generateNode(outNode, result)
		} else {
			return result.toString()
		}
		
	}
	
	
	def getPageForNode(node){
		println "searching for ${node.toString()}"
		def retval
		pn.getPage().each{ Page page ->
			println "searching for ${node.toString()} in ${page.name.text}"
			def p = getPageForNode(node, page)
			println "found $p.name.text"
			if(p != null) retval =  p
		}
		return retval
	}
	
	def getPageForNode(node, page){
		def retval = null
		page.getObject().each { obj ->
			println "checking ${obj}"
			if(obj instanceof Page){
				def p = getPageForNode(node, obj)
				println "got ${p.name.text} from $obj"
				if(p != null){
					retval =  p
					return
				}
			} else 	if(obj == node)
				println "returning  $page.name.text"
				retval = page
		}
		println "retval: $retval.name.text"
		return retval
	}
	
	
	def checkNumOutgoing(node){
		if(node.getOut().size() > 1){
			throw new RuntimeException("Maximum number of outgoing arcs exeeded on: ${node.toString()}")
		}
	}
}
