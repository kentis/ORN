package org.k1s.orn.test

import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelFactory;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.serialisation.PNMLFactory;

class ePNKBuilder {
	
	def pn
	def currentPage
	def factory
	
	def labelsMap
	
	ePNKBuilder(factory, labelsMap = [:]){
		println "setting factory: $factory"
		this.factory = factory
		this.labelsMap = labelsMap
		pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
	}
	
	def make(closure){
		//def builder = new ePNKBuilder()
		closure.setDelegate(this)
		closure.call()
		
		return pn
	}
	
	
	def methodMissing(String methodName, args) {
		def obj
		switch(methodName.toLowerCase()){
			case "page":
					def p = createPage(args[0])
					if(currentPage == null){
						pn.page << p
						currentPage = p
						if(args.size() > 1){
							args[1].call()
						}
						currentPage = null
					} else {
						currentPage.object << p
						def prevPage = currentPage
						currentPage = p
						if(args.size() > 1){
							args[1].call()
						}
						currentPage = prevPage
					}
					obj = p
				break;
			case "place":
					def p = createPlace(args[0])
					currentPage.object << p
					obj = p
				break;
			case "transition":
					def t = createTransition(args[0])
					currentPage.object << t
					obj = t
				break
			case "refplace":
				println args
					def rp = createRefPlace(args)
					currentPage.object << rp
					obj = rp
				break
			case "arc":
				println "arc($args)"
				obj = createArc(args)
				break
			case "epnkbuilder":
				break
			default:
				throw new RuntimeException("Unknown node type: $methodName")
		}
		return obj
	}
	
	def createRefPlace(args){
		RefPlace ref = PnmlcoremodelFactory.eINSTANCE.createRefPlace()
		ref.setRef args.ref
		ref.setName(getName(args.name == null ? "page" : args.name))
		return ref
	}
	
	def createPage(args){
		//println args
		Page p = factory.createPage()
		p.setName(getName(args.name == null ? "page" : args.name))
		if(args.labels != null){
			setLabels(p, args.labels)
		}
		return p
	}
	
	def createPlace(args){
		def place = factory.createPlace()
		place.setName getName(args.name == null ? "place" : args.name)
		if(args.labels != null){
			setLabels(place, args.labels)
		}
		return place
	}
	
	def createTransition(args){
		def trans = factory.createTransition()
		trans.setName getName(args.name == null ? "trans" : args.name)
		if(args.labels != null){
			setLabels(trans, args.labels)
		}
		return trans
	}
	
	def createArc(args){
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		if(args.size() == 3){
			arc.setName getName(args[2])
		} else {
			arc.setName getName("arc1")
		}
		arc.setSource args[0]
		arc.setTarget args[1]
		
		args[0].out.add arc
		args[1].in.add arc
		return arc
	}
	
	def setLabels(node, labels){
		println "setting labels: ${labels.keySet()}"
		labels.keySet().each { labelName ->
			println "setting label: $labelName" 
			if(node.hasProperty(labelName) && labelsMap.containsKey(labelName)){
				def label = labelsMap[labelName].newInstance()
				label.setText(labels[labelName])
				label.setStructure(label.parse(label.text))
				node."$labelName" << label
			}
		}
		
	}
	
	def getName(text){
		Name n =  PnmlcoremodelFactory.eINSTANCE.createName()
		n.setText text
		return n
	}
}