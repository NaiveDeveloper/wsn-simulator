package org.jcjxb.wsn.common;

import java.util.HashMap;

public class Options {
	
	private HashMap<String, String> knowOptions;
	
	public static abstract class Option {
		
		protected String name;
		
		protected String description;
		
		public abstract void setValue(String val);

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
		
		
	}
	
}
