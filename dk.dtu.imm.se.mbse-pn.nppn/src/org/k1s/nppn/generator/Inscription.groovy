package org.k1s.nppn.generator

class Inscription {

	def vars = []
	/*
	static Inscription parseVar(def var, inheritedPrags = []){
		Inscription retval = new Inscription()
		def variable = null
		
		if(isGroup(var)){ //this is a set of variables
			
			def groupPrag = []
			//first remove groupPragamtic
			if(var.endsWith(">>")){
				groupPrag << Pragmatic.prag(var.substring(var.lastIndexOf('<<')))
				var = var.substring(0, var.lastIndexOf('<<'))
			}
			
			println "${var.indexOf('(')}  ${var.lastIndexOf(')')}"
			println var
			def vars = var.substring(var.indexOf("(")+1, var.lastIndexOf(")"))
			def idx = 0
			def hasNext = true
			while(hasNext){
				def currVar = vars.substring(idx)
				if(isGroup(currVar)){
					currVar = currVar.substring( 0, currVar.lastIndexOf(')'))
				} else {
					currVar = currVar.substring(0, currVar.lastIndexOf(','))
				}
				retval.vars << parseVar(currVar, groupPrag).vars
				idx = currVar.indexOf(",")
				if(idx < 0) hasNext = false
			}
			retval.vars = retval.vars.flatten()
		} else {
			println var
			if(var.indexOf("<<") > -1){
				def prag = Pragmatic.prag(var)
				variable = new Variable(name:var.substring(0,var.indexOf("<<")), prags: [prag]) 
			} else {
				variable = new Variable(name:var)
			}
			variable.prags += inheritedPrags
			println variable.prags
			retval.vars << variable
		}
		return retval
	}
	*/
	static def isGroup(String var){
		boolean inPrag
		def lastChar
		def thisChar
		def isGr = false
		var.chars.each{
			if(inPrag){
				if(lastChar == '>' && it == '>') inPrag = false
			} else {
				if(lastChar == '<' && it == '<') inPrag = true
				if(it == '(') isGr = true 
			}
			lastChar = it
		}
		
		return isGr
	}
}
