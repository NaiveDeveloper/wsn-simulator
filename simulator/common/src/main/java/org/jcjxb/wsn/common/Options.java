package org.jcjxb.wsn.common;

import java.util.HashMap;

public class Options {

	private HashMap<String, Option> knowOptions = new HashMap<String, Option>();

	private HashMap<String, String> unknowOptions = new HashMap<String, String>();

	public BoolOption newOption(String name, boolean value, String description) {
		BoolOption option = new BoolOption(name, value, description);
		knowOptions.put(name, option);
		return option;
	}

	public StringOption newOption(String name, String value, String description) {
		StringOption option = new StringOption(name, value, description);
		knowOptions.put(name, option);
		return option;
	}

	public IntOption newOption(String name, int value, String description) {
		IntOption option = new IntOption(name, value, description);
		knowOptions.put(name, option);
		return option;
	}

	public void parseCommandLine(String[] args) {
		for (String arg : args) {
			if (arg.charAt(0) != '-' || arg.length() <= 1) {
				throw new IllegalArgumentException(
						"CommandLine arguments illegal, see help information");
			}
			parseOption(arg);
		}
	}

	private void parseOption(String arg) {
		String name, value;
		int index = arg.indexOf('=');
		if (index < 0) {
			name = arg.substring(1);
			value = "";
		} else {
			name = arg.substring(1, index);
			value = arg.substring(index + 1);
			;
		}
		setOption(name, value);
	}

	private void setOption(String name, String value) {
		Option option = knowOptions.get(name);
		if (option != null) {
			option.setValue(value);
		} else {
			unknowOptions.put(name, value);
		}
	}

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

	public static class StringOption extends Option {

		private String value;

		private StringOption(String name, String value, String description) {
			this.name = name;
			this.value = value;
			this.description = description;
		}

		@Override
		public void setValue(String val) {
			this.value = val;
		}

		public String getValue() {
			return value;
		}
	}

	public static class IntOption extends Option {

		private int value;

		private IntOption(String name, int value, String description) {
			this.name = name;
			this.value = value;
			this.description = description;
		}

		@Override
		public void setValue(String val) {
			this.value = Integer.parseInt(val);
		}

		public int getValue() {
			return value;
		}
	}

	public static class BoolOption extends Option {

		private boolean value;

		private BoolOption(String name, boolean value, String description) {
			this.name = name;
			this.value = value;
			this.description = description;
		}

		@Override
		public void setValue(String val) {
			this.value = Boolean.parseBoolean(val);
		}

		public boolean getValue() {
			return value;
		}
	}
}
