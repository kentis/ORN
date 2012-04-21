package org.k1s.orn;

import java.util.List;

import org.junit.Test;

import orn.Page;

import static org.junit.Assert.*;

class ORNImporterTests {

	
	@Test
	void testWS(){
		def fileName = "/home/kent/projects/ws/websocket/descriptive-fmooods-forte-12/model/WSProtocol.cpn"
		ORNImporter translator = new ORNImporter();
		List<Page> pages =  translator.translate(fileName);
		
		assertEquals( 1, pages.size())
		
		assertTrue pages[0].object.size() > 1 
	}
}
