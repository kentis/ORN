package org.k1s.orn.test

import java.security.Principal;

import org.k1s.orn.templates.BindingsDSL;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelFactory;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

import orn.PragStructure;
import orn.Pragmatics;
import orn.impl.OrnFactoryImpl;

class ORNTestUtils {

	
	/********************************
	 * NPPN Stuff
	 *************************************/

	static def getSimpleProtocolPN(){
		def pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		//Root page
		def root = getPage()
		
		Page client = getPage("Server")
		addPrag "Principal", "", client
		root.getObject().add client
		
		Page server = getPage("Server")
		addPrag "Principal", "", server
		root.getObject().add server
		
		def channel = getPlace("channel")
		
		root.object << client
		root.object << server
		
		root.object << channel
		pn.page << root
		
		
		//Client page
		
		def clientExt = getTransition("start")
		addPrag "External", "msg", clientExt
		 
		def waitSend = getPlace("waitSend")
		
		def send = getTransition("send")
		addPrag "Send", "msg", send
		
		RefPlace chRef = PnmlcoremodelFactory.eINSTANCE.createRefPlace()
		chRef.setRef channel
		def arc1 = getArc(clientExt, waitSend)
		def arc2 = getArc(waitSend, send)
		def arc3 = getArc(send, chRef)
		client.object << clientExt
		client.object << waitSend
		client.object << send
		client.object << chRef
		
		//Server page
		def serverExt = getTransition("start")
		addPrag "External", "msg", serverExt
		
		def waitRecieve = getPlace("waitRecieve")
		
		def recieve = getTransition("recieve")
		addPrag "Recieve", "msg", send
		
		def arc4 = getArc(serverExt, waitRecieve)
		def arc5 = getArc(waitRecieve, recieve)
		
		server.object << serverExt
		server.object << waitRecieve
		server.object << recieve
		
		return pn
	}
	
	static def createOnePrincipalOneExtenalNoExtraNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page root = getPage()
		
		Page principal = getPage() 
		addPrag "Principal()", "", principal
		root.getObject().add principal
		
		Transition external = getTransition()
		addPrag "External()", "", external
		principal.getObject().add external
		
		pn.getPage().add root
		
