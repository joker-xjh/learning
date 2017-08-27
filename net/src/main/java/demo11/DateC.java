package demo11;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class DateC implements Icommands{
	
	private ObjectOutputStream out;
	private static final String NAME = "Date";
	private static final String HELP = "/Date - Returns the current date and time of the server. (May vary from local time)";
	
	public DateC(ObjectOutputStream out) {
		this.out = out;
	}

	@Override
	public String perform() {
		if(out != null) {
			String date = new Date().toString();
			try {
				out.writeObject(date);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String help() {
		return HELP;
	}

}
