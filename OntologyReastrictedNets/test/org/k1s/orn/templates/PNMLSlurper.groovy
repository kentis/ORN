package org.k1s.orn.templates

import orn.OrnFactory;
import orn.OrnPackage;
import orn.impl.OrnFactoryImpl;
import groovy.util.XmlSlurper;

class PNMLSlurper {

	OrnPackage pack = OrnPackage.eINSTANCE
	OrnFactory orn = OrnFactoryImpl.eINSTANCE
	def slurp(file){
		def root = new XmlSlurper().parse(file)
		def rootPage = root.net.page
		println rootPage.name()
	}
	
	
	def handlePage(page) {
		def retVal = orn.createP
	}
}
