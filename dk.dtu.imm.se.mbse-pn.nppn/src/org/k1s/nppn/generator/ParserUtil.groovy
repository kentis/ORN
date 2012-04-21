package org.k1s.nppn.generator
import nppnnets.Pragmatics;

import org.cpntools.accesscpn.model.Transition;

import groovy.text.GStringTemplateEngine;
class ParserUtil {

	static plattform = new GroovyPlattform()
	
	static config = new GroovyKCConfig()
	
	static boolean debug = true
	
	static def getCode(String segmentName, binding, currentBindings = null){
		def engine = new GStringTemplateEngine()
		binding.VARS = currentBindings
		binding.parserUtil = new ParserUtil()
		//println "binding $binding"
		def prop = config.getProperties()[segmentName]
		if(prop == null) prop = plattform.getProperties()[segmentName]
		
		def template = engine.createTemplate(prop).make(binding)
		println segmentName
		return template.toString()
	}
	
	static def getComment(comment){
		def engine = new GStringTemplateEngine()
		def template = engine.createTemplate(plattform.COMMENT).make([comment: comment.toString()])
		
		return template.toString()
	}
	
	static def findCodeFor(Transition trans, Pragmatics prag, currentBindings = null ){
		//println "FIND CODE FOR"
		def prop = config.getProperties()[prag.arguments['name'].toUpperCase()]
		def propVars = config.getProperties()["${prag.arguments['name'].toUpperCase()}_VARS"]
		//println "PROP1 $prop"
		if(prop == null) prop = plattform.getProperties()[prag.arguments['name'].toUpperCase()]
		
		if(propVars == null) propVars = plattform.getProperties()["${prag.arguments['name'].toUpperCase()}_VARS"]
		
		//println "PROP2 $prop"
		
		if(prop == null) return ""
		
		//println "PROP3 $prop"
		
		def engine = new GStringTemplateEngine()
		def bindings = prag.getArguments()
		bindings.VARS = currentBindings
		def template = engine.createTemplate(prop).make(bindings)
		
		println "PROPVARS: ${propVars}"
		propVars?.each{
			currentBindings << it
		}
		
		return template.toString()
	}
	
	static def initializeVar(var, bindings){
		def retval = ""
		
		if(var instanceof List){
			var.each{ v ->
				println "BINDINGS: $bindings"
				if(v instanceof String && !bindings?.contains(v)){
					retval +=  "def ${v}\n"
					bindings << v
				} else if(!bindings?.contains(v.name)){
					retval +=  "def ${v.name}\n"
					bindings << v.name
				}
			}
		}else  if(var instanceof String && !bindings?.contains(var)){
			retval =  "def ${var}"
			bindings << var
		} else if(!bindings?.contains(var.name)){
			retval =  "def ${var.name}"
			bindings << var.name
		}
		return retval
	}
	static def debug(str){
		if(debug){
			println "--------------------------"
			println str
			println "--------------------------"
		}
	}
	
}
