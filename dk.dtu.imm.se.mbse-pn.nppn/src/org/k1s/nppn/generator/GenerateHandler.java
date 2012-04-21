package org.k1s.nppn.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.PnmlcoremodelPackage;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HlpngdefinitionPackage;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Page;

public class GenerateHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		DirectoryDialog dlg = new DirectoryDialog(window.getShell(), SWT.OPEN);
	    String fileName = dlg.open();
	    File dir = new File(fileName);
	    if (!dir.exists() || !dir.isDirectory()) {
	    	MessageDialog.openInformation(
					window.getShell(),
					"Not a directory ",
					": "+fileName);
	    	return null;
	    }

	    PetriNet pn = (PetriNet) ((TreeSelection)window.getSelectionService().getSelection()).getFirstElement();
	    
	    
	    Generator gen = new Generator(new GroovyKCConfig());
	    
	    List<String> entities = (List)gen.generate(pn);
	   	
	    List<String> names = gen.getNames();
	    
	    for(int i = 0; i< names.size(); i++){
	    	String name = names.get(i);
//	    	System.out.println("NAME: "+name);
//	    	System.out.println("ENT: "+entities.get(i));
//	    	
	    	File f = new File(fileName +"/"+ name + ".groovy");
	    	try {
//	    		System.out.println(f.getAbsolutePath());
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(entities.get(i).getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return null;
	}

	

}
