package org.k1s.orn.owl


import org.pnml.tools.epnk.pnmlcoremodel.Transition;
//import org.codehaus.groovy.tools.ast.TranformTestHelper;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;

class ManchesterSyntaxBuilder {

	
	String newIndividual(name, types, facts, sb){
		sb.append("Individual: $name")
		types.each {
			sb.append("\t").append("Types: $it")		
		}
		
		facts.each { 
			sb.append("\t").append("Facts: $it")
		}
	}
	
	String pn2ManchesterSyntax(PetriNet pn){
		def sb = new StringBuilder()
		sb.append(ORNConstants.OWL_TEMPLATE).append("\n");
		
		Page topPage = pn.getPage().get(0);
		
		page2ManchesterSyntax(topPage, sb, true);
		
		return sb.toString();
	}
	
	def page2ManchesterSyntax(Page page, StringBuilder sb, isTop = false) {
		sb.append("\n")
		sb.append("Individual: ${page.name.text}").append("\n")
		sb.append("\t").append("Types: Page").append("\n")
		sb.append("\t").append("Facts: isTop \"${isTop}\"^^xsd:boolean").append("\n")
		
		page.getObject().each { obj -> 
			println obj
			switch(obj){
				case Page:
					page2ManchesterSyntax(obj, sb)
					break
				case Place:
					place2ManchesterSyntax(obj, page, sb)
					break
				case Transition:
					transition2ManchesterSyntax(obj, page, sb)
					break
				case Arc:
					arc2ManchesterSyntax(obj, page, sb)
					break
			}
		}
	}
	
	
	def place2ManchesterSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Individual: ${obj.name.text}").append("\n")
		sb.append("\t").append("Types: Place").append("\n")
		
	}
	
	def transition2ManchesterSyntax(obj, page, sb){
		sb.append("\n")
		sb.append("Individual: ${obj.name.text}").append("\n")
		sb.append("\t").append("Types: Transition").append("\n")
	}
	
	def arc2ManchesterSyntax(arc, page, sb){
		sb.append("\n")
		sb.append("Individual: ${arc.name.text}").append("\n")
		sb.append("\t").append("Types: Arc").append("\n")
		sb.append("\t").append("Fact: Arc").append("\n")
		sb.append("\t").append("Types: Arc").append("\n")
	}
	
}
