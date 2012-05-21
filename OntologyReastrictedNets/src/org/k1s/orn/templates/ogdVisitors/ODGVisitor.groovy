package org.k1s.orn.templates.ogdVisitors

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
		att.children.each {
			if(it != null) flattenAtt(it, list)	
		}
		return list
	}
}
