

package org.k1s.nppn.generator
import groovy.text.GStringTemplateEngine;

import java.io.ByteArrayOutputStream;


import java.io.OutputStream;

import nppnnets.Pragmatics;

import nppnnets.Transition;

//import org.k1s.nppn.ParserUtil;

import nppnnets.Arc;
//import org.cpntools.accesscpn.model.Instance;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import nppnnets.Place;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
//import org.k1s.nppn.plattforms.GroovyPlattform;
class Generator {

	def plattform = new GroovyPlattform()
	def configuration
	
	def pn
	
	def currentBindings = []
	
	def reachedPlaces = []
	def deferedTransitions = []
	
	Generator(configuration){
		this.configuration = configuration
	}
	List names = []
	def generate(PetriNet pn){
		def protocolEntites = []
		this.pn = pn
		def entities = findEntities(pn)
		//println"ENTITIES $entities"
		entities.each {
			def bos = new ByteArrayOutputStream()
			currentBindings = []
			ParserUtil.debug it.name.text
			generateEntity it, 	bos
			
			names <<  it.name.text
			protocolEntites << bos.toString()
		}
		return protocolEntites
	}
	
	List getNames(){
		return names
	}
	
	def getCode(String segmentName, binding){
		GStringTemplateEngine engine = new GStringTemplateEngine(this.getClass().getClassLoader())
		new org.k1s.nppn.generator.Variable();
		new org.k1s.nppn.generator.VariableGroup();
		//engine.parentLoader = this.getClass().getClassLoader();
		def template = engine.createTemplate(plattform."${segmentName}").make(binding)
		
		return template.toString()
	}
	
	
	
	def generateEntity(Page p, OutputStream os){
		def apis = []
		println "$p"
		println "--------------------------------"
		os << ParserUtil.getComment(p)
		os << getCode("START_MODULE",[p: p])
		if(configuration) os << configuration.CONSTRUCTOR(p.name.text)
		else os << configuration.CONSTRUCTOR(p.name.text)
		os << getCode('GET_SOCKET_UDP',[:])
		os << ParserUtil.getCode('ENCODEDATA',[:])
		os << ParserUtil.getCode('DECODEDATA',[:])
		p.getObject().findAll { (it instanceof Transition) }.each { Transition trans ->
			Pragmatics prag = trans.getPragmatics()[0]
			println prag.getText()
			println prag.getName()
			if(prag != null && prag.name == "API") apis << trans
		}
		
		apis.each{
			generateFunction(it, os)
		}
		
		if(apis.size() == 0) { //no api, defaulting to main
			os << getCode('START_MAIN', [:])
			// no apis, look for listening ports
			println "no apis, look for listening ports"
			p.getObject().findAll {it instanceof RefPlace}.each{ RefPlace it ->
				//println"PORT"
				if( it instanceof RefPlace ){
					handleSourceRefPlace(it, os)
					
					it.getOut().each { arch ->
						handleArch(arch, os)
					}
				}
				
				
			}
			
			os << getCode('END_MAIN',[:])
		}
		
		os << getCode("END_MODULE",[p: p])
		////printlnp.name
	}
	
	def generateFunction(Transition trans, OutputStream os){
		Pragmatics prag = trans.getPragmatics()[0]
		
		if(prag.name != "API") throw new RuntimeException("not an api")
		os << ParserUtil.getComment(trans)
		os << getCode('START_API', [prag: prag])
			enableResultingPlaces trans
			trans.getOut().each {
				println "handleing out: $it"
				handleArch(it, os)
			}
		os << getCode('END_API', [prag: prag])
	}
	
	def handledTargets = []
	
	def handleArch(Arc arch, os){
		////println"arch $arch"
		////printlnarch.target
		println arch.getTarget()
		if(handledTargets.contains(arch.target.id)){
			println "allready handled"
			return
		} else {
			if(!(arch.target instanceof Transition)) handledTargets << arch.target.id
		}
		
		if( arch.target instanceof RefPlace ){
			handleTargetRefPlace(arch.target, os)
		} else if (arch.target instanceof Transition){
			handleTransition arch.target, arch, os
		} else if(arch.target instanceof Place){
			println"HANDLE PLACE ${arch.target}"
			handlePlace(arch.target, os)
		} else if(arch.target instanceof Page){ //this is a substitution transition
			handleSubPage(arch.target, arch, os, arch)
			
		}
	}
	
	
	def handleTransition(Transition trans, Arc arc, OutputStream os){
		if(!canFire(trans)) return
		handledTargets << trans.id
		println "fireing $trans"
		Pragmatics prag = trans.getPragmatics()[0]
		if(prag) {
			////printlnprag.name
			switch(prag.name){
				case "oper":
				case "operation":
					//println"OPERATRION ${prag.name} ${prag.arguments}"
					handleOperation(trans, prag, os)
					break
				case "encrypt":
					handleEncrypt(trans, prag, os)
					break
				case "decrypt":
					//println"DECRYPT"
					handleDecrypt(trans, prag, os)
					break
				case "recieve":
					handleRecieve(trans, prag, os, arc)
					break
				
			}
		}
		//printlnarc.target.sourceArc.toList()
		enableResultingPlaces trans
		
		arc.target.getOut().each {
			println"SGDFGSDFGSDF  $it.target"
			handleArch(it, os)
		}
	}
	
