package org.k1s.orn.templates;

import org.junit.Test;
import org.k1s.orn.test.ORNTestUtils;

import orn.OrnFactory;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
class ConditionalsTests {

	@Test
	void testAtomicIfGroovy(){
		
		/*
		 * if(%%yeild_p1%%){ %%yield_e1%% }
		 * else if(%%yeild%%) { %%yield_e2%% }
		 * else { %%yield_e_final%% }
		 */
		def pragmatic = "Id(cond: '(foo arc1)(bar arc2)(t arc3)' )"
		pragmatic = makePrag(pragmatic)
		
		def bindings = ORNTestUtils.createGroovyBindingsTCP()
		
		def cond = Conditionals.translatePrags(pragmatic, bindings)
		println "COND: $cond"
		assertTrue cond.contains('if( foo ){ arc1 }')
		assertTrue cond.contains('else if( bar ){ arc2 }')
		assertTrue cond.contains('else if( true ){ arc3 }')
	}
	
	@Test
	void testAtomicIfJava(){
		/*
		* if(foo != null || foo != false){ %%yield_e1%% }
		* else { %%yield_e2%% }
		*/

		def pragmatic = "Id(cond: '(foo arc1)(bar arc2)(t arc3)' )"
		pragmatic = makePrag(pragmatic)
		
		def bindings = ORNTestUtils.createJavaBindings()
		
		def cond = Conditionals.translatePrags(pragmatic, bindings)
		println "COND: $cond"
		assertTrue cond.contains('if( foo != null || foo ){ arc1 }')
		assertTrue cond.contains('else if( bar != null || bar ){ arc2 }')
		assertTrue cond.contains('else if( true ){ arc3 }')
	}

	@Test
	void testAtomicIfLisp(){
		def pragmatic = "Id(cond: '(foo arc1)(bar arc2)(t arc3)' )"
		pragmatic = makePrag(pragmatic)
		
		def bindings = ORNTestUtils.createLispBindings() 
		
		def cond = Conditionals.translatePrags(pragmatic, bindings)
		println "COND: $cond"
		assertTrue cond.contains('cond(')
		assertTrue cond.contains('(foo arc1)')
		assertTrue cond.contains('(bar arc2)')
		assertTrue cond.contains('(t arc3)')
		assertTrue cond.contains('))')
	}

	
	@Test
	void testEqualityIfGroovy(){
		/*
		* if(foo == bar){ %%yield_e1%% }
		* else if(foo != bar) { %%yield_e2%% }
		* else { %%yield_e_final%% }
		*/
	   def pragmatic = "Id(cond: '((eq foo bar)arc1)((neq foo bar) arc2)(t arc3)' )"
	   pragmatic = makePrag(pragmatic)
	   
	   def bindings = ORNTestUtils.createGroovyBindingsTCP()
	   
	   def cond = Conditionals.translatePrags(pragmatic, bindings)
	   println "COND: $cond"
	   assertTrue cond.contains('if( foo == bar ){ arc1 }')
	   assertTrue cond.contains('else if( foo != bar ){ arc2 }')
	   assertTrue cond.contains('else if( true ){ arc3 }')
	}

	@Test
	void testEqualityIfJava(){
		/*
		* if(foo == bar){ %%yield_e1%% }
		* else if(foo != bar) { %%yield_e2%% }
		* else { %%yield_e_final%% }
		*/
	   def pragmatic = "Id(cond: '((eq foo bar)arc1)((neq foo bar) arc2)(t arc3)' )"
	   pragmatic = makePrag(pragmatic)
	   
	   def bindings = ORNTestUtils.createJavaBindings()
	   
	   def cond = Conditionals.translatePrags(pragmatic, bindings)
	   println "COND: $cond"
	   assertTrue cond.contains('if( foo.equals(  bar )){ arc1 }')
	   assertTrue cond.contains('else if(! foo.equals(  bar )){ arc2 }')
	   assertTrue cond.contains('else if( true ){ arc3 }')
	}

	@Test
	void testEqualityIfLisp(){
		def pragmatic = "Id(cond: '((eq foo bar)arc1)((neq foo bar) arc2)(t arc3)' )"
		pragmatic = makePrag(pragmatic)
		
		def bindings = ORNTestUtils.createLispBindings() 
		
		def cond = Conditionals.translatePrags(pragmatic, bindings)
		println "COND: $cond"
		assertTrue cond.contains('cond(')
		assertTrue cond.contains('((eq foo bar) arc1)')
		assertTrue cond.contains('((neq foo bar) arc2)')
		assertTrue cond.contains('(t arc3)')
		assertTrue cond.contains('))')
	}

	//TODO:numbers, boolean operators
	
	@Test
	void testParser(){
		def pragmatic = "(foo arc1)(bar arc2)(t arc3)"
		def conds = Conditionals.parse(pragmatic)
		assertThat conds.conds.size(), is(3)
		
		assertThat conds.conds[0].e, is('foo')
		assertThat conds.conds[0].p, is('arc1')
		assertThat conds.conds[1].e, is('bar')
		assertThat conds.conds[1].p, is('arc2')
		assertThat conds.conds[2].e, is('t')
		assertThat conds.conds[2].p, is('arc3')
	}
	
	def makePrag(prag){
		def pragmatic = OrnFactory.eINSTANCE.createPragmatics()
		pragmatic.setText prag
		pragmatic.setStructure pragmatic.parse(pragmatic.text)
		
		return pragmatic
	}
}
