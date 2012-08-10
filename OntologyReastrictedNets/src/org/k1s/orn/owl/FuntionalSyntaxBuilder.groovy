package org.k1s.orn.owl



import orn.RefPlace;

import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;




import org.pnml.tools.epnk.pnmlcoremodel.Transition;
//import org.codehaus.groovy.tools.ast.TranformTestHelper;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;

import orn.RefPlace;

class FuntionalSyntaxBuilder {

	
	
	
	def page2FunctionalSyntax(Page page, StringBuilder sb, isTop = false) {
		sb.append("\n")
		sb.append("Declaration( NamedIndividual( :${getNameForPage(page)} ) )").append("\n")
		sb.append("ClassAssertion( cpn:Page :${getNameForPage(page)} )").append("\n")
		//sb.append("\t").append("Facts: isTop \"${isTop}\"^^xsd:boolean").append("\n")
		sb.append "\n"
		
		if(page.pragmatics != null){
			page.pragmatics.each{ prag ->
				pragmatic2FunctionalSyntax(prag,page,sb)
			}
		}
		
		page.getObject().each { obj -> 
			println obj
			switch(obj){
				case Page:
					page2FunctionalSyntax(obj, sb)
					break
				case Place:
					place2FunctionalSyntax(obj, page, sb)
					break
				case Transition:
					transition2FunctionalSyntax(obj, page, sb)
					break
				case Arc:
					arc2FunctionalSyntax(obj, page, sb)
					break
				case RefPlace:
				case org.pnml.tools.epnk.pnmlcoremodel.RefPlace:
					refplace2FunctionalSyntax(obj, page, sb)
				break
			}
		}
	}
	
	def getNameForPage(page){
		if(page.name) return page.name.text.replace(" ", "_").replace("\n", "_")+ "_Page"
		if(page.id) return page.id
		
		return "Page_${page.hashCode()}"
	}
	
	def getNameForNode(Place node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_")+ "_Place"
		if(node.id) return node.id
		
		return "Place_${node.hashCode()}"
	}
	
	def getNameForNode(Transition node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_")+ "_Transisiton"
		if(node.id) return node.id
		
		return "Transition_${node.hashCode()}"
	}
	
	def getNameForNode(RefPlace node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_") + "_RefPlace_${node.hashCode()}"
		if(node.id) return node.id
		
		return "RefPlace_${node.hashCode()}"
	}
	
	def getNameForNode(org.pnml.tools.epnk.pnmlcoremodel.RefPlace node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_") + "_RefPlace_${node.hashCode()}"
		if(node.id) return node.id
		
		return "Place_${node.hashCode()}"
	}
	
	def getNameForNode(orn.Page node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_") + "_Page"
		if(node.id) return node.id
		
		return "Page_${node.hashCode()}"
	}
	
	def getNameForNode(Arc node){
		if(node.name != null) return node.name.text.replace(" ", "_").replace("\n", "_")
		if(node.id) return node.id
		
		return "Arc_${node.hashCode()}"
	}
	
	def place2FunctionalSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Declaration( NamedIndividual( :${getNameForNode(obj)}))").append("\n")
		sb.append("ClassAssertion( cpn:Place :${getNameForNode(obj)}) ").append("\n")
		sb.append "\n"
		
		if(obj.pragmatics != null){
			obj.pragmatics.each{ prag ->
				pragmatic2FunctionalSyntax(prag,obj,sb)
			}
		}
	}
	
	
	def refplace2FunctionalSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Declaration( NamedIndividual( :${getNameForNode(obj)}))").append("\n")
		sb.append("ClassAssertion( cpn:Place :${getNameForNode(obj)}) ").append("\n")
		sb.append "\n"
		
		def ref = obj.ref
		while(ref instanceof org.pnml.tools.epnk.pnmlcoremodel.RefPlace){
			ref = ref.ref
		}
		if(ref.pragmatics != null){
			ref.pragmatics.each{ prag ->
				pragmatic2FunctionalSyntax(prag,obj,sb)
			}
		}
	}
	
	def transition2FunctionalSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Declaration( NamedIndividual( :${getNameForNode(obj)}))").append("\n")
		sb.append("ClassAssertion( cpn:Transition :${getNameForNode(obj)}) ").append("\n")
		sb.append "\n"
		
		if(obj.pragmatics != null){
			obj.pragmatics.each{ prag ->
				pragmatic2FunctionalSyntax(prag,obj,sb)
			}
		}
	}
	
	def arc2FunctionalSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Declaration( NamedIndividual( :${getNameForNode(obj)}))").append("\n")
		sb.append("ClassAssertion( cpn:Arc :${getNameForNode(obj)}) ").append("\n")
		
		sb.append("ObjectPropertyAssertion( cpn:source :${getNameForNode(obj)} :${getNameForNode(obj.source)} )").append("\n")
		sb.append("ObjectPropertyAssertion( cpn:target :${getNameForNode(obj)} :${getNameForNode(obj.target)} )").append("\n")
		
		//add in/out for connected objects
		sb.append("ObjectPropertyAssertion( :out  :${getNameForNode(obj.source)} :${getNameForNode(obj)} )").append("\n")
		sb.append("ObjectPropertyAssertion( :in  :${getNameForNode(obj.target)} :${getNameForNode(obj)})").append("\n")
		
		sb.append "\n"
	}
	
	def pragmatic2FunctionalSyntax(prag, containingObj, sb){
		sb.append '\n'
		
		sb.append("Declaration( NamedIndividual( :${getNameForPrag(prag)}))").append("\n")
		sb.append("ClassAssertion( :${prag.structure.name} :${getNameForPrag(prag)}  ) ").append("\n")
		
		sb.append("ObjectPropertyAssertion( :belongsTo :${getNameForPrag(prag)} :${getNameForNode(containingObj)} )").append("\n")
		sb.append("ObjectPropertyAssertion( :hasPragmatic :${getNameForNode(containingObj)} :${getNameForPrag(prag)})")
		sb.append '\n'
	}
	
	def getNameForPrag(prag){
		return "${prag.structure?.name}_${System.identityHashCode(prag)}"
	}
}
