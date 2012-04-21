
package org.k1s.nppn.generator;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.impl.PnmlcoremodelFactoryImpl;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HlpngdefinitionFactoryImpl;


import java.io.FileInputStream;


import org.cpntools.accesscpn.model.importer.DOMParser;
import org.junit.Test;
import org.k1s.epkn.cpnimport.CPNTranslator;

import static org.junit.Assert.*;

class GeneratorTests {

	@Test
	void testFindEntities(){
		def pages = new CPNTranslator().translate( new FileInputStream(simpleEnc))
		
		PetriNet pn = PnmlcoremodelFactoryImpl.eINSTANCE.createPetriNet()
		pn.getPage().addAll pages 
		
		Generator gen = new Generator(null)
		
		def en = gen.findEntities(pn)
		println  en
		assertEquals(2, en.size())
	}
	
	
	@Test
	void testGenerate(){
		def pages = new CPNTranslator().translate( new FileInputStream(simpleEnc))
		
		PetriNet pn = PnmlcoremodelFactoryImpl.eINSTANCE.createPetriNet()
		pn.getPage().addAll pages
		
		Generator gen = new Generator(new GroovyKCConfig())
		def classes = gen.generate(pn)
		
		
		
		ppClasses classes
		
		def output = []
		def client = new ProtcolRunner().run(classes[0], "new A('localhost',31337)")
		
		def server = new ProtcolRunner().run(classes[1], "new B(null,null,31337)")
		
		//server.metaClass.println { String str -> output << str }
		
		
		def buf = new ByteArrayOutputStream()
		def newOut = new PrintStream(buf)
		def saveOut = System.out
		
		
		System.out = newOut
		
		
		def t = Thread.start { server.start() }
		
		client.send("hallo")
		
		
		Thread.sleep 1000
		
		
		System.out = saveOut
		println buf.toString().trim()
		assertEquals( "hallo", buf.toString().trim() )
		
	}
	
	def ppClasses(classes){
		//PrettyPrintGroovy pp =  new PrettyPrintGroovy()
		classes.each{
			println "**************************************"
			println it
		}
	}
	
	def simpleEnc = new File("/home/kent/simpleEncryptionProtocol.cpn")
}
