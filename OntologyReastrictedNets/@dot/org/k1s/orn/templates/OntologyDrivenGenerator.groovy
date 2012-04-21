package org.k1s.orn.templates

import org.k1s.orn.templates.ogdVisitors.ODGVisitor;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;

class OntologyDrivenGenerator {
	def templateManager
	
	//1. Add extra pragmatics
	//2. Create abstract template tree (ATT)
	//3. visit 3 to build up code
	//4. make att into files
	//5. Profit
	
	
	def generate(PetriNet pn, bindings){
		//def rootPage = pn.page[0]
		//1. (skip for now)
		
		
		//2. 
		def att = AbstractTemplateTree.attForNet(pn, bindings)
		println att

		//3.
		att.children.each{
			ODGVisitor.visitATT(it)
		}
		println "created $att"
		println "with children ${att.children}"
		
		//4.
		def files = []
		att.children.each{
			files << attToFile(it) 
		}
		return files
	}
	
	
	def attToFile(att){
		def file
		if(att.templateText?.contains("%%yield%%")){
			StringBuilder childrenText = new StringBuilder()
			att.children.each{ child ->
				childrenText.append attToFile(child)	
			}
			file = att.templateText.replaceAll("%%yield%%", childrenText.toString())
		} else {
			file = att.templateText
		}
		return file
	}
	
}
