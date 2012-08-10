package org.k1s.orn.templates
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;



import org.pnml.tools.epnk.pnmlcoremodel.Transition;



class AbstractTemplateTree {
	def templateText
	def parameters
	
	def isContainer
	def isMultiContainer
	def children = []
	def parent
	
	def fileName
	def outputDir
	
	def templateBinding
	def templateName
	def pragmaticName
	
	def label
	def correspondingNetElement
	
	String toString(){
		if(correspondingNetElement != null)
			return "|${correspondingNetElement.name.text}, ${correspondingNetElement.getClass()}, ${correspondingNetElement.pragmatics[0]?.text}|"
			
		return "|SEQ|"
	}
	static rnd = new Random()
	String toGraphString(i){
		if(correspondingNetElement != null)
		return "<html>$i<br>${correspondingNetElement.name.text}<br/> ${correspondingNetElement.getClass()}<br/> ${correspondingNetElement.pragmatics[0]?.text}</html>"
		
	return "<html>$i<br>|SEQ|${rnd.nextInt()}</html>"
	}
	
	static def attForNet(pn, Bindings bindings){
		def rootPage = pn.page[0]
		def att = new AbstractTemplateTree(templateName: "ROOT", pragmaticName: "ROOT", correspondingNetElement: rootPage)
		rootPage.object.each{ node ->
			if(node instanceof Page) att.children  << attForNode(node, bindings, att)
		}
		return att
	}
	
	
	static def attForNode(node, Bindings bindings, parent = null, usedNodes = []){
		//println "gettin att for: ${node.name} prags: ${node.pragmatics}"
		def att = new AbstractTemplateTree()
		
		def binding = null
		if((node.properties.containsKey("pragmatics") && node.pragmatics.size() > 0)){
			if(bindings.prag2Binding[node.pragmatics[0].structure.name]){
				binding = bindings.prag2Binding[node.pragmatics[0].structure.name]
			} else {
				
				
				println "NO NAME??  ${node.pragmatics[0].structure.name}"
				println "NODE: $node.name"
				println "NODE PROPS: ${node.properties}"
				return null
			}
		} else if(!(node instanceof Page)){
			println "NO PRAG??"
			println "NODE: $node"
			println "NODE PROPS: ${node.properties}"
			return null
		}
		if(binding != null){
			att.isContainer = binding.isContainer
			att.isMultiContainer = binding.isMultiContainer
		} else{
			att.isContainer = (node instanceof Page)
		}
		att.templateBinding = binding
		att.parent = parent
		att.correspondingNetElement = node
		if(att.isMultiContainer){
			handleMultiContainer(att, node, bindings, parent, usedNodes)
			
		} else 
		if(att.isContainer){
			if(node instanceof Page && findStartForPage(node) == null){
				def subatts = []
				node.object.each { 
					if(it instanceof Page){
						att.children << attForNode(it, bindings, parent, usedNodes)
					}
					//parent.children.addAll(subatts)
				}
			} else {
				def nextNode = findNextNode(node, parent)
				println "found: $nextNode"
				while(nextNode != null){
					
					if(!usedNodes.contains(nextNode)){
						usedNodes << nextNode
						def nextAtt = attForNode(nextNode, bindings, att, usedNodes)
						if(nextAtt != null) att.children << nextAtt 
						
						nextNode = findNextNode(nextNode, parent, nextAtt)
						println "found: $nextNode"
					} else {
						//escape loop
						nextNode = null 
					}
				}
			}
		}
		return att
	}
	
