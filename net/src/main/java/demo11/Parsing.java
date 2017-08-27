package demo11;

import java.io.ObjectOutputStream;

public class Parsing {
	
	public static boolean shouldParse(String command) {
		if(command != null && command.length()>0 && command.substring(0, 1).endsWith("/"))
			return true;
		
		return false;
	}
	
	public static String parse(String command, ObjectOutputStream outputStream) {
		command = command.toLowerCase();
		Icommands icommands = CommandFactory.getCommand(command, outputStream);
		return icommands.perform();
	}
	

}
