package org.k1s.orn.att

class External extends Container{
	
	def startNode
	
	String toGraphString(i){
		return "<html>$i<br/>External<br/>${correspondingNetElement.name.text}<br/>${correspondingNetElement.pragmatics.structure.name}</html>"
	}
}
