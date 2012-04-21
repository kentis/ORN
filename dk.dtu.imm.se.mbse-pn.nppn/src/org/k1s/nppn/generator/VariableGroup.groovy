package org.k1s.nppn.generator

class VariableGroup {
	def name
	def groups = []
	def vars = []
	def prags = []
	
	String toString(){
		return "($name, $prags)"
	}
}
