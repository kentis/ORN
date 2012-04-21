package org.k1s.owleditors.fowl;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class FunctionalConfiguration extends SourceViewerConfiguration {
	public IPresentationReconciler getPresentationReconciler(
	         ISourceViewer sourceViewer) {
	         PresentationReconciler pr = new PresentationReconciler();
	         DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(new FunctionalScanner());
	         pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
	         pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
	         return pr;
	      }
	     public IContentAssistant getContentAssistant(ISourceViewer sv) {
	         ContentAssistant ca = new ContentAssistant();
	         IContentAssistProcessor cap = new FunctionalCompletionProcessor();
	         ca.setContentAssistProcessor(cap, IDocument.DEFAULT_CONTENT_TYPE);
	         ca.setInformationControlCreator(getInformationControlCreator(sv));
	         return ca;
	      }
	     
	     
	     
	      public ITextHover getTextHover(ISourceViewer sv, String contentType) {
	         return null;
	      }
}
