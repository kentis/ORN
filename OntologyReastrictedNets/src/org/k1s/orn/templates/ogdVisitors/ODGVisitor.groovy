package org.k1s.orn.templates.ogdVisitors

abstract class ODGVisitor {

	abstract def visitElement(element);
	
	static void visitATT(att){
		def visitors = [new ParameterVisitor(), new PrefixVisitor(), new TemplateVisitor()]
		
		def atts = flattenAtt(att, [])
		
		atts.each{ node ->
			visitors.each{ visitor ->
				visitor.visitElement(node)	
			}	
		}
	}
	
	static def flattenAtt(att, list){
		println att
		list << att
		att.children.each { 
			flattenAtt(it, list)	
		}
		return list
	}
}
