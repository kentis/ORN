package org.k1s.orn;

import java.util.List;

import org.junit.Test;
import org.k1s.orn.test.ORNTestUtils;

import orn.Page;
import orn.impl.TransitionImpl

import static org.junit.Assert.*;

class ORNImporterTests {

//	
//	@Test
//	void testWS(){
//		def fileName = "/home/kent/projects/ws/websocket/descriptive-fmooods-forte-12/model/WSProtocol.cpn"
//		ORNImporter translator = new ORNImporter();
//		List<Page> pages =  translator.translate(fileName);
//		
//		assertEquals( 1, pages.size())
//		
//		assertTrue pages[0].object.size() > 1 
//	}
	
	@Test
	void tesgetPrag(){
		String elemName  = "Dill <<Dall()>>"
		def res = new ORNImporter().getPrags(elemName)[0]
		assertTrue res.contains("Dall")
		assertFalse res.contains("Dill")
		assertFalse res.contains("<<")
		assertFalse res.contains(">")
		
	}
	
	@Test
	void tesgetPrags(){
		String elemName  = "Dill <<Dall()>>\n<<EttellerAnnet()>>"
		def res = new ORNImporter().getPrags(elemName)
		
		assertTrue res instanceof List

		def first = res[0]
		assertTrue first.contains("Dall")
		assertFalse first.contains("Dill")
		assertFalse first.contains("<<")
		assertFalse first.contains(">")

		def second = res[1]
		assertNotNull second
		assertEquals "EttellerAnnet()", second
		

	}
	
	@Test
	void testsetPragmatics(){
		String elemName  = "Dill <<Dall()>>\n<<EttellerAnnet()>>"
		def elem = new TransitionImpl()
		
		elem.setName(ORNTestUtils.getName(elemName))
		
		new ORNImporter().setPragmatics(elem, elemName)
		
		assertEquals(2, elem.pragmatics.size())		
	}
	
}
