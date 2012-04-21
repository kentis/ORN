package org.k1s.orn.owl;

import org.junit.Test;
import org.k1s.orn.ORNVerifier;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelFactory;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;


import static org.junit.Assert.*;

class ManchesterSyntaxBuilderTests {
	
	@Test
	void testBuildPlace(){
		
		Place p = PnmlcoremodelFactory.eINSTANCE.createPlace()
		
		p.setName(getName("test"))
		StringBuilder sb = new StringBuilder()
		
		new ManchesterSyntaxBuilder().place2ManchesterSyntax p, getPage(), sb
		
		
		def res = """
Individual: ${p.name.text}
	Types: Place
"""
		assertEquals(res.toString(), sb.toString())
	}
	
	
	@Test
	void testBuildTransition(){
		
		def p = PnmlcoremodelFactory.eINSTANCE.createTransition()
		
		p.setName(getName("test"))
		StringBuilder sb = new StringBuilder()
		
		new ManchesterSyntaxBuilder().transition2ManchesterSyntax p, getPage(), sb
		
		
		def res = """
Individual: ${p.name.text}
	Types: Transition
"""
		assertEquals(res.toString(), sb.toString())
	}
	
	@Test
	void testBuildEmptyPage(){
		
		def p = getPage()
		
		
		StringBuilder sb = new StringBuilder()
		
		new ManchesterSyntaxBuilder().page2ManchesterSyntax p, sb
		
		
		def res = """
Individual: ${p.name.text}
	Types: Page
	Facts: isTop "false"^^xsd:boolean
"""
		assertEquals(res.toString(), sb.toString())
	}
	
	
	@Test
	void testBuildSmallPetriNet(){
		
		PetriNet pn = getVerySimplePetriNet()
		def res = new ManchesterSyntaxBuilder().pn2ManchesterSyntax(pn)
		
		def pageText = """
Individual: ${page.name.text}
	Types: Page
	Facts: isTop "true"^^xsd:boolean
"""
		
		def transitionText = """
Individual: trans1
	Types: Transition
"""
		
		def placeText = """
Individual: place1
	Types: Place
"""

		def arcText = """
Individual: arc1
	Types: Arc
	Facts: source trans1
	Facts: target place1
"""

		println res
		assertTrue(res.contains(pageText))
		assertTrue(res.contains(transitionText))
		assertTrue(res.contains(placeText))
		assertTrue(res.contains(arcText))
		
	}
	
	@Test
	void testBuildSmallPetriNetAndParse(){
		PetriNet pn = getVerySimplePetriNet()
		def res = new ManchesterSyntaxBuilder().pn2ManchesterSyntax(pn)
		
		ORNVerifier.isValid(res)
		
	}
	
	
	def getVerySimplePetriNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage()
		
		
		Transition trans = PnmlcoremodelFactory.eINSTANCE.createTransition()
		trans.setName getName("trans1")
		page.getObject().add(trans)
		
		Place place = PnmlcoremodelFactory.eINSTANCE.createPlace()
		place.setName getName("place1")
		page.getObject().add(place)
		
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc.setName getName("arc1")
		arc.setSource trans
		arc.setTarget place
		page.getObject().add(arc)
		
		
		pn.getPage().add(page)
		
		return pn
	}
	
	def getPage(){
		Page p = PnmlcoremodelFactory.eINSTANCE.createPage()
		p.setName(getName("Testpage"))
		return p
	}
	
	
	def getName(text){
		Name n =  PnmlcoremodelFactory.eINSTANCE.createName()
		n.setText text
		return n
	}
}
