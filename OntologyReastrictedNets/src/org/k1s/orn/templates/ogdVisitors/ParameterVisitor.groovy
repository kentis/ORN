package org.k1s.orn.templates.ogdVisitors

class ParameterVisitor extends ODGVisitor{

	def visitElement(element){
		element.parameters = [:]
		element.parameters["name"] = element.correspondingNetElement.name.text
	}
	
}
