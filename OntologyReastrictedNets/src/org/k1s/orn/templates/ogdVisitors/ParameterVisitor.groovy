package org.k1s.orn.templates.ogdVisitors
import org.k1s.orn.templates.Conditionals;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;



class ParameterVisitor extends ODGVisitor{
	
	def bindings
	
	def visitElement(element){
		element.parameters = [:]
		if(element.correspondingNetElement){
			element.parameters["name"] = element.correspondingNetElement.name.text
			element.parameters["node"] = element.correspondingNetElement
			
			element.parameters["outMsg"] = getOutMsg(element)
			if(element.correspondingNetElement.pragmatics && element.correspondingNetElement.pragmatics.size() > 0){
				element.parameters["params_raw"] = element.correspondingNetElement.pragmatics[0].structure.arguments
				element.parameters["params"] = element.correspondingNetElement.pragmatics[0].structure.arguments.split(",")
				element.parameters["params"]. each {
					if(it.startsWith('cond: ')){
						println "TRANSLATING EXPR: element.correspondingNetElement.pragmatics[0]"
						element.parameters["cond"] = Conditionals.translateExpr(element.correspondingNetElement.pragmatics[0], bindings)
						println "TO: ${element.parameters["cond"]}"
					}
				}
			}
		}
	}
	
	def getOutMsg(element){
		def msgInscription = []
		if(element.correspondingNetElement instanceof Transition){
			element.correspondingNetElement.out.each {
				if(it.target instanceof RefPlace){
					
					def place = it.target.ref
					while(place instanceof RefPlace) place = place.ref
					println "*********************************'"
					println place
					println place.pragmatics
					println "*********************************'"
					if(place.pragmatics.size() == 1 && place.pragmatics[0].structure.name == "Channel")
						msgInscription << it.inscription.text
				}
			}
			if(msgInscription.size() == 1) msgInscription = msgInscription[0]
		}
		return msgInscription
	}
}
