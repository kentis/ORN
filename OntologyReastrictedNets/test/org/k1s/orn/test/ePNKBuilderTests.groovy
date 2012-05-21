package org.k1s.orn.test;


import com.hp.hpl.jena.sparql.function.CastXSD.Instance;
import org.junit.Test;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelFactory;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.OrnFactory;
import orn.Pragmatics;
import orn.impl.PragmaticsImpl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class ePNKBuilderTests {
	
	@Test
	void testBuilderPagePlaceArcTransition(){
		//def builder = new ePNKBuilder(PnmlcoremodelFactory.eINSTANCE)
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE)
		println "builder.factory  ${builder.factory}"
		def pn = builder.make {
			page(name:'root'){
				def p = place(name:'place')
				def t = transition(name:'tranistion')
				arc(p,t)
			}
		}
		
		assertThat pn.page.size(),  is(1)
		assertThat pn.page[0].object.size(), is(2)
		println pn.page[0].object[0].class
		assertTrue pn.page[0].object[0] instanceof Place
		assertTrue pn.page[0].object[1] instanceof Transition
		Place p = pn.page[0].object[0]
		
		assertThat p.getOut().size(), is(1)
		Arc arc = p.getOut()[0]
		assertThat arc.getTarget(), is(pn.page[0].object[1])

		assertThat pn.page[0].object[1].getIn()[0], is(arc)
	}
	
	@Test
	void testBuilderMultiPage(){
		//def builder = new ePNKBuilder(PnmlcoremodelFactory.eINSTANCE)
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE)
		println "builder.factory  ${builder.factory}"
		def pn = builder.make {
			def page = page(name:'root'){
				def p = place(name:'place')
				page(name: 'subpage'){
					def np = refPlace(ref:p, name: 'name')
					def nt = transition(name:'tranistion')
					arc(np,nt)
				}
				def t = transition(name:'tranistion')
				arc(p,t)
			}
		}
		
		//a place, a page and a transotion
		assertThat pn.page[0].object.size(), is(3)
		assertTrue pn.page[0].object[1] instanceof Page
		/**********************************************
		 * Make sure everything from last page is true
		 **********************************************/
		assertThat pn.page.size(),  is(1)
		
		println pn.page[0].object[0].class
		assertTrue pn.page[0].object[0] instanceof Place
		assertTrue pn.page[0].object[2] instanceof Transition
		Place p = pn.page[0].object[0]
		
		assertThat p.getOut().size(), is(1)
		Arc arc = p.getOut()[0]
		assertThat arc.getTarget(), is(pn.page[0].object[2])

		assertThat pn.page[0].object[2].getIn()[0], is(arc)
		
		
		/***************************************
		 * 
		 *************************************/
		def page =  pn.page[0].object[1]
		assertTrue page.object[0] instanceof RefPlace
		assertTrue page.object[1] instanceof Transition
		assertThat page.object[0].getOut().size(), is(1)
		
	}
	
	
	
	@Test
	void testBuilderPagePlaceWithPrag(){
		//def builder = new ePNKBuilder(PnmlcoremodelFactory.eINSTANCE)
		def builder = new ePNKBuilder(OrnFactory.eINSTANCE, [pragmatics: PragmaticsImpl.class])
		println "builder.factory  ${builder.factory}"
		def pn = builder.make {
			page(name:'root', labels:[pragmatics: "Principal()"]){
				def p = place(name:'place', labels:[pragmatics: "Channel()"])
				transition(name:'trans', labels:[pragmatics: "External()"])
			}
		}
		assertThat pn.page.size(),  is(1)
		assertThat pn.page[0].object.size(),  is(2)
		
		assertTrue pn.page[0].object[0] instanceof Place
		
		Place p = pn.page[0].object[0]
		
		assertThat p.pragmatics.size(), is(1)
		assertThat p.pragmatics[0].text, is("Channel()")
		assertThat p.pragmatics[0].structure.name, is("Channel")
		
		assertThat pn.page[0].pragmatics.size(), is(1)
		assertThat pn.page[0].pragmatics[0].text, is("Principal()")
		assertThat pn.page[0].pragmatics[0].structure.name, is("Principal")
		
		Transition t = pn.page[0].object[1]
		assertThat t.pragmatics.size(), is(1)
		assertThat t.pragmatics[0].text, is("External()")
		assertThat t.pragmatics[0].structure.name, is("External")
		
	}
}
