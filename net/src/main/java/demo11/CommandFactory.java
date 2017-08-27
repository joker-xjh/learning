package demo11;

import java.io.ObjectOutputStream;

public class CommandFactory {
	
	public static Icommands getCommand(String input, ObjectOutputStream out) {
		Icommands command = null;
		
		switch (input) {
		case "/flip":
			command = new CoinFlip();
			break;
		case "/date":
			command = new DateC(out);
			break;
		case "/roll":
			command = new Roll();
			break;
		default :
			command =new  NullCommand(input, out);
			break;

		}
		return command;
	}
	
	

}
