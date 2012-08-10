package org.k1s.orn.att

import orn.Pragmatics;

class Transition implements Block{
	
	public def correspondingNetElement
	public  List pargmatics = []
	public def parent
	public def start
	public def end
	
	//public def bindings
	public def templateBindings = []
	
	//For generation
	def parameters
	def templateText
	
	String toGraphString(i){
		return "<html>$i<br/>Transition<br/>${correspondingNetElement.name.text}<br/></html>"
	}
}
