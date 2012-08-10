package org.k1s.orn.att

class Choice extends Container{

	
	String toGraphString(i){
		return "<html>$i<br/>Choice<br/>${correspondingNetElement.name.text}<br/></html>"
	}
}