	static def handleMultiContainer(att, node, bindings, parent, usedNodes){
		
		node.out.each { outArc -> 
			def seq = new AbstractTemplateTree()
			seq.label = outArc.name == null ? "" : outArc.name.text
			seq.setTemplateText "%%yield%%"
			def nextNode = outArc.target
			while(nextNode != null && !nodeIsMerge(nextNode)&&!nodeIsLoopEnd(node)){
				if(!usedNodes.contains(nextNode)){
					usedNodes << nextNode
					def nextAtt = attForNode(nextNode, bindings, seq, usedNodes)
					if(nextAtt != null) seq.children << nextAtt
					
					nextNode = findNextNode(nextNode, parent, nextAtt)
				} else {
					//escape loop
					nextNode = null
				}
			}
			
			att.children << seq
		}
		
		
		return att
	}
	
	static def findNextNode(node, parent, att = null){
		println "finding next for $node.name"
		switch(node){
			case Transition:
				println "finding next for a transition"
				if(node.out.size() == 1){
					return node.out.target[0]
				}
				if(node.out.size() > 1){
					println "with more than 1 outgoing"
					//find the Id node
					def idNodes = []
					node.out.each{ idCandidate ->
						println "checking idCandidate: $idCandidate.target.name"
						println "with Pragname: ${idCandidate.target.pragmatics[0]}"
						if(!(idCandidate.target instanceof RefPlace) &&
							idCandidate.target.pragmatics[0] != null &&
						 (idCandidate.target.pragmatics[0].structure.name == "Id" ||
							 idCandidate.target.pragmatics[0].structure.name == "Cond" ||
							 idCandidate.target.pragmatics[0].structure.name == "Loop" ||
							 idCandidate.target.pragmatics[0].structure.name == "EndLop"
						)) idNodes << idCandidate.target
					}
					if(idNodes.size() > 1) throw new RuntimeException("Too many places to go from $node")
					else return idNodes[0]
				}

			case Place:
				
				if(nodeIsLoop(node)){
					println "FIND NEXT FOR: $node    ${node.pragmatics[0].text}"
					def loopEnd = findLoopEnd(att, node)
					if(loopEnd == null) throw new ATTNotOKException("Loop has no End")
					def nextNode
					loopEnd.out.each {
						def target = it.target
						if(target.out.size() == 1 && target.out[0].target == node){
							//ignore this path
						} else {
							nextNode = target
						}
					}
					return nextNode
				} else if(node.out.size() == 1){
					return node.out.target[0]
				}
				break
			case Page:
				return findStartForPage(node)
				break;
		}
	}
	
	static def findLoopEnd(att, node){
		def retval
		att.children[0].children.each{
			if(nodeIsLoopEnd(it.correspondingNetElement)){
				def hasNodeAsSuccessor = false
				it.correspondingNetElement.out.target.each{ target ->
					if(target.out.size() > 0 && target.out[0].target == node)
						hasNodeAsSuccessor = true
				}
				if(hasNodeAsSuccessor)
					retval = it.correspondingNetElement
			}
		}
		return retval
	}
	
	static def nodeIsId(node){
		return node.pragmatics[0].structure.name == "Id"
	}
	
	static def nodeIsConditional(node){
		if(nodeIsId(node)){
			node.pragmatics[0].structure.parameters.contains "Cond"
		}
	}

	static def nodeIsLoop(node){
		if(node.pragmatics.size() > 0){
			//return node.pragmatics[0].structure.params.contains("Loop") 
		//} else {
			return  node.pragmatics[0].structure.name == "Loop"
		}
	}
	
	static def nodeIsLoopEnd(node){
			return node.pragmatics[0].structure.name == "EndLop" || node.pragmatics[0].structure.name == "EndLoop"
	}
		
	static def nodeIsMerge(node){
		if(node.pragmatics.size() > 0){
		return node.pragmatics[0].structure.name == "Merge"
		} else return false
	}
	
	static def findStartForPage(page){
		def startNode
		//first, look for external
		println page
		page.object.each{
			if(it.properties.containsKey("pragmatics") && it.pragmatics.size() > 0){
				if(it.pragmatics[0]?.structure.name == "External"){
					startNode = it
				}
			}
		}
		
		return startNode
	}
}

