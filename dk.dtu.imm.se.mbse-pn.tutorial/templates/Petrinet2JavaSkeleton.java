import helpers.HelperFunctions;

import java.util.HashMap;

import org.pnml.tools.epnk.helpers.FlatAccess;
import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.Node;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.Place;
import org.pnml.tools.epnk.pnmlcoremodel.PlaceNode;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;
import org.pnml.tools.epnk.pnmlcoremodel.TransitionNode;

import codegenerator.Utils;

// public class CLASS {
public class Petrinet2JavaSkeleton {

	private String className;
	@SuppressWarnings("unused")
	private String packageName;
	
	private int noPlaces;
	private int noTransitions;

	private HashMap<Integer,inhibitornets.Place> intPlaces;
	private HashMap<Integer,Transition> intTransitions;
	private HashMap<Place,Integer> placesInt;
	private HashMap<Transition,Integer> transitionsInt;
	
	private FlatAccess flatnet;

	@SuppressWarnings("unused")
	private void initialize(PetriNet pn) {
		flatnet = new FlatAccess(pn);
		
		Name name = pn.getName();
		if (name != null) {
			className = Utils.getClassName(name.getText());
			packageName = Utils.getPath(name.getText());
		}
		
		if (className == null) {
			className = "Net";
			packageName = "";
		}

		noPlaces = 0;
		intPlaces = new HashMap<Integer,inhibitornets.Place>();
		placesInt = new HashMap<Place,Integer>();
		
		for (Place place : flatnet.getPlaces()) {
			if (place instanceof inhibitornets.Place) {
				intPlaces.put(noPlaces, (inhibitornets.Place) place);
				placesInt.put(place,noPlaces);
				noPlaces++;			
			}
		}
		
		noTransitions = 0;
		intTransitions = new HashMap<Integer,Transition>();
		transitionsInt = new HashMap<Transition,Integer>();
		
		for (Transition transition : flatnet.getTransitions()) {
			intTransitions.put(noTransitions,transition);
			transitionsInt.put(transition,noTransitions);
			noTransitions++;		
		}
	}

	@SuppressWarnings("unused")
	private inhibitornets.Place getPlace(int p) {
		if (intPlaces != null) {
			return intPlaces.get(p);
		} 

		return null;
	}
	
	@SuppressWarnings("unused")
	private int getPlaceMarking(int p) {
		inhibitornets.Place place = intPlaces.get(p);
		return HelperFunctions.getMarking(place);
	}

	private int getPlaceNo(Node n) {
		if (!(n instanceof Place) && n instanceof PlaceNode) {
			n = flatnet.resolve((PlaceNode) n);
		}
		
		if (n instanceof Place && placesInt != null) {
			Place p = (Place) n;
			return placesInt.get(p).intValue();
		}

		return -1;
	}

	@SuppressWarnings("unused")
	private Transition getTransition(int t) {
		if (intTransitions != null) {
			return intTransitions.get(t);
		} 

		return null;
	}

	private int getTransitionNo(Node n) {
		if (!(n instanceof Transition) && n instanceof TransitionNode) {
			n = flatnet.resolve((TransitionNode) n);
		}
		
		if (n instanceof Transition && transitionsInt != null) {
			Transition p = (Transition) n;
			return transitionsInt.get(p).intValue();
		}

		return -1;
	}
	
	@SuppressWarnings("unused")
	private String getNodeName(Node node) {
		String result = null;
		
		Name name = node.getName();
		if (name != null) {
			result = name.getText();
		}
		
		if (result == null) {
			result = node.getId();
		}
		
		if (result == null) {
			if (node instanceof Place) {
				int no = getPlaceNo(node);
				result = "p" + (no >= 0 ? no : "X");
			}
		}
		
		if (result == null) {
			if (node instanceof Transition) {
				int no = getTransitionNo(node);
				result = "t" + (no >= 0 ? no : "X");
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private String getClassName() {
		return className;
	}

	public String generate(Object argument) {
		return "";
	} 
}