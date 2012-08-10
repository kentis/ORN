package org.k1s.orn.att

class Loop extends Container{

	
	String toGraphString(i){
		return "<html>$i<br/>Loop<br/>${correspondingNetElement.name.text}<br/></html>"
	}
}
