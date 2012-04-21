package org.k1s.orn.owl

import org.junit.Test;
import org.k1s.orn.ORNVerifier;
import org.k1s.orn.test.ORNTestUtils;
import static org.junit.Assert.*;
class ExternalIdFromNetTests {

	
	
	
	@Test
	void testValidNetIsValid(){
		def net = ORNTestUtils.getValidProtocolNet()
		assertTrue( ORNVerifier.isValid(net, ORNTestUtils.nppnOntTmpl) )
	}
	
	@Test
	void testInValidNetIsValid(){
		def net = ORNTestUtils.getInValidProtocolNet()
		assertFalse( ORNVerifier.isValid(net, ORNTestUtils.nppnOntTmpl) )
	}
}
