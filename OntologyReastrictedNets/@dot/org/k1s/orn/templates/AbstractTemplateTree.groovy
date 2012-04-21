package org.k1s.orn.templates
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Place;



import org.pnml.tools.epnk.pnmlcoremodel.Transition;



class AbstractTemplateTree {
	def templateText
	def parameters

	def isContainer
	def children = []
	def parent
	
	def fileName
	def outputDir
	
	def templateBinding
	def templateName
	def pragmaticName
	
	def correspondingNetElement
	
	String toString(){
		return "|${templateName}, ${pragmaticName}, ${templateText}|"
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
		def att = new AbstractTemplateTree()
		
		def binding = null
		if(node.properties.containsKey("pragmatics") && node.pragmatics.size() > 0){
			if(bindings.prag2Binding[node.pragmatics[0].structure.name]){
				binding = bindings.prag2Binding[node.pragmatics[0].structure.name]
			} else {
				return null
			}
		} else {
			return null
		}
		att.isContainer = binding.isContainer
		att.templateBinding = binding
		att.parent = parent
		att.correspondingNetElement = node
		if(att.isContainer){
			def nextNode = findNextNode(node, parent)
			while(nextNode != null){
				if(!usedNodes.contains(nextNode)){
					usedNodes << nextNode
					def nextAtt = attForNode(nextNode, bindings, node, usedNodes)
					if(nextAtt != null) att.children << nextAtt 
			
					nextNode = findNextNode(nextNode, parent)
				} else {
					//escape loop
					nextNode = null 
				}
			}
		}
		return att
	}
	
	static def findNextNode(node, parent){
		switch(node){
			case Transition:
			case Place:
				if(node.out.size() == 1){
					return node.out.target[0]
				}
				break
			case Page:
				return findStartForPage(node)
				break;
		}
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

