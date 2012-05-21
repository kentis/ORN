package org.k1s.orn.templates.ogdVisitors

import org.k1s.orn.templates.Conditionals;
import org.pnml.tools.epnk.pnmlcoremodel.Page;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

class TemplateVisitor extends ODGVisitor{
	def bindings
	def visitElement(element){
		
		println "visiting $element"
		if(element.templateBinding != null){
			def template = element.templateBinding.template
			if(!(element.correspondingNetElement instanceof Page) && element.correspondingNetElement.pragmatics[0].structure.name == 'Cond'){
				println "making text for cond!"
				
				element.templateText = Conditionals.translatePrags(element.correspondingNetElement.pragmatics[0], bindings)
			} else 	if(template instanceof String){
				def tmpl = new File(template).text
				
				SimpleTemplateEngine engine = new SimpleTemplateEngine()
				Template simpleTemplate = engine.createTemplate(tmpl)
				
				def templateText = simpleTemplate.make(element.parameters)
				element.templateText = templateText.toString()
			}
		} else { //passthrough elements
			element.templateText = "%%yield%%"
		}
	}
}
