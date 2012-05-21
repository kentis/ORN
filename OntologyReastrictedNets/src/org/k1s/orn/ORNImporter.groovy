package org.k1s.orn

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import orn.Inscription;
import orn.OrnFactory;
import orn.Place;
import orn.Pragmatics;
import orn.RefPlace;
//import orn.RefPlace;
import orn.Transition;
import orn.impl.OrnFactoryImpl;
import orn.impl.RefPlaceImpl;
//import orn.Page;

import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.ParameterAssignment;
import org.cpntools.accesscpn.model.importer.DOMParser;

import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Node;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PlaceNode;
import org.pnml.tools.epnk.pnmlcoremodel.impl.PnmlcoremodelFactoryImpl;
//import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Page;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HlpngdefinitionPackage;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HlpngdefinitionFactoryImpl;

class ORNImporter {
	UUID uuid = UUID.randomUUID();
	
	
	public List<Page> translate(String filename){
		try {
			List<Page> pages =translate(new FileInputStream(new File(filename)));
			println pages
			println pages[0].getObject()
			
			return pages
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	Hashtable<String, Page> allPages = new Hashtable<String, Page>();
	public List<Page> translate(InputStream cpn){
		List<Page>  pages = new ArrayList<Page>();
		
		org.cpntools.accesscpn.model.PetriNet pn = null;
		
		try {
			pn = DOMParser.parse(cpn, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();d
			throw new RuntimeException(e);
		}
		
		
		for(org.cpntools.accesscpn.model.Page cpnPage : pn.getPage()){
			def page;
			println "setting prag for page: ${cpnPage.name.text}"
			if(allPages.containsKey(cpnPage.getId())){
				page = allPages.get(cpnPage.getId());
			}else{
				page = OrnFactoryImpl.eINSTANCE.createPage();
				page.setId(UUID.randomUUID().toString());
				allPages.put(cpnPage.getId(), page);
				pages.add(page);
			}
			
			Name name = new PnmlcoremodelFactoryImpl().createName();
			name.setText(cpnPage.getName().getText());
			page.setName(name);
			
			//setPragmatic(page, cpnPage.name.text)
			copyPage(cpnPage, page);
		}
		
		//set all references in ref-places
		for(String refId : refs.keySet()){
			RefPlace refPlace = refs.get(refId);
			org.cpntools.accesscpn.model.RefPlace sRef = sRefs.get(refId);
			
			String targetId = refMap.get(refId).getParameter();
			PlaceNode target = places.get(targetId);
			if(target == null)target = refs.get(targetId);
			if(target != null) refPlace.setRef(target);
		}
		return pages;
	}
	
	
	/**
	 * Copies places and transitions
	 *
	 * TODO: Arcs, ports, substitution transitions
	 * @param source
	 * @param target
	 */
	Hashtable<String, ParameterAssignment> refMap = new Hashtable<String,ParameterAssignment>();
	public void copyPage(org.cpntools.accesscpn.model.Page source, def target){
		Hashtable<String, Object> nodes = new Hashtable<String, Object>();
		
		
		for(org.cpntools.accesscpn.model.Place sPlace : source.place()){
			println "adding place"
			Place place = transformPlace(sPlace);
			place.setId(UUID.randomUUID().toString());
			target.getObject().add(place);
			nodes.put(sPlace.getId(), place);
		}
		
		for(org.cpntools.accesscpn.model.RefPlace sPlace : source.portPlace()){
			
			RefPlace place = transformPortPlace(sPlace);
			target.getObject().add(place);
			place.setId(UUID.randomUUID().toString());
			nodes.put(sPlace.getId(), place);
		}
		
		for(org.cpntools.accesscpn.model.Transition sTrans : source.transition()){
			Transition trans = transformTansition(sTrans);
			trans.setId(UUID.randomUUID().toString());
			target.getObject().add(trans);
			nodes.put(sTrans.getId(), trans);
		}
		
		for(org.cpntools.accesscpn.model.Instance subst : source.instance()){
			Page subPage = transformInstance(subst);
			subPage.setId(UUID.randomUUID().toString());
			nodes.put(subst.getId(), subPage);
			target.getObject().add(subPage);
			
			for(ParameterAssignment ass : subst.getParameterAssignment()){
				refMap.put(ass.getValue(), ass);
			}
		}
		
		
		
		for(org.cpntools.accesscpn.model.Arc sArc: source.getArc()){
			Arc arc = transformArc(sArc, nodes);
			arc.setId(UUID.randomUUID().toString());
			if(arc.getSource() != null && arc.getTarget() != null) target.getObject().add(arc);
		}
	}
	
	
	Hashtable<String, RefPlace> refs = new Hashtable<String, RefPlace>();
	Hashtable<String, org.cpntools.accesscpn.model.RefPlace> sRefs = new Hashtable<String, org.cpntools.accesscpn.model.RefPlace>();
	private RefPlace transformPortPlace(org.cpntools.accesscpn.model.RefPlace place) {
		RefPlace p = OrnFactoryImpl.eINSTANCE.createRefPlace();
		p.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		p.setName(createName(delPrag(place.getName().getText())));
		
		refs.put(place.getId(), p);
		sRefs.put(place.getId(), place);
		
		Pragmatics prag = OrnFactory.eINSTANCE.createPragmatics();
		prag.setText(getPrag(place.getName().getText()));
		
		//p.getPragmatics().add(prag);
		return p;
	}
	
	
	private Page transformInstance(Instance subst) {
		
		def page;
		if(allPages.containsKey(subst.getSubPageID())){
			page = allPages.get(subst.getSubPageID());
		}else{
			page = OrnFactoryImpl.eINSTANCE.createPage();
			allPages.put(subst.getSubPageID(), page);
		}
		page.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		setPragmatic page, subst.getName().getText()
		return page;
	}
	
	private Arc transformArc(org.cpntools.accesscpn.model.Arc sArc, Hashtable<String, Object> nodes) {
		// TODO Auto-generated method stub
		orn.Arc arc = OrnFactory.eINSTANCE.createArc();
		Inscription insc = OrnFactory.eINSTANCE.createInscription();
		insc.setText(sArc.getHlinscription().getText());
		arc.setInscription(insc);
		//PnmlcoremodelFactoryImpl.eINSTANCE.creater
		if(nodes.get(sArc.getSource().getId()) instanceof Node)
			arc.setSource((Node)nodes.get(sArc.getSource().getId()));
		if(nodes.get(sArc.getTarget().getId()) instanceof Node)
		
			arc.setTarget((Node)nodes.get(sArc.getTarget().getId()));
		return arc;
	}
	
	private Transition transformTansition(org.cpntools.accesscpn.model.Transition sTrans) {
		Transition trans = OrnFactory.eINSTANCE.createTransition();
		trans.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		Name name = createName(delPrag(sTrans.getName().getText()));
		trans.setName(name);
		setPragmatic(trans, sTrans.getName().getText() )
//		Pragmatics prag = OrnFactory.eINSTANCE.createPragmatics();
//		prag.setText(getPrag(sTrans.getName().getText()));
//		trans.getPragmatics().add(prag);
		return trans;
	}
	
	Hashtable<String, Place> places = new Hashtable<String, Place>();
	public Place transformPlace(org.cpntools.accesscpn.model.Place place){
		Place p = OrnFactory.eINSTANCE.createPlace();
		p.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		p.setName(createName(delPrag(place.getName().getText())));
		places.put(place.getId(), p);
		
		setPragmatic(p,place.getName().getText() )
		//		Pragmatics prag = OrnFactory.eINSTANCE.createPragmatics();
		//		prag.setText(getPrag(place.getName().getText()));
		//		
		//		p.getPragmatics().add(prag);
		return p;
	}
	
	public Name createName(String nameS){
		Name name = new PnmlcoremodelFactoryImpl().createName();
		name.setText(nameS);
		return name;
	}
	
	def setPragmatic(element, name){
		Pragmatics prag = OrnFactory.eINSTANCE.createPragmatics()
		prag.setText(getPrag(name))
		
		prag.setStructure(prag.parse(prag.getText()))
		
		if(prag.getText() != null && !prag.getText().equals(""))
			element.getPragmatics().add(prag);
	}
	
	protected String getPrag(String elemName){
		if(elemName == null) return "";
		String retval = null;
		
		if(elemName.indexOf("<<") == -1 ) return null;
		
		int pragStart = elemName.indexOf( "<<") + 2;
		int pragEnd = elemName.indexOf(">>");
		println elemName
		String pragDef = elemName.substring(pragStart, pragEnd);
		
		if(!pragDef.contains("(")) pragDef = pragDef + "()";
		return pragDef;
	}
	
	protected String delPrag(String elemName){
		if(elemName == null) return "";
		String retval = null;
		
		if(elemName.indexOf("(*<<") != -1 ) return elemName.substring(0, elemName.indexOf("(*<<"));
		if(elemName.indexOf("<<") != -1 ) return elemName.substring(0, elemName.indexOf("<<"));
		
		return elemName;
	}
}
