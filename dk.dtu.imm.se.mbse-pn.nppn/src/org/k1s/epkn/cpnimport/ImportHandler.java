package org.k1s.epkn.cpnimport;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelPackage;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HlpngdefinitionPackage;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Page;

public class ImportHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		FileDialog dlg = new FileDialog(window.getShell(), SWT.OPEN);
	    String fileName = dlg.open();
	    /*if (fileName != null) {
	    	MessageDialog.openInformation(
					window.getShell(),
					"filename",
					": "+fileName);
	    }*/

	    PetriNet pn = (PetriNet) ((TreeSelection)window.getSelectionService().getSelection()).getFirstElement();
	    
	    
	    if (pn != null) {
    	MessageDialog.openInformation(
				window.getShell(),
				"pn",
				": "+pn.toString());
        }
	    
	    if (fileName != null) {
	    	CPNTranslator translator = new CPNTranslator();
	    	List<Page> pages =  translator.translate(fileName);
	    	EditingDomain eDomain = AdapterFactoryEditingDomain.getEditingDomainFor(pn);
	    	org.eclipse.emf.common.command.Command cmd = SetCommand.create(
				    eDomain,
				    pn,
				    PnmlcoremodelPackage.Literals.PETRI_NET__PAGE,
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
		return null;
	}

	

}
