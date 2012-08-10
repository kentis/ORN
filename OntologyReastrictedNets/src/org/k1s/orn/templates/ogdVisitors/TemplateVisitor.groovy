package org.k1s.orn.templates.ogdVisitors

import org.k1s.orn.templates.Conditionals;
import org.pnml.tools.epnk.pnmlcoremodel.Page;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

class TemplateVisitor extends ODGVisitor{
	def bindings
	def visitElement(element){
		
		println "visiting $element"
		if(element.templateBindings != null){
			element.templateBindings.each{ binding ->
				if(binding == null) throw new Exception("Null-binding for ${element.correspondingNetElement.name.text}: ${element.templateBindings}")
				def template = binding.template
				if(!(element.correspondingNetElement instanceof Page) && element.correspondingNetElement.pragmatics[0].structure.name == 'Cond'){
					println "making text for cond!"
					
					element.templateText = Conditionals.translatePrags(element.correspondingNetElement.pragmatics[0], bindings)
				} else 	if(template instanceof String){
					def tmpl = new File(template).text
					try{
						SimpleTemplateEngine engine = new SimpleTemplateEngine()
						Template simpleTemplate = engine.createTemplate(tmpl)
					
						def templateText = simpleTemplate.make(element.parameters)
						element.templateText = templateText.toString()
					} catch(Exception ex){
						throw new Exception("Error running template $template",ex)
					}
				}
				//add outgoing token
	
				SimpleTemplateEngine engine = new SimpleTemplateEngine()
				
				def tokenTemplate = bindings.bindings['TOKEN']
				def tokenText = engine.createTemplate(new File(tokenTemplate.template)).make(element.parameters)
				
				if(tokenText == null || tokenText == 'null') throw new RuntimeException("Token text is null")
				if(element.templateText == null || element.templateText == 'null'){
					 throw new RuntimeException("Element text is null")
				}
				if(element.templateText != null && element.templateText.contains("%%yeild_token%%")){
					element.templateText = element.templateText.replace("%%yield_token%%", tokenText)
				} else {
					element.templateText =  element.templateText + "\n $tokenText"
				}
		}
		} else { //passthrough elements
			element.templateText = "%%yield%%"
		}
	}
}
