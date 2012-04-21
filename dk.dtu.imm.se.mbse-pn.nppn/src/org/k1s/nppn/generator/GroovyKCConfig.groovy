package org.k1s.nppn.generator

import static org.k1s.nppn.generator.ParserUtil.*;

class GroovyKCConfig {
	
	def platform = new GroovyPlattform()
	
	
	def CONSTRUCTOR = { name ->
		def retval 	
		if(name == "A") retval = getCode('CONSTRUCTOR_CLIENT',[name: name])
		else retval = getCode('CONSTRUCTOR_SERVER_CLIENT',[name: name])
		
		return AUTH_SERVER + retval
	}
	
	def AUTH_SERVER = '''
		def AUTH_SERVER = [host: 'localhost', port: 31337]
	'''
	
	def GENERATENONCE = '''
		def random = new java.security.SecureRandom()
		def nonceA = random.nextInt()
		def nonceA_created = nonceA
	''' 
	
	def GENERATENONCE_VARS = ['random', 'nonceA']
	
	def ENCRYPTNONCE = '''
		<%out << org.k1s.nppn.ParserUtil.initializeVar(out_vars, VARS)%>
		
		ENC: {


			def desKey = org.k1s.kc.authServer.KeyManager.getKey('key')
            def desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.ENCRYPT_MODE, desKey);
			def ciphertext = []
			/*${in_vars}*/
	<%if(in_vars[1] instanceof org.k1s.nppn.Variable){ %>
		${in_vars[1].name} = ${in_vars[1].name}.trim()
		ciphertext = ciphertext + desCipher.update(${in_vars[1].name}.bytes).toList()
	<%} else {
		def first = true
		in_var.vars.each{ var ->
		if(!first) {%>ciphertext = ciphertext + desCipher.update('|'.bytes).toList()<%}
		first = false
	%>
		
		ciphertext = ciphertext + desCipher.update(${var.name}.bytes).toList()
	<% } } %>

        ciphertext = ciphertext + desCipher.doFinal().toList()
            
		${out_var.name} = ciphertext as byte[]
		${out_var.name} = Base64.encode(${out_var.name})
		<%if(out_var instanceof org.k1s.nppn.VariableGroup){%>
			${out_var.vars[0].name} = ${out_var.name} 
		<%}%>
	}
	'''
	//def DECRYPTKEY = platform.DEC
	
	def DECRYPTKEY = '''
		<%out << org.k1s.nppn.ParserUtil.initializeVar(out_vars, VARS)%>
		
		DEC: {
			
			def decrypt = { var, decKey ->
				def desKey = org.k1s.kc.authServer.KeyManager.getKey(decKey)
				def desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				desCipher.init(Cipher.DECRYPT_MODE, desKey);
				def b64 = Base64.decode(var) as byte[]
				byte[] cleartext = desCipher.doFinal(b64,0,b64.length);
				return cleartext
			}
			/*${out_vars}
			${enc_vars}
			${in_vars}*/
			<%enc_vars.each{ enc_var -> %>
				/*${enc_var.class}*/
				def cleartext_${enc_var.name} = new String( decrypt(${enc_var.name}, '${enc_var.prags[0].arguments['key']}') )
				<%if(enc_var instanceof org.k1s.nppn.Variable || (enc_var instanceof org.k1s.nppn.VariableGroup && enc_var.vars.size() == 1)){ %>
					${enc_var.name} = new String(cleartext_${enc_var.name})
					
				<%} else if(enc_var instanceof org.k1s.nppn.VariableGroup) {
					
					
					out.print org.k1s.nppn.ParserUtil.initializeVar(enc_var.vars, VARS) %>
					(<%
						def sb = new StringBuilder()
						enc_var.vars.each { var ->
							println "VAR: ${var.name}"
							sb.append(var.name).append(", ")
						}
						out.print sb.toString().substring(0, sb.toString().length() - 2)
					%>  ) = decodeData(cleartext_${enc_var.name})
					
				<%	}
				
				
				  %>
			<%}%>
				
		}
'''
	
def a = '''

<%if(out_var instanceof org.k1s.nppn.Variable){ %>
					${out_var.name} = new String(cleartext)
				<%} else {%>
					${out_var.name} = new String(cleartext).split('\\\\\\\\|')
				<% } %>
'''

	def AUTHENTICATE = '''
		if(nonceA.toInteger() == nonceA_created) println "Server (B) authenticated"
		else throw new RuntimeException("nonceA ("+nonceA+") != nonceA_created ("+nonceA_created+")")
	'''
	
	def ENCODEDATA = platform.ENCODEDATA_PSL
	def DECODEDATA = platform.DECODEDATA_PSL
	
	
	
}
