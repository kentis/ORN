package org.k1s.owleditors.fowl;


import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Display;


public class FunctionalScanner extends RuleBasedScanner {
    
	public static final String[] keywords = {
		    "SubClassOf",
			"Ontology",
			"Import",
		    "EquivalentClasses",
		    "DisjointClasses",
		    "DisjointUnion",
		    "Annotation",
		    "AnnotationProperty",
		    "AnnotationAssertion",
		    "SubAnnotationPropertyOf",
		    "AnnotationPropertyDomain",
		    "AnnotationPropertyRange",
		    "HasKey",
		    "Declaration",
		    "Documentation",
		    "Class",
		    "ObjectProperty",
		    "DataProperty",
		    "NamedIndividual",
		    "Datatype",
		    "DataOneOf",
		    "DataUnionOf",
		    "DataIntersectionOf",
		    "ObjectOneOf",
		    "ObjectUnionOf",
		    "ObjectHasValue",
		    "ObjectInverseOf",
		    "InverseObjectProperties",
		    "DataComplementOf",
		    "DatatypeRestriction",
		    "DatatypeDefinition",
		    "ObjectIntersectionOf",
		    "ObjectComplementOf",
		    "ObjectAllValuesFrom",
		    "ObjectSomeValuesFrom",
		    "ObjectHasSelf",
		    "ObjectMinCardinality",
		    "ObjectMaxCardinality",
		    "ObjectExactCardinality",
		    "DataAllValuesFrom",
		    "DataSomeValuesFrom",
		    "DataHasValue",
		    "DataMinCardinality",
		    "DataMaxCardinality",
		    "DataExactCardinality",
		    "ObjectPropertyChain",
		    "SubObjectPropertyOf",
		    "EquivalentObjectProperties",
		    "DisjointObjectProperties",
		    "ObjectPropertyDomain",
		    "ObjectPropertyRange",
		    "FunctionalObjectProperty",
		    "InverseFunctionalObjectProperty",
		    "ReflexiveObjectProperty",
		    "IrreflexiveObjectProperty",
		    "SymmetricObjectProperty",
		    "AsymmetricObjectProperty",
		    "TransitiveObjectProperty",
		    "SubDataPropertyOf",
		    "EquivalentDataProperties",
		    "DisjointDataProperties",
		    "DataPropertyDomain",
		    "DataPropertyRange",
		    "FunctionalDataProperty",
		    "SameIndividual",
		    "DifferentIndividuals",
		    "ClassAssertion",
		    "ObjectPropertyAssertion",
		    "NegativeObjectPropertyAssertion",
		    "DataPropertyAssertion",
		    "NegativeDataPropertyAssertion",
		    "Prefix",
		    "length",
		    "minLength",
		    "maxLength",
		    "pattern",
		    "minInclusive",
		    "maxInclusive",
		    "minExclusive",
		    "maxExclusive",
		    "totalDigits",
		    "fractionDigits",
		    "DLSafeRule",
		    "ClassAtom",
		    "DataRangeAtom",
		    "ObjectPropertyAtom",
		    "DataPropertyAtom",
		    "BuiltInAtom",
		    "SameIndividualAtom",
		    "DifferentIndividualsAtom",
		    "Variable",
		    "DescriptionGraphRule",
		    "DescriptionGraph",
		    "Nodes",
		    "NodeAssertion",
		    "Edges",
		    "EdgeAssertion",
		    "MainClasses",
	};
	
	public FunctionalScanner() {
    	
        WordRule rule = new WordRule(new IWordDetector() {
           public boolean isWordStart(char c) { 
        	return Character.isJavaIdentifierStart(c); 
           }
           public boolean isWordPart(char c) {   
           	return Character.isJavaIdentifierPart(c); 
           }
        });
        
        Token keyword = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, SWT.BOLD));
//        Token comment = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY)));
//        Token string = new Token(new TextAttribute(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN)));
        //add tokens for each reserved word
       
        for (int n = 0; n < keywords.length; n++) {
           rule.addWord(keywords[n].toString(), keyword);
        }
        setRules(new IRule[] {
           rule
//           new SingleLineRule("#", null, comment),
//           new SingleLineRule("", "", string, '\\'),
//           new SingleLineRule("'", "'", string, '\\'),
//           new WhitespaceRule(new IWhitespaceDetector() {
//              public boolean isWhitespace(char c) {
//                 return Character.isWhitespace(c);
//              }
//           }),
        });
     }
}