	def enableResultingPlaces(Transition trans){
		trans.getOut().each {
			if(it.target instanceof Place) {
				println "enabeling ${it.target}"
				reachedPlaces << it.target
			}
		}
	}
	
	def canFire(Transition trans){
		def retval = true
		trans.getIn().each { Arc arc ->
			def target = arc.source
			if(target instanceof Place && !reachedPlaces.contains(target)){
				Pragmatics prag = Pragmatic.prag(target.name.text)
				if(prag == null || prag.name != 'transient'){
					println"skipping ${trans} because ${target} is not yet reached"
					retval = false
				}
			}
		}
		return retval
	}
	
	/**
	 * Handles a place by handleing its outgoing archs
	 * @param place
	 * @param os
	 * @return
	 */
	def handlePlace(def place, OutputStream os){
		//new RuntimeException().printStackTrace()
		//printlnplace.sourceArc.toList()
		place.getOut().each { arch ->
			println "from place ${arch}"
			//printlnarch.target
			handleArch(arch, os)
		}
	}
	
	def handleSubPage(Page sub, Arc arc, OutputStream os, foundThrough){
				//first, find the right page
		def pageId = sub.subPageID
		
		Page subPage
		pn.getPage().each{ page ->
			if(page.id == sub.subPageID) subPage = page
		}
		
		//printlnsubPage
		
		//printlnarc
		//printlnarc.source
		
		//find starting port ID
		def portId
		sub.parameterAssignment.each{ param ->
			if(param.parameter == arc.source.id) portId =  param.value
		}
		
		//println"portId: $portId"
		
		//find the starting-port
		def startPort
		subPage.portPlace().each{ RefPlace port ->
			
			//printlnport
			//printlnport.id == portId
			if(port.id == portId) startPort = port
		}
		
		handlePlace startPort, os
	}
	
	def handleRecieve(Transition trans, Pragmatics prag, OutputStream os, foundThrough){
		//find in-place and handle
		//println"RECIEVE"
		
		def in_var
		trans.targetArc.each{ targetArch ->
			if(targetArch.source instanceof RefPlace){
				
				//println "RECIEVE REF PLACE: ${targetArch.source}"
				def isChannel = handleSourceRefPlace(targetArch.source, os)
				if(isChannel) in_var = InscriptionParser.parse (targetArch.getHlinscription().text)[0].name
			}
		}
		def out_vars = []
		trans.sourceArc.each{ source ->
			def var = InscriptionParser.parse(source.getHlinscription().text)
			println source.getHlinscription().text
			out_vars << var
		}
		out_vars = out_vars.flatten()
		
		println out_vars
		if(prag.getArguments().containsKey('vars')){
			def recv_list = prag.getArguments()['vars'].split(',').toList().flatten()
			println recv_list
			os << 	ParserUtil.getCode("RECIEVE_SPLIT_DATA_RECV_LIST", [out_vars: out_vars, in_var: in_var, prag: prag, recv_list: recv_list], currentBindings )
		} else {
			os << ParserUtil.getCode("RECIEVE_SPLIT_DATA", [out_vars: out_vars, in_var: in_var, prag: prag], currentBindings )
		}
			/*trans.sourceArc.each { arch ->
			handleArch(arch, os)
		}*/
	}
	
	def handleEncrypt(Transition trans, Pragmatics prag, OutputStream os){

		def in_var = InscriptionParser.parse (trans.getIn()[0].inscription.text)
		
		def out_var = InscriptionParser.parse(trans.getOut()[0].inscription.text)

		os << ParserUtil.getComment(trans)
		os << getCode("ENC", [in_var: in_var[0], out_var: out_var[0]])
		
	}

	def getVars(arcs){
		def retval = []
		arcs.each{
			retval << InscriptionParser.parse(it.inscription.text)
		}
		return retval
	}
	
	def handleDecrypt(Transition trans, Pragmatics prag, OutputStream os){
		def in_var = InscriptionParser.parse (trans.getIn()[0].inscription.text)
		def out_vars = []
		trans.getOut().each {
			out_vars << it.inscription.text
		}
		def out_var = InscriptionParser.parse(trans.getOut()[0].inscription.text)
		
		os << ParserUtil.getComment(trans)
		os << ParserUtil.getCode("DEC", [out_var: out_var[0], out_vars: out_vars, in_var: in_var[0]], currentBindings)
		
	}
		
