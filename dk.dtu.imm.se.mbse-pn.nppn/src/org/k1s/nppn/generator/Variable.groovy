package org.k1s.nppn.generator

class Variable {
	
	def name
	def prags = []
	
	String toString(){
		return "(name: $name, prags: ${prags})"
	}
}
