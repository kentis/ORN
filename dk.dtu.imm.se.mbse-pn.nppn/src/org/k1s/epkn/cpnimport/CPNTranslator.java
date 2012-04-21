 package org.k1s.epkn.cpnimport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.ParserConfigurationException;

import nppnnets.Inscription;
import nppnnets.NppnnetsFactory;
import nppnnets.Place;
import nppnnets.Pragmatics;
import nppnnets.Transition;
import nppnnets.impl.NppnnetsFactoryImpl;

//import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.ParameterAssignment;
import org.cpntools.accesscpn.model.importer.DOMParser;
import org.cpntools.accesscpn.model.importer.NetCheckException;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Node;
import org.pnml.tools.epnk.pnmlcoremodel.PlaceNode;
import nppnnets.RefPlace;
import org.pnml.tools.epnk.pnmlcoremodel.impl.PnmlcoremodelFactoryImpl;
import org.pnml.tools.epnk.pnmlcoremodel.serialisation.PNMLFactory;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Page;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HlpngdefinitionFactoryImpl;
import org.xml.sax.SAXException;


public class CPNTranslator {
UUID uuid = UUID.randomUUID();
	
	public List<Page> translate(String cpn){
		try {
			return translate(new FileInputStream(new File(cpn)));
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
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
		
		for(org.cpntools.accesscpn.model.Page cpnPage : pn.getPage()){
			Page page;
			if(allPages.containsKey(cpnPage.getId())){
				page = allPages.get(cpnPage.getId());
			}else{
				page = HlpngdefinitionFactoryImpl.eINSTANCE.createPage();
				page.setId(UUID.randomUUID().toString());
				allPages.put(cpnPage.getId(), page);
				pages.add(page);
			}
		
			Name name = new PnmlcoremodelFactoryImpl().createName();
			name.setText(cpnPage.getName().getText());
			page.setName(name);
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
	public void copyPage(org.cpntools.accesscpn.model.Page source, Page target){
		Hashtable<String, Object> nodes = new Hashtable<String, Object>(); 
		 
		
		for(org.cpntools.accesscpn.model.Place sPlace : source.place()){
			
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
		RefPlace p = NppnnetsFactoryImpl.eINSTANCE.createRefPlace();
		p.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		p.setName(createName(delPrag(place.getName().getText())));
		
		refs.put(place.getId(), p);
		sRefs.put(place.getId(), place);
		
		Pragmatics prag = NppnnetsFactory.eINSTANCE.createPragmatics();
		prag.setText(getPrag(place.getName().getText()));
		
		//p.getPragmatics().add(prag);
		return p;
	}


	private Page transformInstance(Instance subst) {
		
		Page page;
		if(allPages.containsKey(subst.getSubPageID())){
			page = allPages.get(subst.getSubPageID());
		}else{
			page = HlpngdefinitionFactoryImpl.eINSTANCE.createPage();
			allPages.put(subst.getSubPageID(), page);
		}
		page.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		return page;
	}

	private Arc transformArc(org.cpntools.accesscpn.model.Arc sArc, Hashtable<String, Object> nodes) {
		// TODO Auto-generated method stub
		nppnnets.Arc arc = NppnnetsFactory.eINSTANCE.createArc();
		Inscription insc = NppnnetsFactory.eINSTANCE.createInscription();
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
		Transition trans = NppnnetsFactory.eINSTANCE.createTransition();
		trans.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		Name name = createName(delPrag(sTrans.getName().getText()));
		trans.setName(name);
		Pragmatics prag = NppnnetsFactory.eINSTANCE.createPragmatics();
		prag.setText(getPrag(sTrans.getName().getText()));
		trans.getPragmatics().add(prag);
		return trans;
	}

	Hashtable<String, Place> places = new Hashtable<String, Place>();
	public Place transformPlace(org.cpntools.accesscpn.model.Place place){
		Place p = NppnnetsFactory.eINSTANCE.createPlace();
		p.setGraphics(PnmlcoremodelFactoryImpl.eINSTANCE.createNodeGraphics());
		p.setName(createName(delPrag(place.getName().getText())));
		places.put(place.getId(), p);
		Pragmatics prag = NppnnetsFactory.eINSTANCE.createPragmatics();
		prag.setText(getPrag(place.getName().getText()));
		
		p.getPragmatics().add(prag);
		return p;
	}
	
	public Name createName(String nameS){
		Name name = new PnmlcoremodelFactoryImpl().createName();
		name.setText(nameS);
		return name;
	}
	
	
	protected String getPrag(String elemName){
		if(elemName == null) return "";
		String retval = null;
		
		if(elemName.indexOf("<<") == -1 ) return null;
		
		int pragStart = elemName.indexOf( "<<") + 2;
		int pragEnd = elemName.indexOf(">>");
		//println elemName
		String pragDef = elemName.substring(pragStart, pragEnd);
		
		return pragDef;
	}
	
	protected String delPrag(String elemName){
		if(elemName == null) return "";
		String retval = null;
		
		if(elemName.indexOf("(*<<") != -1 ) return elemName.substring(0, elemName.indexOf("(*<<"));
		if(elemName.indexOf("<<") != -1 ) return elemName.substring(0, elemName.indexOf("<<"));
		
		return elemName;
	}
	
	
/*	public String getAsString(String cpnFile){
		File file = new File(cpnFile);
		byte[] b = new byte[(int) file.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			  
		    fis.read(b);  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	    return new String(b);
	}*/
}
