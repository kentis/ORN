package helpers;

import inhibitornets.InhibitornetsFactory;
import inhibitornets.Marking;
import inhibitornets.Place;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;

import org.pnml.tools.epnk.helpers.FlatAccess;
import org.pnml.tools.epnk.pnmlcoremodel.Arc;
import org.pnml.tools.epnk.pnmlcoremodel.Node;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

public class HelperFunctions {

	public static List<Place> getPreset(FlatAccess flatnet, Transition transition) {
		List<Place> result = new Vector<Place>();
		for (Arc arc: flatnet.getIn(transition)) {
			if (arc instanceof inhibitornets.Arc) {
				Node node = arc.getSource();			
				if (node != null && node instanceof Place) {
					result.add((Place) node);
				}
			}	
		}
		return result;
	}

	public static List<Place> getPostset(FlatAccess flatnet, Transition transition) {
		List<Place> result = new Vector<Place>();
		for (Arc arc: flatnet.getOut(transition)) {
			if (arc instanceof inhibitornets.Arc) {
				Node node = arc.getTarget();			
				if (node != null && node instanceof Place) {
					result.add((Place) node);
				}
			}	
		}
		return result;
	}
	
	public static boolean isMarked(Place place) {
		return getMarking(place) > 0;
	}

	public static int getMarking(Place place) {
		if (place != null) {
			Marking marking = place.getMarking();
			if (marking != null) {
				return marking.getText().intValue();
			}
			return 0;
		}
		return -1;	
	}

	public static void removeToken(Place place) {
		if (place != null) {
			Marking marking = place.getMarking();
			if (marking == null) {
				return;
			}
			BigInteger value = marking.getText();
			if (value.compareTo(BigInteger.ONE) >= 0) {
				marking.setText(value.subtract(BigInteger.ONE));
			}
		}
	}

	public static void addToken(Place place) {
		if (place != null) {
			Marking marking = place.getMarking();
			if (marking == null) {
				marking = InhibitornetsFactory.eINSTANCE.createMarking();
				marking.setText(BigInteger.ONE);
				place.setMarking(marking);
			} else {
				BigInteger value = marking.getText();
				marking.setText(value.add(BigInteger.ONE));
			}
		}
	}

}
