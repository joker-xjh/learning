package demo11;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Help implements Icommands{
	
	private ObjectOutputStream out;
	
	public Help(ObjectOutputStream out) {
		this.out = out;
	}

	@Override
	public String perform() {
		if(out == null)
			throw new IllegalArgumentException("Printwriter null on call to Help.perform()");
		try {
			out.writeObject("MESSAGE " + "*** Commands are not case-sensitive  ***");
            out.writeObject("MESSAGE " + "\t" + new CoinFlip().help());
            out.writeObject("MESSAGE " + "\t" + new DateC(null).help());
            out.writeObject("MESSAGE " + "\t" + new Help(null).help());
            out.writeObject("MESSAGE " + "\t" + new Roll().help());
            out.writeObject("MESSAGE " + "*** End Help ***");
			
		} catch (IOException e) {
		}
		
		
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

}
