package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ePNKBuilder;
import org.k1s.orn.test.ePNKBuilderTests;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.OrnFactory;
import orn.impl.PragmaticsImpl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
class PNPatternsTest {
	
	
	@Test
	void testgetNodesByName(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE).make{
			page(name:"dill"){
				place(name: "dilldall")
				transition(name:"fdsdfds")
			}
		}
		def pattern = new PNPattern(name:"dilldall")
		
		def matches = pattern.match(pn)
		
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Place.class))
		assertThat matches[0].name.text, is("dilldall")
	}
	
	@Test
	void testgetNodesByPragmatic(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			page(name:"dill"){
				place(name: "dilldall", labels:[pragmatics: "Channel()"])
				transition(name:"fdsdfds")
			}
		}
		
		def pattern = new PNPattern(pragmatics:["Channel"])
		def matches = pattern.match(pn)
		
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Place.class))
		assertThat matches[0].name.text, is("dilldall")
		

	}
	
	@Test
	void testgetNodesAdjacentType(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			page(name:"dill"){
				def p = place(name: "dilldall", labels:[pragmatics: "Channel()"])
				def t = transition(name:"fdsdfds")
				arc(p,t)
			}
		}
		def adjPattern = new PNPattern(type: Transition.class)
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Place.class))
		assertThat matches[0].name.text, is("dilldall")
	}
	
	
	@Test
	void testgetNodesAdjacentWrongType(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			page(name:"dill"){
				def p = place(name: "dilldall", labels:[pragmatics: "Channel()"])
				def t = transition(name:"fdsdfds")
				arc(p,t)
			}
		}
		def adjPattern = new PNPattern(type: Place.class)
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(0))
		
	}
	
	
	@Test
	void testgetNodesAdjacentPragmatic(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			page(name:"dill"){
				def p = place(name: "dilldall", labels:[pragmatics: "Channel()"])
				def t = transition(name:"fdsdfds")
				arc(t,p)
			}
		}
		def adjPattern = new PNPattern(pragmatics:["Channel"])
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Transition.class))
		assertThat matches[0].name.text, is("fdsdfds")
	}
	
	@Test
	void testOutArcInscription(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			page(name:"dill"){
				def p = place(name: "dilldall", labels:[pragmatics: "Channel()"])
				def t = transition(name:"fdsdfds")
				arc(t,p, 'arc1', "CreateEndpointName(rhost,rport)")
			}
		}
		
		def pattern = new PNPattern(outArcInscription: 'CreateEndpointName')
		
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Transition.class))
		assertThat matches[0].name.text, is("fdsdfds")
	}
	
	
	@Test
	void testfindRefPlaceAdjacentPragmatic(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			
			page(name: "dall"){
				def p = place(name: "dilldall!!", labels:[pragmatics: "Channel()"])
			page(name:"dill"){
				def rp = refplace(name: "dilldall", ref: p)
				def t = transition(name:"fdsdfds")
				arc(t,rp)
			}
			}
		}
		def adjPattern = new PNPattern(pragmatics:["Channel"])
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Transition.class))
		assertThat matches[0].name.text, is("fdsdfds")
	}
	
	
	@Test
	void testfindRefPlaceAdjacentPragmaticNoBacklinkFail(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			
			page(name: "dall"){
				def p = place(name: "dilldall!!", labels:[pragmatics: "Channel()"])
			page(name:"dill"){
				def rp = refplace(name: "dilldall", ref: p)
				def t = transition(name:"fdsdfds")
				arc(t,rp)
			}
			}
		}
		def adjPattern = new PNPattern(pragmatics:["Channel"], backlink: true)
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(0))
		
	}
	
	@Test
	void testfindRefPlaceAdjacentPragmaticBacklink(){
		def pn = new ePNKBuilder(OrnFactory.eINSTANCE,[pragmatics: PragmaticsImpl.class]).make{
			
			page(name: "dall"){
				def p = place(name: "dilldall!!", labels:[pragmatics: "Channel()"])
			page(name:"dill"){
				def rp = refplace(name: "dilldall", ref: p)
				def t = transition(name:"fdsdfds")
				arc(t,rp)
				arc(rp, t)
			}
			}
		}
		def adjPattern = new PNPattern(pragmatics:["Channel"], backlink: true)
		def pattern = new PNPattern(adjacentPatterns: [adjPattern])
		def matches = pattern.match(pn)
		
		println matches
		assertThat(matches.size(), is(1))
		assertThat matches[0], is(instanceOf(Transition.class))
		assertThat matches[0].name.text, is("fdsdfds")
		
		
	}
}