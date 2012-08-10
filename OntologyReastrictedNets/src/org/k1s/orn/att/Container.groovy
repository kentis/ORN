package org.k1s.orn.att

import org.k1s.orn.templates.AbstractTemplateTree;

import orn.Pragmatics;

abstract class Container implements Block {
	
	List<Block> children = []
	
	public def correspondingNetElement
	public  List pargmatics = []
	public def parent
	public def start
	public def end
	
	//public def bindings
	public List templateBindings = []
	
	//For generation
	def parameters
	def templateText
	
	def getEndAsAtt(bindings){
		def att = new AbstractTemplateTree()
		att.templateBinding = bindings.prag2Binding[end.pragmatics[0].structure.name]
		att.correspondingNetElement = end
		
		return att
	}
}
