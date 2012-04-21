package org.k1s.orn.templates

import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import org.pnml.tools.epnk.pnmlcoremodel.Place;

class Generator {
	
	def templateManager
	
	def generate(pn, generatorSpec){
		def topPage =  findTopPage(pn)
		def external = findExternal(topPage)
		
		def generatorImpl = new LinearGenerator(pn)
		generatorImpl.templateManager = templateManager
		
		return generatorImpl.generateNode(external)
	}
	
	def findExternal(page){
		def extern
		page.getObject().each{ node ->
			if((node instanceof Place || node instanceof Transition ) && node.getPragmatics() != null){
				node.getPragmatics().each{ prag ->
					if(prag.getText().startsWith("External")){
						if(extern == null){
							extern = node
						} else {
							throw new RuntimeException("More than one extenal defined for module")
						}
					}
				}
			}
		}
		return extern;
	}
	
	def findTopPage(pn){
		return pn.getPage().get(0)
	}
}
