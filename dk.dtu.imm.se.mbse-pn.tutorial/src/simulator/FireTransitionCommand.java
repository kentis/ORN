package simulator;

import helpers.HelperFunctions;
import inhibitornets.Place;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.util.TransactionUtil;

import org.pnml.tools.epnk.helpers.FlatAccess;
import org.pnml.tools.epnk.helpers.NetFunctions;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;

public class FireTransitionCommand  extends RecordingCommand {

	private Transition transition;
	private FlatAccess flat;
	
	public FireTransitionCommand(Transition transition) { 
		super(TransactionUtil.getEditingDomain(transition), "Fire transition");
		this.transition = transition;
		this.flat = new FlatAccess(NetFunctions.getPetriNet(transition)); 
	}

	@Override
	protected void doExecute() {
		if (transition == null) {
			return;
		}

		for (Place place: HelperFunctions.getPreset(flat, transition)) {
			HelperFunctions.removeToken(place);
		}

		for (Place place: HelperFunctions.getPostset(flat, transition)) {
			HelperFunctions.addToken(place);
		}
	}

	@Override
	public boolean canExecute() {
		if (transition != null) {
			for (Place place: HelperFunctions.getPreset(flat, transition)) {
				if (!HelperFunctions.isMarked(place)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
