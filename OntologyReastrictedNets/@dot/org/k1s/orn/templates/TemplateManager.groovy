package org.k1s.orn.templates

import javax.management.RuntimeErrorException;

import groovy.lang.Closure;
import groovy.text.GStringTemplateEngine;

class TemplateManager {

	def templateFinder 
	
	def findTemplate(name){
		def template = templateFinder."$name"
		
		if(template instanceof String)
			return template
			
		if(template instanceof Closure)
			return template.call()
			
		throw new RuntimeException("could not find specified template $name".toString())
	}
	
	def runTemplate(template, params){
		def engine = new GStringTemplateEngine()
		def binding = [params: params]
		
		
		def generated = engine.createTemplate(template).make(binding)
		
		return generated.toString()
	}
	
	def findAndRunTemplate(templateName, params){
		return runTemplate(findTemplate(templateName), params)
	}
	
}
