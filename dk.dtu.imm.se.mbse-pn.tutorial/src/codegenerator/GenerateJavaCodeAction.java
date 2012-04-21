package codegenerator;


import inhibitornets.InhibitorNet;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.pnml.tools.epnk.pnmlcoremodel.Name;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNetType;

/**
 * User action to start the generation of the Java code for
 * a Petri net.
 * 
 * @author kindler
 */
public class GenerateJavaCodeAction implements IObjectActionDelegate {

	private PetriNet petrinet;

	public void run(IAction action) {
		if (isEnabled()) {
			IJavaProject javaProject = null;

			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if ( window != null ) {
				IWorkbenchPage page = window.getActivePage();
				if ( page != null ) {
					IEditorPart editor = page.getActiveEditor();
					if ( editor != null ) {
						IEditorInput input = editor.getEditorInput();
						if ( input instanceof IFileEditorInput) {
							IFileEditorInput fileInput = (IFileEditorInput) input;
							IProject project = fileInput.getFile().getProject();
							javaProject = JavaCore.create(project);
						}
					}
				}					
			}

			if (javaProject == null  || !javaProject.exists()) {
				MessageDialog.openInformation(
						null,
						"ePNK: Inhibitor Code Generator",
						"For generating code, the Petri net must be contained in " +
						"a java project."
				);
				return;
			}

			Name name = petrinet.getName();
			String text = name != null ? name.getText() : null;
			String className = text != null ? Utils.getClassName(text) : null;
			String path = text != null ? Utils.getPath(text) : null;
			
			if (className == null  || className.equals("") || path == null) {
				MessageDialog.openInformation(
						null,
						"ePNK: Inhibitor Code Generator",
						"The net must have a non-empty name (possibly with package prefix)."
				);
				return;
			}

			try {
				IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots(); 
				if (roots!= null && roots.length > 0) {
					for (IPackageFragmentRoot root: roots) {
						if (!root.isReadOnly()) {
							IPackageFragment packageFragment = root.createPackageFragment(path, false, null);
							IResource resource = packageFragment.getResource();
							if (resource instanceof IFolder) {
								IFolder folder =  (IFolder) resource;
								CodeGenerator.generate(petrinet, folder);
								return;
							}
						}
					}
				}
			} catch (JavaModelException e) {
			}

			MessageDialog.openInformation(
					null,
					"ePNK: Inhibitor Code Generator",
					"Code generation could not be started"
			);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// get the component from the current selection, if possible
		petrinet = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1
					&& structuredSelection.getFirstElement() instanceof PetriNet) {
				petrinet = (PetriNet) structuredSelection.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	private boolean isEnabled() {
		if (petrinet != null) {
			PetriNetType type = petrinet.getType();
			if (type instanceof InhibitorNet) {
				return true;
			}
		}
		return false;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
