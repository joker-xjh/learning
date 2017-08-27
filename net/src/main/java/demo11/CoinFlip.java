package demo11;



public class CoinFlip implements Icommands{
	
	private static final String HEAD = "Flipping a coin: Heads.";
	private static final String TAIL = "Flipping a coin: Tails.";
	private static final String NAME = "CoinFlip";
	private static final String HELP = "/CoinFlip - Simulates flipping a coin, will return either heads or tails.";
	
	
	@Override
	public String perform() {
		
		int coin = (int)Math.random()*10;
		coin = coin &1;
		if(coin == 1)
			return TAIL;
		else
			return HEAD;
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
