package org.k1s.nppn

class NPPN {

	public static void main(String[] args){
		def cli = new CliBuilder(usage: 'NPPN')
		cli.t('Specify template bindings')
		cli.o('Specify ontology')
		cli.o('Output directory (default: .)')
		cli.n('Specify CPN model')
		cli.u('Print usage statement')
		
		def options = cli.parse(args)
		
		if(options.u){
			println cli.usage()
			return
		}
		
		
		
	}
}
