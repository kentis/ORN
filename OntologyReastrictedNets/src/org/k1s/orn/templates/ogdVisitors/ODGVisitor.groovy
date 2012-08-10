package org.k1s.orn.templates.ogdVisitors

import org.k1s.orn.att.Container;
import org.k1s.orn.templates.AbstractTemplateTree;

abstract class ODGVisitor {

	abstract def visitElement(element);
	
	static void visitATT(att, bindings){
		def visitors = [new ParameterVisitor(bindings: bindings), 
						new PrefixVisitor(bindings: bindings), 
						new TemplateVisitor(bindings: bindings)]
		
		def atts = flattenAtt(att, [])
		
		atts.each{ node ->
			visitors.each{ visitor ->
				visitor.visitElement(node)	
			}	
		}
	}
	
	static def flattenAtt(att, list){
		println "${att}    ${att.correspondingNetElement}"
		list << att
		if(att instanceof AbstractTemplateTree || att instanceof Container){
			att.children.each {
				if(it != null) flattenAtt(it, list)	
			}
		}
		return list
	}
}
