package org.k1s.nppn.generator

class ProtcolRunner {

	def run(String code, String starter){
		return new GroovyShell().evaluate("${code}\n${starter}")
	}
	
}
