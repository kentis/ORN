package org.k1s.orn.att

class Principal extends Container{

	
	String toGraphString(i){
		return "<html>$i<br>Principal</br>${correspondingNetElement.name.text}<br/></html>"
	}
}
