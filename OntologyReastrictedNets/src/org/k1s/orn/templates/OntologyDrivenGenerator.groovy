
package org.k1s.orn.templates

import org.cpntools.accesscpn.model.impl.PlaceImpl;
import org.k1s.orn.att.Transition;



import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

import org.semanticweb.owlapi.model.OWLLiteral;


import org.k1s.orn.att.AbstractTemplateTree2;
import org.k1s.orn.owl.ORNConstants;
import org.k1s.orn.templates.ogdVisitors.ODGVisitor;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import orn.PragStructure;
import orn.Pragmatics;
import orn.impl.OrnFactoryImpl;

class OntologyDrivenGenerator {
	def templateManager
	
	//1. Add extra pragmatics
	//2. Create abstract template tree (ATT)
	//3. visit 3 to build up code
	//4. make att into files
	//5. Profit
	
	
	def generate(PetriNet pn, bindings){
		//def rootPage = pn.page[0]
		//1. 
		
		addExtraPragmatics(pn)
		
		//2. 
		def att = AbstractTemplateTree2.attForNet(pn, bindings)
		println "attForNet : $att"
		
		//3.
		att.children.each{
			//println "child: $it"
			ODGVisitor.visitATT(it,bindings)
		}
		
		println "created $att"
		println "with children ${att.children}"
		
		att.children.each {  println "with children ${it.children}" }
		
		//4.
		def files = []
		println "att.children ${att.children}"
		println "att.children[0].children ${att.children[0].children}"
		att.children.each{ files << attToFile(it)  }
		
		return files
	}
	
	
	def generate2(PetriNet pn, bindings){
		//def rootPage = pn.page[0]
		//1.
		
		addExtraPragmatics(pn)
		
		//2.
		def att = AbstractTemplateTree2.attForNet(pn, bindings)
		println "attForNet : $att"
		
		//3.
		att.children.each{
			//println "child: $it"
			ODGVisitor.visitATT(it,bindings)
		}
		
		println "created $att"
		println "with children ${att.children}"
		
		att.children.each {  println "with children ${it.children}" }
		
		//4.
		def files = []
		att.children.each{ files << attToFile(it, bindings)  }
		return files
	}
	
	def addExtraPragmatics(pn){
		//println "ONTOLOGY: ${pn.page[0].ontology.structure.params}"
		def extraPatterns = getImplicitPatterns(pn.page[0].ontology.text)
		println "implicit patterns: $extraPatterns" 
		extraPatterns.each { patternTouple ->
				def pattern = new GroovyShell(this.getClass().getClassLoader()).parse(patternTouple[1]).run()
				def pragmaticName = patternTouple[0]
				//println "Matching: ${pragmaticName}"
				def matches = pattern.match(pn)
				println "!!MATCHES!!:  $matches"
				matches.each { match ->
					//println "!!MATCH!!:  $match"
					def cond = ""
					if(patternTouple[2]){
						
						cond = getCond(pragmaticName, match)
					}
					addPrag(pragmaticName, cond, match)
					
					//println match.pragmatics
				}
		}
	}
	
	static def getCond(pragmaticName, match){
		if(match instanceof orn.Place){
			orn.Place p = (orn.Place)match
			if(pragmaticName == "EndLop"){
				//in loops true means continue and false means break.
			    if(p.out.size() != 2) throw new ATTNotOKException("EndLoop ${p.name.text} has invalid number of out arcs: ${p.out.size()}")
				
			}
		}
	}
	
	static def  addPrag(String name, String params, node){
		Pragmatics prag = OrnFactoryImpl.eINSTANCE.createPragmatics()
		prag.setText("${name}(${params})")
		
		PragStructure ps = prag.parse(prag.getText())/*OrnFactoryImpl.eINSTANCE.createPragStructure()
		ps.setName name
		ps.setArguments params*/
		prag.setStructure ps
		
		//node.getPragmatics()[0] =  prag
		if(node.getPragmatics()[0] && node.getPragmatics()[0].structure.name == "Id")
			node.getPragmatics()[0] =  prag
		else 
			node.getPragmatics().add(prag)
		
		return prag
	}
	def getImplicitPatterns(ontologyFile){
		if(ontologyFile.size > 0) ontologyFile = ontologyFile[0]
		else return []
		def patterns = []
		println "ontologyFile $ontologyFile"
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		File basicFile = new File("/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/basic.fowl");
		SimpleIRIMapper basicMapper = new SimpleIRIMapper(new URI(ORNConstants.BASIC_IRI), IRI.create(basicFile));
		
		File cpnFile = new File("/home/kent/ws-ePNK/OntologyReastrictedNets/ontologies/cpn.fowl");
		SimpleIRIMapper cpnMapper = new SimpleIRIMapper(new URI(ORNConstants.CPN_IRI)
				, IRI.create(cpnFile));
		manager.addIRIMapper(basicMapper);
		manager.addIRIMapper(cpnMapper);
		
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new StringDocumentSource(new File(ontologyFile).text));
		