		return pn
	}

	static def createGroovyBindings(){
		return 	BindingsDSL.makeBindings {
			
			ClassTemplate(pragmatic: 'Principal', template: '/home/kent/ws-ePNK/OntologyReastrictedNets/plattforms/groovy/mainClass.tmpl', isContainer: true)
			
			ExternalTemplate(pragmatic: 'External', template: '/home/kent/ws-ePNK/OntologyReastrictedNets/plattforms/groovy/externalMethod.tmpl', isContainer: true)
			
			Send(pragmatic: 'Send', template: '/home/kent/ws-ePNK/OntologyReastrictedNets/plattforms/groovy/sendMessage.tmpl', dependencies: 'channels')
			
			Recieve(pragmatic: 'Revieve', template: '/home/kent/ws-ePNK/OntologyReastrictedNets/plattforms/groovy/recieveMessage.tmpl', dependencies: 'channels')
		}
	}
	
	/*************************
	 * LOGO stuff
	 *************************/
	
	static def getPetriNet(){
		return PnmlcoremodelFactory.eINSTANCE.createPetriNet()
	}
	
	static def getSimpleLogoPetriNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage()
		
		Transition extTrans = OrnFactoryImpl.eINSTANCE.createTransition()
		extTrans.setName getName("Start")
		addPrag("External", "", extTrans)
		page.getObject().add(extTrans)
		
		
		
		Place place = OrnFactoryImpl.eINSTANCE.createPlace()
		place.setName getName("place1")
		addPrag("pd", "", place)
		page.getObject().add(place)
		
		Transition trans = OrnFactoryImpl.eINSTANCE.createTransition()
		trans.setName getName("trans1")
		addPrag("fd", "10", trans)
		page.getObject().add(trans)
		
		Arc arc0 = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc0.setName getName("arc0")
		arc0.setSource extTrans
		arc0.setTarget place
		page.getObject().add(arc0)
		
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc.setName getName("arc1")
		arc.setSource place
		arc.setTarget trans
		page.getObject().add(arc)
		
		
		pn.getPage().add(page)
		
		return pn
	}
	
	
	static def getLogoPetriNetWithRepeat(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage()
		
		Transition extTrans = OrnFactoryImpl.eINSTANCE.createTransition()
		extTrans.setName getName("Start")
		addPrag("External", "", extTrans)
		page.getObject().add(extTrans)
		
		RefPlace refPlace = PnmlcoremodelFactory.eINSTANCE.createRefPlace()
		refPlace.setName(getName("ref"))
		
		Page subPage = getPage()
		addPrag "repeat", "2", subPage
		subPage.setName(getName( "page2"))
		
		
		Place place = OrnFactoryImpl.eINSTANCE.createPlace()
		place.setName getName("place1")
		addPrag("pd", "", place)
		subPage.getObject().add(place)
		
		refPlace.ref = place
		
		
		Transition trans = OrnFactoryImpl.eINSTANCE.createTransition()
		trans.setName getName("trans1")
		addPrag("fd", "10", trans)
		subPage.getObject().add(trans)
		
		Arc arc0 = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc0.setName getName("arc0")
		arc0.setSource extTrans
		arc0.setTarget refPlace
		subPage.getObject().add(arc0)
		
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc.setName getName("arc1")
		arc.setSource place
		arc.setTarget trans
		subPage.getObject().add(arc)
		
		
		page.getObject().add(subPage)
		
		pn.getPage().add(page)
		
		return pn
	}
	
	
	static def getValidProtocolNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage("toppage")
		
		Page client = getPage("Client")
		Page server = getPage("Server")

		Place clientToServer = getPlace("ClientToServer")
		Place serverToClient = getPlace("ServerToClient")
		addPrag("channel", null, clientToServer)
		addPrag("channel", null, serverToClient)
		
		getValidClientNet(client, clientToServer, serverToClient)
		page.object.add client
		page.object.add server
		
		page.object.add clientToServer
		page.object.add serverToClient
		
		pn.getPage().add page
		return pn
	}
	
	static def getInValidProtocolNet(){
		PetriNet pn = PnmlcoremodelFactory.eINSTANCE.createPetriNet()
		
		Page page = getPage("toppage")
		
		Page client = getPage("Client")
		Page server = getPage("Server")

		Place clientToServer = getPlace("ClientToServer")
		Place serverToClient = getPlace("ServerToClient")
		addPrag("channel", null, clientToServer)
		addPrag("channel", null, serverToClient)
		
		getInValidClientNet(client, clientToServer, serverToClient)
		
		page.object.add client
		page.object.add server
		
		page.object.add clientToServer
		page.object.add serverToClient
		
		pn.getPage().add page
		
		return pn
	}
	
	static def getValidClientNet(client, clientToServer, serverToClient){
		Place start = getPlace("start")
		addPrag "Id", null, start
		
		Transition external = getTransition("extern")
		addPrag "External", null, external
		
		Arc arc = getArc(start, external, "arc")
		
		client.object.add(start)
		client.object.add(external)
		client.object.add(arc)
		
		
	}
	
	static def getInValidClientNet(client, clientToServer, serverToClient){
		Place start = getPlace("start")
		
		
		Transition external = getTransition("extern")
		addPrag "External", null, external
		
		Arc arc = getArc(start, external, "arc")
		
		client.object.add(start)
		client.object.add(external)
		client.object.add(arc)
		
		
	}
	
	static def getArc(source, target, name = null){
		Arc arc = PnmlcoremodelFactory.eINSTANCE.createArc()
		arc.setName getName(name == null ? "arc1" : name)
		arc.setSource source
		arc.setTarget target
		
		source.out.add arc
		target.in.add arc
		return arc
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
	static def getPage(name=null){
		Page p = OrnFactoryImpl.eINSTANCE.createPage()
		p.setName(getName(name == null ? "Testpage" : name))
		return p
	}
	
	
	static def getName(text){
		Name n =  PnmlcoremodelFactory.eINSTANCE.createName()
		n.setText text
		return n
	}
	
	static def getPlace(name = null){
		Place place = OrnFactoryImpl.eINSTANCE.createPlace()
		place.setName getName(name == null ? "place1" : name)
		return place
	}
	
	static def getTransition(name = null){
		Transition trans = OrnFactoryImpl.eINSTANCE.createTransition()
		trans.setName getName(name == null ? "trans1" : name)
		return trans
	}
	
	
	static final def nppnOntTmpl ="""
	Prefix(:=<http://org.k1s/orn/ext/>)
 Prefix(basic:=<http://t.k1s.org/OntologyReastrictedNets/basic/>)
 Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
 Prefix(owl:=<http://www.w3.org/2002/07/owl#>)
 Prefix(cpn:=<http://hib.no/SADLTest/cpn>)
 
 Ontology(<http://org.k1s/orn/ext/>
  Import( <http://t.k1s.org/OntologyReastrictedNets/basic.owl> )
  Import( <http://t.k1s.org/OntologyReastrictedNets/cpn.owl> )
   
  
  SubClassOf( :Id basic:Pragmatic )
  SubClassOf( :Id ObjectAllValuesFrom( :belongsTo cpn:Place ) ) 

  SubClassOf( :External basic:Pragmatic )
  SubClassOf( :External ObjectAllValuesFrom( :belongsTo :TransitionConnectedToId ) ) 


  SubClassOf( :IdPlace ObjectAllValuesFrom( :hasPragmatic :id))
  
    SubClassOf( :IdPlace ObjectIntersectionOf(
      cpn:Place
  	  ObjectAllValuesFrom( :hasPragmatic :Id) 
  	  ObjectExactCardinality( 1 :hasPragmatic )
    )
  )


SubClassOf( :ArcFromId ObjectIntersectionOf(
  		cpn:Arc
  		ObjectAllValuesFrom( cpn:source :IdPlace) 
  		ObjectExactCardinality( 1 cpn:source :IdPlace)
  	)
  )
 
 SubClassOf( :TransitionConnectedToId ObjectIntersectionOf(
  		cpn:Transition
  		ObjectAllValuesFrom( :in :ArcFromId) 
		
  		)
  	)
  	
 )
	"""
}
