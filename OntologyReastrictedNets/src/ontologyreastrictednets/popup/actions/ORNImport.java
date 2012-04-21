package ontologyreastrictednets.popup.actions;

import java.io.File;
import java.util.List;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import org.k1s.orn.ORNImporter;
import org.k1s.orn.ORNVerifier;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelPackage;

import orn.OrnPackage;
import orn.Page;

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

public class ORNImport extends AbstractHandler {

	private Shell shell;
	private IEditorPart targetEditor;
	/**
	 * Constructor for Action1.
	 */
	public ORNImport() {
		super();
	}

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		FileDialog dlg = new FileDialog(window.getShell(), SWT.OPEN);
	    String fileName = dlg.open();
	    
	    PetriNet pn = (PetriNet) ((TreeSelection)window.getSelectionService().getSelection()).getFirstElement();
	    
	    if (fileName != null) {
	    	ORNImporter translator = new ORNImporter();
	    	List pages =  translator.translate(fileName);
	    	EditingDomain eDomain = AdapterFactoryEditingDomain.getEditingDomainFor(pn);
	    	org.eclipse.emf.common.command.Command cmd = new AddCommand(
				    eDomain,
				    pn,
				    PnmlcoremodelPackage.eINSTANCE.getPetriNet_Page(),
				    pages);
	eDomain.getCommandStack().execute(cmd);
	    	/*for(Page page : pages){
	    		//pn.getPage().add(pages.get(0));
	    		//Command Ad
	    		
	    	}*/
	    	
	    	/*for(Adapter adapter : pn.eAdapters()){
	    		adapter.notifyChanged(notification);
	    	}*/
	    }
	    
	    String msg = "no errors: ";
	    
	    
	    MessageDialog.openInformation(
				window.getShell(),
				msg,
				msg);
	    
		return true;
	}



	
//	/**
//	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
//	 */
//	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		shell = targetPart.getSite().getShell();
//		
//	}
//
//	/**
//	 * @see IActionDelegate#run(IAction)
//	 */
//	public void run(IAction action) {
//		IDocument document =((MCHEditor)targetEditor).getDocumentProvider().getDocument(targetEditor.getEditorInput());
//		String content = document.get();
//		System.out.println(document.get());
//		MessageDialog.openInformation(
//			shell,
//			"ORN Model",
//			"validate was executed on: "+targetEditor.getClass().toString());
//	}
//
//	/**
//	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
//	 */
//	public void selectionChanged(IAction action, ISelection selection) {
//	}
//
//	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
//		this.targetEditor = targetEditor;
//		
//	}
//


}
