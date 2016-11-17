package server;

enum ShipSegment
{
	CARRIER ('C', -1, -1, 5),
	BATTLESHIP ('B', -1, -1, 4),
	CRUISER ('R', -1, -1, 3),
	SUBMARINE ('S', -1, -1, 3),
	DESTROYER ('D', -1, -1, 2),
	NONE (' ', -1, -1, 0);
	
	private final char abbreviation;
	
	private final int row;
	
	private final int column;
	
	private final int length;
	
	ShipSegment(char abbreviation, int row, int column, int length)
	{
		this.abbreviation = abbreviation;
		this.row = row;
		this.column = column;
		this.length = length;
	}
	
	char getAbbreviation()
	{
		return abbreviation;
	}
	
	int getLength()
	{
		return length;
	}
}
