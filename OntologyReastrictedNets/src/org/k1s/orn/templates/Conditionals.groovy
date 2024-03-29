

package org.k1s.orn.templates

import groovy.lang.GroovyShell;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

class Conditionals {
	
	def conds = []
	
	static def translatePrags(pragmatic, bindings){
		def retval = ""
		SimpleTemplateEngine engine = new SimpleTemplateEngine()
		
		def condStr = null
		println "prag name: ${pragmatic.structure.name}"
		println "prag args: ${pragmatic.structure.arguments}"
		println "prag text: ${pragmatic.text}"
		if(pragmatic.structure.name == "Id"){
			println pragmatic.structure.arguments
			def args = pragmatic.structure.arguments
			def map = new GroovyShell().evaluate("return [$args]")
			println map
			condStr = map.cond
		} else if(pragmatic.structure.name == "Cond"){
			def args = pragmatic.structure.arguments
			println "prag name: ${pragmatic.structure.name}"
			println "prag args: ${pragmatic.structure.arguments}"
			println "prag text: ${pragmatic.text}"
			println "args = '$args'"
			condStr = new GroovyShell().evaluate("return $args")
		} 
		println condStr
		def conds = parse(condStr)
		println conds.conds
		/*def iftmpl = '''
			${first ? '' : 'else '}if(${cond.e == 't'? t : cond.e}){ ${cond.p} }
		'''
		def trueTmpl = '''true'''
		
		def exprTmpl = '''<%
			def e = cond.e.split(' ')
			def verb = null
			switch(e[0]){
				case 'eq':
				 verb = "=="
				 break
				case 'neq': 
				 verb = "!="
				 break
				case 't':
				 verb = ''
				 e = [verb, 'true']
				 break
				default:
				 verb = ''
				 e = [verb, e[0]]
				 break
			} 
			for(def i = 1; i < e.size(); i++){%> ${e[i]} ${i < (e.size() -1) ? verb : ''}<%}%>'''  
		*/
		println "BB: ${bindings.bindings}"
		def exprTmpl = new File(bindings.bindings.EXPR.template).text
		def condTmpl = new File(bindings.bindings.COND.template).text
		def trueTmpl = new File(bindings.bindings.TRUE.template).text
		def first = true
		conds.conds.each { 
			
			Template exprTemplate = engine.createTemplate(exprTmpl)
			def exprText = exprTemplate.make([cond: it, first: first, t: trueTmpl]).toString()

			Template simpleTemplate = engine.createTemplate(condTmpl)
			def templateText = simpleTemplate.make([cond: it, first: first, t: trueTmpl, e: exprText]).toString()
			retval += templateText
			first = false
		}
		return retval
	}
	
	static def translateExpr(pragmatic, bindings){
		def args = pragmatic.structure.arguments
		def map = new GroovyShell().evaluate("return [$args]")
		println map
		def exprStr = map.cond
		def exprTmpl = new File(bindings.bindings.STMT.template).text
		
		SimpleTemplateEngine engine = new SimpleTemplateEngine()
		
		
		def trueTmpl = new File(bindings.bindings.TRUE.template).text
		Template exprTemplate = engine.createTemplate(exprTmpl)
		
		def exprText = exprTemplate.make([stmt: parseExpr(exprStr), t: trueTmpl]).toString()
		
		println "exprText: ${exprText}"
		return exprText
		
	}
	
	static def parseExpr(exprStr){
		println "-------------------------------------"
		def exprs = []
		def currExpr
		def parentExprs = []
		def currToken = ""
		exprStr.each{
			if(it == '('){
				if(currExpr != null) parentExprs.push(currExpr)
				currExpr = new Expr()
				
			}else if(it == ')'){
				currExpr.args << currToken
				currToken = ""
				def finishedExpr = currExpr
				if(parentExprs.size() > 0){
					currExpr = parentExprs.pop()
					currExpr.args << finishedExpr
				}else{
				 	exprs << currExpr
				}
			}else if(it == ' '){
				if(currExpr.oper == null){
					currExpr.oper = currToken
				}else {
					if(currToken.trim().size() > 0)
					currExpr.args << currToken
				}
				currToken = ""
			} else {
			    currToken += it
			}
		}
		
		println exprs[0]
		println "-------------------------------------"
		return exprs[0]
	}
	
	
	static final int COND = 0
	static final int EXPR = 1
	
	static def parse(cond){
		def conds = new Conditionals()
		def currCond = null
		def currToken = ""
		def state = COND
		cond.each{
			if(it == '(' && currCond == null){
				//new condition
				currCond = new Conditional()
			}else if(it == '(') {
				state = EXPR
			} else if(it == ')'){
				if(state == EXPR){
					currCond.e = currToken
					currToken = ""
					state = COND
				} else 	if(state == COND){
					if(currToken.size() > 0){
						currCond.p = currToken
					}
					conds.conds << currCond
					currToken = ""
					currCond = null
				}
			}else if(it == ' ' && state == COND){
			    if(currCond.e == null) currCond.e = currToken
				else currCond.p = currToken
				
				currToken = ""
			} else {
			    currToken += it
			}
			
		}
		return conds
	}
}
class Conditional{
	def e 
	def p 
	
	String toString(){
		"(e: $e, p: $p)"
	}
}

class Expr {
	def oper
	def args = []
	
	String toString(){
		"|${oper}\n$args|"
	}
}
