package server;

enum Ship
{
	CARRIER ('C', 5),
	BATTLESHIP ('B', 4),
	CRUISER ('R', 3),
	SUBMARINE ('S', 3),
	DESTROYER ('D', 2),
	NONE (' ', 0);
	
	private final char abbreviation;
	
	private final int length;
	
	Ship(char abbreviation, int length)
	{
		this.abbreviation = abbreviation;
		this.length = length;
	}
	
	char getAbbreviation()
	{
		return abbreviation;
	}
}