	def handleOperation(Transition trans, Pragmatics prag, OutputStream os){
		////println"operation!!"
		def sources = trans.getIn()
		switch(prag.arguments.name){
			case "print":
				os << ParserUtil.getComment(trans)
				os << getCode('OPER_PRINT', [text: sources.get(0).inscription.text])
				break
			case "decryptKey":
				//find encrypted incomming data
				def in_vars = getVars(trans.getIn())
				
				
				def enc_vars = []
				getEncs in_vars, enc_vars
								
				def out_vars = getVars(trans.getSourceArc())
				
				os << ParserUtil.getComment(trans)
				
				os << ParserUtil.getCode('DECRYPTKEY', [enc_vars: enc_vars.flatten(), out_vars: out_vars.flatten(), in_vars: in_vars],currentBindings)
				break
			default:
				//println"DEFAULT"
				def in_var = InscriptionParser.parse (trans.getTargetArc().get(0).getHlinscription().text)
				def out_var = InscriptionParser.parse(trans.sourceArc.get(0)?.getHlinscription().text)
				
				def in_vars = []
				trans.getTargetArc().each{
					in_vars << InscriptionParser.parse (it.getHlinscription().text)
				}
				
				/*def out_vars = []
				trans.sourceArc.each {
					out_vars << it.getHlinscription().text
				}*/
				prag.arguments.in_var = in_var[0]
				prag.arguments.in_vars = in_vars.flatten()
				prag.arguments.out_var = out_var[0]
				prag.arguments.out_vars = out_var
				//prag.arguments.out_vars = out_vars
				os << ParserUtil.getComment(trans)
				os << ParserUtil.findCodeFor(trans, prag,currentBindings)
			break
		}
	}
	
	def getEncs(in_vars, enc_vars){
		println "getEncs: ${in_vars}"
		in_vars  = in_vars.flatten()
		in_vars.each {
			def encPrag = null
			def prags = it.prags.flatten()
			prags.each { p ->
				println "${p.class}"
				println "getEncs: ${p.name?.class}"
				if(p.name == "enc") encPrag = p
			}
			if(encPrag){
				enc_vars << it
			}else{
				println "getEnc enc not found for: ${it.name}"
				println "getEnc rec: ${it.class}"
				if(it instanceof VariableGroup) getEncs it.vars, enc_vars
				if(it instanceof VariableGroup) getEncs it.groups, enc_vars
				if(it instanceof List) getEncs it, enc_vars
				
			}
		}
	}
	
	def handleSourceRefPlace(RefPlace place, OutputStream os){
		println"REF: $place"
		def retval = false
		Pragmatics prag = place.getRef().getPragmatics()[0]
		if( prag != null && prag.name == 'channel'){ //this is probably incomming channel place
			if(place.getOut().size() == 0) return
		
			def out_var = InscriptionParser.parse(place.getOut()[0]?.inscription.text)
		
			os << ParserUtil.getComment(place)
			os << ParserUtil.getCode("UDP_RECIVE", [out_vars: out_var[0], prag: prag],currentBindings)
			retval = true
		}
		return retval
	}
	
	def handleTargetRefPlace(RefPlace place, OutputStream os){
		Pragmatics prag = place.getRef().getPragmatics()[0]
		if(prag != null && prag.name == 'channel'){ //this is probably an outgoing channel place
			
			def sources = place.getIn()
			////printlnsources
			def sendVars = InscriptionParser.parse(sources.get(0)?.inscription.text)
			/*Arc a = sources.get(0)
			def data = a.getHlinscription().text
			def prag = null
			if(data.indexOf("<<") > -1) {
				prag = getPragmatic(data)
				data = data.substring(0, data.indexOf("<<"))
				
			}*/
			os << ParserUtil.getComment(place)
			os << ParserUtil.getCode('UDP_SEND',[sendVars: sendVars, prag: prag], currentBindings)
			
		}
	}
	
	def findEntities(PetriNet pn){
		//def entityIds = findEntityIds(pn)
		def entities = []
		Page root = pn.page.get(0)
		root.getObject().each{ page ->
			if(page instanceof Page)
			 entities << page
		}
		return entities
	}
	
//	def findEntityIds(PetriNet pn){
//		Page root = pn.page.get(0)
//		def en = []
//		
//		root.getObject().each{ def i ->
//			println"ENTITY ID:  ${i}"
//			if(i instanceof Page)
//				en << i
//		}
//		println en.size()
//		return en
//	}
	
//	def findRoot(PetriNet pn){
//		def root = []
//		def references = []
//		
//		pn.getPage().each{ Page page ->
//			page.instance().each{ Instance i ->
//				references << i.subPageID
//			}
//		}
//		
//		pn.getPage().each{ Page page ->
//			def isRoot = true
//			references.each {ref ->
//					if(page.id == ref) isRoot = false
//			}
//			if(isRoot) root << page
//		}
//		
//		return root
//	}
	
	
	
}