		def implClass
		ontology.getClassesInSignature().each { 
			println it	
			if(it.toString() == "<http://t.k1s.org/OntologyReastrictedNets/basic/Implicit>") implClass = it
		}
		
		def implClasses = implClass.getSubClasses(ontology)
		
		println "IMPLCLASSES: $implClasses"
		
		implClasses.each{ OWLClass imp ->
			def name = imp.getIRI().toString().substring(imp.getIRI().toString().lastIndexOf('/')+1)
			OWLIndividual patternInd = getIndiviual( "${name}ImplicitPattern", ontology)
			def key = patternInd.getDataPropertyValues(ontology).keySet().toArray()[0]
//			println "name: $name, pattrnInd: ${name}ImplicitPattern"
			println "keys ${patternInd.getDataPropertyValues(ontology).keySet().toArray().collect{ ((OWLDataProperty) it).toString()  } }"
			println "map ${patternInd.getDataPropertyValues(ontology)}"
			def pttrnKey = patternInd.getDataPropertyValues(ontology).keySet().find{ it.toString().endsWith("pttrn>") }
			
//			println "values: ${patternInd.getDataPropertyValues(ontology)[key]}"
			def condKey = patternInd.getDataPropertyValues(ontology).keySet().find{ it.toString().endsWith("setCond>") }
			println "condKey: $condKey"
			def setCond = false
			if(condKey){
				if(((OWLLiteralImpl)patternInd.getDataPropertyValues(ontology)[condKey].first()).getLiteral().toLowerCase() == "true")
					setCond = true
			}
			 
			patterns << [name, "import org.k1s.orn.templates.*\n "+((OWLLiteralImpl)patternInd.getDataPropertyValues(ontology)[pttrnKey].first()).getLiteral()+"", setCond]
			
		}
		
		
		return patterns
	}
	
	def getIndiviual(name, OWLOntology ontology){
		def retVal
		ontology.getIndividualsInSignature().each { OWLIndividual ind ->
				if(ind.asOWLNamedIndividual().getIRI().toString().endsWith(name)){
					retVal = ind
				}
		}
		return retVal
	}
	
//	def matchOnPage(page, patterns){
//		page.object.each{
//			if(it instanceof Page){
//				matchOnPage(it, patterns)
//			} else if(!(it instanceof Arc)){
//				patterns.each { pattern -> 
//					pattern = new GroovyShell(this.getClass().getClassLoader()).parse(pattern).run()
//					
//					if(pattern.matchNode(it)){
//						applyPattern(it, pattern)
//					}
//				}
//			}
//		}
//	}
	
	
	def attToFile(att, bindings = null){
		def file
		//println "att: $att"
		//println "att: $att.correspondingNetElement.name"
		println "templateText ${att.templateText}"
		if(att.templateText?.contains("%%yield%%")){
			StringBuilder childrenText = new StringBuilder()
			if(!(att instanceof Transition)){
				att.children.each{ child ->
					def text = attToFile(child, bindings)
					if(text != null && text.trim() != "null")
						childrenText.append attToFile(child, bindings)
				}
			}
			//println childrenText.class
			file = att.templateText.replaceAll("%%yield%%", childrenText.toString())
		} else if(att.templateText?.contains("%%yeild_")){
			//println "merging conditional: ${att.templateText}"
			file = att.templateText
			att.children.each{ child ->
				//println "checking child with label: ${child.label}"
				def childrenText = attToFile(child)
				//println "setting %%yeild_${child.label}%%"
				file = file.replaceAll("%%yeild_${child.label}%%", childrenText.toString())
			}
		} else {
			file = att.templateText
		}
		println "making text for $att : $file"
		return file
	}
}
