package org.k1s.orn.templates


class ProtcolRunner {

	def run(String code, String starter){
		return new GroovyShell().evaluate("${code}\n${starter}")
	}
	
}
