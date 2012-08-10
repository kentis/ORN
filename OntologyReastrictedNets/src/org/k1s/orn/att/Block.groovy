package org.k1s.orn.att

import orn.Pragmatics;

interface Block {
	
	//TODO: use start/end
	public def correspondingNetElement
	
	public  Pragmatics pargmatics
	public Block parent
	public def templateBinding
	
	public def start
	public def end
	
	public static rnd = new Random()
	
	public abstract String toGraphString(i);
}
