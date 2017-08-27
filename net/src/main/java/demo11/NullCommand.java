package demo11;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class NullCommand implements Icommands{
	
	private String text;
	private ObjectOutputStream out;
	
	public NullCommand(String text, ObjectOutputStream out) {
		this.text = text;
		this.out = out;
	}

	@Override
	public String perform() {
		try {
			out.writeObject("MESSAGE " + "Command not recognized: " + text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		
		return "NullCommand";
	}

	@Override
	public String help() {
		return "/NullCommand is used behind the scenes to handle unknown commands.";
	}

}
