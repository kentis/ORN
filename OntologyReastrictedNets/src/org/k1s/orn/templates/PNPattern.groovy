package org.k1s.orn.templates

import org.pnml.tools.epnk.pnmlcoremodel.Arc;



import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;

import com.clarkparsia.pellet.rules.rete.Node;

class  PNPattern {
	
	def name
	def pragmatics
	def type
	
	def minInEdges
	def maxInEdges
	
	def minOutEdges
	def maxOutEdges
	
	def outArcInscription
	
	def patternClosure
	
	def adjacentPatterns
	
	def backlink
	def backlinkTo
	
	def matchNode(node){
		
		if(type != null)
			if(!type.isInstance(node)){
				println "mismatch in types: ${node.class}  -- $type"
				return false
			} 
		
		
		if(node instanceof Arc){
			println "WTF arc?"
		}
		
		if(name != null){
			println "mismatch in names"
			if(name != node.name.text) return false
		}
		
		if(pragmatics != null){
			def pragNode = node
			//if(node instanceof RefPlace) {
			while(pragNode instanceof RefPlace) {
				pragNode = pragNode.ref
			}
			//}
			if(pragNode == null || pragNode.pragmatics == null || pragmatics.size() != pragNode.pragmatics.size()) return false
			def pragsOk = true
			pragNode.pragmatics.each { 
				println "checking prag: ${it}"
				def pragName = it.structure.name
				println "pragName: $pragName"
				if(!pragmatics.contains(pragName)) pragsOk = false
			}
			if(!pragsOk) return false
		}
		
		if(adjacentPatterns != null){
			if(node instanceof Page) return false 
			def adjOk = true
			adjacentPatterns.each { adjPattern -> 
				def hasMatchingAdj = false
				if(node != null && node.out != null){
				node.out.each { adjArc ->
					adjPattern.backlinkTo = node
					if(adjPattern.matchNode(adjArc.target)) hasMatchingAdj = true
				}
				}
				if(!hasMatchingAdj) adjOk = false
			}
			
			
			if(!adjOk) return false
		}
		
		if(backlink != null){
			println "checking backlink"
			def isBackLinked = hasOutLinkTo(node, backlinkTo)
			println "isBackLinked: $isBackLinked"
			if(isBackLinked != backlink) return false
		}
		
		if(minInEdges != null){
			println "checking num in edges"
			if(node.getIn().size() < minInEdges) return false
		}
		
		if(minOutEdges != null){
			println "checking num out edges"
			if(node.getOut().size() < minOutEdges) return false
		}
		
		if(outArcInscription != null){
			if(node instanceof Page) return false
			def retval = false
			node.out.each { outArc ->
				if(outArc.inscription && 
					outArc.inscription.text != null &&  
					outArc.inscription.text.contains(outArcInscription)){
					retval = true
				}
			}
			if(!retval){
				return false
			}
		}
		
		return true
	}
	
	def hasOutLinkTo(from, to){
		def retval = false
		from.out.each{ arc ->
			if(arc.target ==  to) retval = true
		}
		return retval
	}
	
	def match(p, retval = []){
		def nodes
		if(p instanceof PetriNet) nodes = p.page
		else if(p instanceof Page) nodes = p.object
		
		println "matching nodes: $nodes"
		nodes.each { 
			if(!(it instanceof Arc)){
				if(matchNode(it)) retval << it
				if(it instanceof Page){
					match(it, retval)
				}
			}
		}
		
		return retval
	}
}
