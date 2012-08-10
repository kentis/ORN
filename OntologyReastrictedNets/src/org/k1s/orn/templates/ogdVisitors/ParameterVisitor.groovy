package org.k1s.orn.templates.ogdVisitors;

import orn.Place;


import org.k1s.orn.att.AbstractTemplateTree2;
import org.k1s.orn.att.Container;
import org.k1s.orn.att.External;


import org.k1s.orn.templates.Conditionals;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
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
						//println "TRANSLATING EXPR: element.correspondingNetElement.pragmatics[0]"
						element.parameters["cond"] = Conditionals.translateExpr(element.correspondingNetElement.pragmatics[0], bindings)
						//println "TO: ${element.parameters["cond"]}"
					}
				}
			}
			if(element instanceof Container){
				//check for end condition
				if(element.end && element.end.pragmatics && element.end.pragmatics.size() > 0){
					element.parameters["end_params_raw"] = element.end.pragmatics[0].structure.arguments
					element.parameters["end_params"] = element.end.pragmatics[0].structure.arguments.split(",")
					element.parameters["end_params"]. each {
						if(it.startsWith('cond: ')){
							println "TRANSLATING EXPR: ${element.correspondingNetElement.pragmatics[0]}"
							element.parameters["end_cond"] = Conditionals.translateExpr(element.end.pragmatics[0], bindings)
							//println "TO: ${element.parameters["cond"]}"
						}
					}
				}
				//check for LCVs and fields
				if(element.correspondingNetElement instanceof Page){
					def lcvs = [:]
					def fields = []
					element.correspondingNetElement.object.each{
						if(it instanceof Place && it.pragmatics.size() >0 && it.pragmatics[0].structure.name == "LCV"){
							lcvs[it.name.text.trim()] = it.getToolspecific()[0]
						} else if(it instanceof Place){
							fields << it.name.text.trim()
						}
					}
					element.parameters["lcvs"] = lcvs
					element.parameters["fields"] = fields
				}
			}
			
			//Check for LCV preconditions
			if(element instanceof External) println "NETELEM: ${element.correspondingNetElement}"
			if(element instanceof External) println "STARTELEM: ${element.start}"
			if(element instanceof External){
				
				//preconditions only makes sense for transitions
				def pre_conds = []
				element.startNode.in.source.each{
					while(it instanceof RefPlace){ it = it.ref }
					if(it.pragmatics.size() >0 && it.pragmatics[0].structure.name == "LCV"){
						pre_conds << it.name.text.trim()
					}
				}
				element.parameters["pre_conds"] = pre_conds
				
				def endNode = element.children.size() > 0 ? ((External)element).children.last().correspondingNetElement : element.startNode
				def post_conds = []
				endNode.out.target.each{
					while(it instanceof RefPlace){ it = it.ref }
					if(it.pragmatics.size() >0 && it.pragmatics[0].structure.name == "LCV"){
						post_conds << it.name.text.trim()
					}
				}
				
				element.parameters["post_conds"] = post_conds
				
			}
			
			//Set value for the parameter on the outgoing arc on the controlflow path
			if( (element.correspondingNetElement  instanceof orn.Place || element.correspondingNetElement  instanceof orn.Transition) 
				&& element.correspondingNetElement.out.size() > 0){
				
				def outArc = findCFOutArc(element.correspondingNetElement)
				element.parameters["controlflow_outgoing_arc"] = outArc
			}
			
		}
	}
	
	def findCFOutArc(node){
		def retArc
		if(node instanceof orn.Transition)
			retArc = node.out.find{ AbstractTemplateTree2.isControlFlow( it.target ) }
		else if(node instanceof orn.Place){
			retArc = node.out.find{ 
				retArc = it.target.out.find{ targetOut ->  AbstractTemplateTree2.isControlFlow( targetOut.target ) }
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
