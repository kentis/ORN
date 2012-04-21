package org.k1s.orn.templates.ogdVisitors

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

class TemplateVisitor extends ODGVisitor{

	def visitElement(element){
		def template = element.templateBinding.template
		if(template instanceof String){
			def tmpl = new File(template).text
			
			SimpleTemplateEngine engine = new SimpleTemplateEngine()
			Template simpleTemplate = engine.createTemplate(tmpl)
			def templateText = simpleTemplate.make(element.parameters)
			element.templateText = templateText.toString()
		}
	}
	
}
