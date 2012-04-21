package simulator;

import inhibitornets.InhibitorNet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.pnml.tools.epnk.helpers.NetFunctions;
import org.pnml.tools.epnk.pnmlcoremodel.Page;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNetType;
import org.pnml.tools.epnk.pnmlcoremodel.Transition;


public class SimulateAction implements IObjectActionDelegate {
	
	private Transition transition;
	private FireTransitionCommand command;
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	@Override
	public void run(IAction action) {
		if (command != null) {
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(transition);	
			domain.getCommandStack().execute(command);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		transition = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				Object selected = structuredSelection.getFirstElement();
				if ( selected instanceof Page) {
					transition = (Transition) selected;
				} else if ( selected instanceof EditPart ) {
					EditPart part = (EditPart) selected;
					Object model = part.getModel();
					if (model != null && model instanceof View) {
						EObject object = ((View) model).getElement();
						if (object != null && object instanceof Transition) {
							transition = (Transition) object;
						}
					}
				}
			}
		}
		action.setEnabled(isEnabled());
	}

	public boolean isEnabled() {
		if (transition != null) {
			PetriNetType type = NetFunctions.getPetriNetType(transition);
			if (type != null && type instanceof InhibitorNet) {
				command = new FireTransitionCommand(transition);
				return command.canExecute();
			}
		}
		return false;
	}

}
