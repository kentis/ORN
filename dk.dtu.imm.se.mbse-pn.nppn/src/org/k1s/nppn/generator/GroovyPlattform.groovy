package org.k1s.nppn.generator

class GroovyPlattform {
	
	def START_MODULE = '''
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.crypto.Cipher
class ${p.name.text} {
'''
	

def COMMENT = '''
/********************
${comment}
*********************/
'''

def CONSTRUCTOR_SERVER = '''
def port


${name}(port) {
this.port = port
}
'''

def CONSTRUCTOR_CLIENT = '''
def server_addr
def server_port

${name}(server_addr, server_port) {

this.server_addr = server_addr
this.server_port = server_port


}
'''

def CONSTRUCTOR_SERVER_CLIENT = '''
def server_addr
def server_port

def port

${name}(server_addr, server_port, port) {

this.server_addr = server_addr
this.server_port = server_port

this.port = port
}
'''


	
def END_MODULE= '''
}
'''

def START_MAIN = '''
	def start(){
'''

def END_MAIN = '''
	}
'''

def START_API = '''
	def ${prag.arguments['name']}(${prag.arguments['params']}) {
'''	

def END_API = '''
	}
'''

def ENC = '''


	def ${out_var.name}

	ENC: {


			def desKey = new File("/home/kent/projects/phd/dtu/simpleProtocols/simpler/key").newObjectInputStream().readObject()
            def desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.ENCRYPT_MODE, desKey);
			def ciphertext = []
	<%if(in_var.class.name == "org.k1s.nppn.generator.Variable"){ %>
		ciphertext = ciphertext + desCipher.update(${in_var.name}.bytes).toList()
	<%} else {
		def first = true
		in_var.vars.each{ var ->
		if(!first) {%>ciphertext = ciphertext + desCipher.update('|'.bytes).toList()<%}
		first = false
	%>
		
		ciphertext = ciphertext + desCipher.update(${var.name}.bytes).toList()
	<% } } %>

        ciphertext = ciphertext + desCipher.doFinal().toList()
            
		${out_var.name} = Base64.encode(ciphertext as byte[])
		
		 
	}
'''

def DEC = '''
	
	/*def ${out_var.name}*/
	<%if(out_var.class.name == "org.k1s.nppn.generator.VariableGroup"){%>
	<%out << parserUtil.initializeVar(out_var.vars, VARS)%>
	<%}else{%>
	<%out << parserUtil.initializeVar(out_var, VARS)%>
	<%}%>
	DEC: {
			
			
			def dec_data = Base64.decode(new String(${in_var.name}).trim())
			
			def desKey = new File("/home/kent/projects/phd/dtu/simpleProtocols/simpler/key").newObjectInputStream().readObject()
			def desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.DECRYPT_MODE, desKey);

            byte[] cleartext = desCipher.doFinal(dec_data,0,dec_data.length);
            
            <%if(out_var.class.name == "org.k1s.nppn.generator.Variable"){ %>
            	${out_var.name} = new String(cleartext)
            <%} else if(out_var.class.name == "org.k1s.nppn.generator.VariableGroup"){%>
            	/*${out_var.name} = new String(cleartext).split('\\\\\\\\|')*/
            	(<%
def sb = new StringBuilder()
out_var.vars.each { var ->
	
	sb.append(var.name).append(", ")
}
out.print sb.toString().substring(0, sb.toString().length() - 2)
%>  ) = decodeData(new String(cleartext))
            <% } %>
    }
'''

def OPER_PRINT = '''
		println  ${text}
'''

def DECODEDATA_PSL = '''
def decodeData(vars){
  return vars.split('\\\\\\\\|')
}
'''

def ENCODEDATA_PSL = '''
def encodeData(vars){
  def add = ""
  def res = ""
  vars.each{ it ->
  	if(it != null){
  	  res = res + add + it
  	  add = "|"
  	}
  }
  return res.bytes
}
'''

	def UDP_SEND = '''
SEND:{
<%if(prag?.arguments?.target){%>
def B = [host:<%=prag.arguments.target%>.host,port:<%=prag.arguments.target%>.port]
<%} else {%>
def B = [host: server_addr ? server_addr :'localhost', port: server_port ? server_port.toInteger() : 31337]
<%}%>
DatagramSocket sock = getSocket(false)
<%if(sendVars[0].class.name == "org.k1s.nppn.generator.VariableGroup") sendVars = sendVars[0].vars%>
def data = encodeData([<%sendVars.each{ v -> out << v.name << ','}%>null])

DatagramPacket pack = new DatagramPacket(data, data.length, InetAddress.getByName(B.host), B.port)
sock.send pack
sock.close()
}
	'''
	//${data}${prag == null ? '.bytes' :''}, ${data}${prag == null ? '.bytes' :''}.length
	
	def UDP_RECIVE = '''
<% out_vars.each { out_var -> 
 out << parserUtil.initializeVar(out_vars, VARS)
}%>
RECIVE:{
<%if(prag?.arguments?.target) { %>
DatagramSocket in_sock = getSocket(true, ${prag?.arguments?.target})
<% } else {%>
DatagramSocket in_sock = getSocket(true)
<%}%>
byte[] buff = new byte[2048]
DatagramPacket in_pack = new  DatagramPacket(buff, 2048)

in_sock.receive in_pack
in_sock.disconnect()
in_sock.close()


<% out_vars.each { out_var -> %>
<% if(out_var.prags.size() == 0 ){ %> 
${out_var.name} = new String(in_pack.data)
<% } else { %>
${out_var.name} = in_pack.data[0..in_pack.getLength()-1] as byte[]
<%}%>
<%}%>
}
'''

def RECIEVE_SPLIT_DATA = '''
<%out.print parserUtil.initializeVar(out_vars, VARS)%>

(<%
def sb = new StringBuilder()
out_vars.each { var ->
	
	sb.append(var.name).append(", ")
}
out.print sb.toString().substring(0, sb.toString().length() - 2)
%>  ) = decodeData(${in_var})
'''

def RECIEVE_SPLIT_DATA_RECV_LIST = '''
<%out.print parserUtil.initializeVar(recv_list, VARS)%>

(<%
def sb = new StringBuilder()
recv_list.each { String var ->
	
	sb.append(var).append(", ")
}
out.print sb.toString().substring(0, sb.toString().length() - 2)
%>  ) = decodeData(${in_var})
'''


def GET_SOCKET_UDP = '''

def getSocket(server=false, addr = null){
DatagramSocket sock 
if(server) {
	if(addr){
		def port
		switch(addr){
			case String:
			case GString:
				port = addr.substring(addr.indexOf(':')+1).toInteger() 
				break
			case Map:
				port = addr.port
				break
		}
		sock = new DatagramSocket(port)
	} else {
		sock = new DatagramSocket(port)
	}
}
else{ sock = new DatagramSocket()}
return sock
}
'''
	

}
