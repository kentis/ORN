package org.k1s.orn.templates.ogdVisitors

class ParameterVisitor extends ODGVisitor{

	def visitElement(element){
		element.parameters = [:]
		element.parameters["name"] = element.correspondingNetElement.name.text
		
		if(element.correspondingNetElement.pragmatics && element.correspondingNetElement.pragmatics.size() > 0){
			element.parameters["params"] = element.correspondingNetElement.pragmatics[0].structure.arguments
		} 
	}
	
}
