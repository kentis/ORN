package org.k1s.owleditors.mch;

import java.util.ArrayList;
import java.util.List;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class MCHCompletionProcessor implements IContentAssistProcessor {

	
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		List<ICompletionProposal> completions = new ArrayList<ICompletionProposal>();
		String prefix = lastWord(viewer.getDocument(), offset);
		
		if(prefix.equals("ORN") || offset == 0){
			ICompletionProposal compl = new CompletionProposal(MCHConstants.OWL_TEMPLATE, 
					0, 0, 
					0,
					null,
					"Initialize ORN ontology",
					null,
					null);
			completions.add(compl);
		}
		
		for(ManchesterOWLSyntax syn : ManchesterOWLSyntax.values()){
			String keyword = syn.toString();
			
			if(keyword.startsWith(prefix)){
//				System.out.println(prefix);
//				System.out.println(prefix.length());
//				System.out.println(keyword);
//				System.out.println(keyword.length());
				ICompletionProposal compl = new CompletionProposal(keyword.substring(prefix.length()), 
						offset, 0, 
						keyword.length() - prefix.length() +1,
						null,
						keyword,
						null,
						null);

				completions.add(compl);
			}
		
		}
		return completions.toArray(new ICompletionProposal[0]);
	}
	 private String lastWord(IDocument doc, int offset) {
	      try {
	         for (int n = offset-1; n >= 0; n--) {
	           char c = doc.getChar(n);
	           System.out.println("found char: " +c);
	           System.out.println("n: " +n);
	           if (!Character.isJavaIdentifierPart(c))
	             return doc.get(n + 1, offset-n-1);
	           if(n == 0)
	        	   return doc.get(0, offset);
	         }
	      } catch (BadLocationException e) {
	         e.printStackTrace();
	      }
	      return "";
	   }
	        
//	        private String lastIndent(IDocument doc, int offset) {
//	      try {
//	         int start = offset-1; 
//	         while (start >= 0 && doc.getChar(start)!= '\n') start--;
//	         int end = start;
//	         while (end < offset && Character.isSpaceChar(doc.getChar(end))) end++;
//	         return doc.get(start+1, end-start-1);
//	      } catch (BadLocationException e) {
//	         //SysUtils.debug(e);
//	      }
//	      return "";
//	   }

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
