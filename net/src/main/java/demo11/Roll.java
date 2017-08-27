package demo11;

public class Roll implements Icommands{
	
	private static final String NAME = "Roll";
	private static final String HELP = "/Roll - Returns a number between 0 and 99\"";

	@Override
	public String perform() {
		int num = (int) (Math.random()*100);
		return "Rolling (0-99): " + num + ".";
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
