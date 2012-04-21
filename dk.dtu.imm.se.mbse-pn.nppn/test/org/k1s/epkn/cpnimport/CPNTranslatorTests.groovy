package org.k1s.epkn.cpnimport;

import org.cpntools.accesscpn.model.impl.PlaceImpl;
import org.junit.Test;

import nppnnets.Place;
import nppnnets.Transition;

import static org.junit.Assert.*;

class CPNTranslatorTests {

	@Test
	void testtransformPlace(){
		Place resplace;
		org.cpntools.accesscpn.model.Place cpnPlace =  org.cpntools.accesscpn.model.impl.ModelFactoryImpl.eINSTANCE.createPlace();
		def name = org.cpntools.accesscpn.model.impl.ModelFactoryImpl.eINSTANCE.createName()
		name.text = "dilldall(*<<channel()>>*)"
		cpnPlace.setName(name)
		cpnPlace.setId "ssdfsdf"
		CPNTranslator translator = new CPNTranslator()
		resplace = translator.transformPlace(cpnPlace)
		
		
		assertEquals("dilldall", resplace.getName().text)
		assertEquals("channel()", resplace.getPragmatics()[0].text)
	}
	
	@Test
	void testtransformTransition(){
		Transition res;
		org.cpntools.accesscpn.model.Transition cpnTrans =  org.cpntools.accesscpn.model.impl.ModelFactoryImpl.eINSTANCE.createTransition();
		def name = org.cpntools.accesscpn.model.impl.ModelFactoryImpl.eINSTANCE.createName()
		name.text = "dilldall(*<<channel()>>*)"
		cpnTrans.setName(name)
		CPNTranslator translator = new CPNTranslator()
		res = translator.transformTansition(cpnTrans)
		
		
		assertEquals("dilldall", res.getName().text)
		assertEquals("channel()", res.getPragmatics()[0].text)
	}
	
}
