package org.k1s.orn.templates
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;

import org.semanticweb.owlapi.model.OWLLiteral;



import org.k1s.orn.owl.ORNConstants;
import org.k1s.orn.templates.ogdVisitors.ODGVisitor;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
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
		//1. (skip for now)
		
		addExtraPragmatics(pn)
		
		//2. 
		def att = AbstractTemplateTree.attForNet(pn, bindings)
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
		att.children.each{ files << attToFile(it)  }
		return files
	}
	
	def addExtraPragmatics(pn){
		def extraPatterns = getImplicitPatterns(pn.page[0].ontology.text) 
		extraPatterns.each { patternPair ->
				def pattern = new GroovyShell(this.getClass().getClassLoader()).parse(patternPair[1]).run()
				def pragmaticName = patternPair[0]
				//println "Matching: ${pragmaticName}"
				def matches = pattern.match(pn)
				println "!!MATCHES!!:  $matches"
				matches.each { match ->
					//println "!!MATCH!!:  $match"
					addPrag(pragmaticName, "", match)
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
		
		node.getPragmatics().add(prag)
		
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
			println "name: $name"
			patterns << [name, "import org.k1s.orn.templates.*\n "+((OWLLiteralImpl)patternInd.getDataPropertyValues(ontology)[key].first()).getLiteral()+""]
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
	
	
	def attToFile(att){
		def file
		if(att.templateText?.contains("%%yield%%")){
			StringBuilder childrenText = new StringBuilder()
			att.children.each{ child ->
				childrenText.append attToFile(child)
			}
			file = att.templateText.replaceAll("%%yield%%", childrenText.toString())
		} else if(att.templateText?.contains("%%yeild_")){
			println "merging conditional: ${att.templateText}"
			file = att.templateText
			att.children.each{ child ->
				println "checking child with label: ${child.label}"
				def childrenText = attToFile(child)
				println "setting %%yeild_${child.label}%%"
				file = file.replaceAll("%%yeild_${child.label}%%", childrenText.toString())
			}
		} else {
			file = att.templateText
		}
		println "making text for $att : $file"
		return file
	}
}
