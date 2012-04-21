package org.k1s.nppn.generator


	
	import de.susebox.jtopas.Flags;
	import de.susebox.jtopas.StringSource;
	import de.susebox.jtopas.Token;
	import de.susebox.jtopas.Tokenizer;
	import de.susebox.jtopas.StandardTokenizer;
	import de.susebox.jtopas.StandardTokenizerProperties;
	import de.susebox.jtopas.ReaderSource;
	import de.susebox.jtopas.TokenizerProperties;
	import de.susebox.jtopas.TokenizerSource;


class InscriptionParser {

	
	static def parse(String var){
		println "parsing $var"
		def topLevel = []
		try {
			// setup the tokenizer properties
			TokenizerProperties props     = new StandardTokenizerProperties();
			
			//props.setParseFlags();
			props.setSeparators(",");
			props.addSpecialSequence("(")
			props.addSpecialSequence(")")
			props.addSpecialSequence("<<")
			props.addSpecialSequence(">>")
			props.addSpecialSequence("(*<<")
			props.addSpecialSequence(">>*)")
			// create the tokenizer
			Tokenizer           tokenizer = new StandardTokenizer(props);
			//println var
			TokenizerSource     source    = new StringSource(var);
			Token               token;
			int                 len;
			
			try {
				tokenizer.setSource(source);
				
				// tokenize the file and print basically
				// formatted context to stdout
				len = 0;
				
				VariableGroup currentGroup
				def groupName
				def currentVar
				def prevElement
				while (tokenizer.hasMoreToken()) {
					token = tokenizer.nextToken();
					switch(token.getType()){
						case Token.NORMAL:
							println "normal ${tokenizer.currentImage()}"
							println var.charAt(token.getStartColumn()+token.getLength())
							println token.getImage()
							println token.getStartPosition()
							if( (var.length() > token.getStartPosition()+token.getImage().length() ) && 
								var.charAt(token.getStartPosition()+token.getImage().length()) == "(" &&
								!(var.charAt(token.getStartPosition()+token.getImage().length()+1) == "*")){
								//this is the start of a group
								//println "normal  ${tokenizer.currentImage()}  is start of group"
								groupName = token.getImage()
							}else{
								
								currentVar = new Variable(name: tokenizer.currentImage())
								//println currentVar?.name
								if( currentGroup ){
									currentGroup.vars << currentVar
								} else {
									topLevel << currentVar
								}
								prevElement = currentVar
							}
							break
						case Token.SPECIAL_SEQUENCE:
							switch(tokenizer.currentImage()){
								case "(": //start group
									//println "start group $groupName"
									VariableGroup newGroup = new VariableGroup(name: groupName)
									groupName = null
									newGroup.metaClass.parent = currentGroup
									currentGroup = newGroup
									break
								case ")":
									prevElement = currentGroup
									currentGroup = currentGroup.parent
									
									if(currentGroup == null) topLevel << prevElement
									else currentGroup.groups << prevElement
									break
								case "<<":
								case "(*<<":
									def pragDef = var.substring(token.getStartPosition(), var.indexOf( ">>", token.getStartPosition())+2)
									println new Pragmatic().prag(pragDef)
									prevElement.prags << Pragmatic.prag(pragDef)
									//skip to end of pragmatics defenition
									while(! (tokenizer.currentImage() == ">>" || tokenizer.currentImage() == ">>*)" ) ) tokenizer.nextToken() 
									break
							}
						break
					}
					
					//println token
				}
				//println currentGroup
				//println topLevel
			} finally {
				// never forget to release resources and references
				tokenizer.close();
				//source.close();
			}
		} catch (Throwable throwable) {
			// catch and print all exceptions and errors
			throwable.printStackTrace();
		}
		
		return topLevel
	}

	
	
	static class Pragmatic {
		String name
		Map arguments =[:]
	
		Pragmatic prag(def elem){
			def retval = new Pragmatic()
			if(elem == null ) return retval
			String elemName = elem instanceof String ? elem : elem.name
			
			if(elemName.indexOf("<<") == -1 ) return null
			
			def pragStart = elemName.indexOf( "<<") + 2
			def pragEnd = elemName.indexOf(">>")
			//println elemName
			def pragDef = elemName.substring(pragStart, pragEnd)
			//println "pragdef: $pragDef , $pragStart, $pragEnd"
			retval.name = pragDef.subSequence(0, pragDef.indexOf("("))
			def d = pragDef.substring(pragDef.indexOf('(')+1, pragDef.indexOf(')'))
			//println "d: ${d.bytes}"
			if(d.size() == 0) retval.arguments = [:]
			else retval.arguments = new GroovyShell().evaluate("[$d]")
			
			return retval
		}
	
	}
		
}
