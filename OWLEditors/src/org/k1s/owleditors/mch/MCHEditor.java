package org.k1s.owleditors.mch;

import org.eclipse.ui.editors.text.TextEditor;

public class MCHEditor extends TextEditor {
	
	
	public MCHEditor() {
		
		// install the source configuration
		setSourceViewerConfiguration(new MCHConfiguration());
		// install the document provider
		//setDocumentProvider();
	}

	protected void createActions() {
		super.createActions();
		// ... add other editor actions here
	}
}
