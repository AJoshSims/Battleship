package server;

import java.util.Random;

class Grid
{
	private ShipSegment[][] board;
	
	private int boardSize;
	
	private static final int FIRST_ROW = 0;
	
	private final int finalRow;
	
	private static final int FIRST_COLUMN = 0;
	
	private final int finalColumn;
	
	private static final Random RANDOM_VAL_GENERATOR = new Random();
	
	private static final int NONE = 0;
	
	private static final int OFFSET = 1;
	
	Grid(int boardSize)
	{
		this.boardSize = boardSize;
		finalRow = boardSize - OFFSET;
		finalColumn = boardSize - OFFSET;
		board = new ShipSegment[boardSize][boardSize];
		createBoard();
	}
	
	void placeShips()
	{	

		int shipLength = -1;
		ShipSegment[] shipSegments = null;
		int lastSegment = -1;
		for (ShipSegment ship : ShipSegment.values())
		{
			if (ship == ShipSegment.NONE)
			{
				break;
			}
			
			shipLength = ship.getLength();
			lastSegment = shipLength - 1;
			
			do
			{
				shipSegments = buildShip(ship, shipLength);
			}
			while (shipSegments[lastSegment] == null);
		}
	}
	
	ShipSegment[] buildShip(ShipSegment ship, int shipLength)
	{
		ShipSegment[] shipSegments = new ShipSegment[shipLength];
		
		for ()
		{
			
		}
		
//		int rowToPopulate = -1;
//		int columnToPopulate = -1;
//		
//		rowToPopulate = RANDOM_VAL_GENERATOR.nextInt(boardSize);
//		columnToPopulate = RANDOM_VAL_GENERATOR.nextInt(boardSize);
//		
//		boolean populateRow = RANDOM_VAL_GENERATOR.nextBoolean();
//		
//		if (populateRow)
//		{	
//			if (columnToPopulate <= boardSize - shipLength)
//			{
//				// Populate forward
//				for (
//					int segmentsToBuild = shipLength; 
//					segmentsToBuild > NONE;
//					--segmentsToBuild)
//				{
//					board[rowToPopulate][columnToPopulate] = ship;
//					++columnToPopulate;
//				}
//			}
//			
//			else if (columnToPopulate >= shipLength - OFFSET)
//			{
//				// Populate backward
//				for (
//					int segmentsToBuild = shipLength; 
//					segmentsToBuild > NONE;
//					--segmentsToBuild)
//				{
//					board[rowToPopulate][columnToPopulate] = ship;
//					--columnToPopulate;
//				}
//			}
//		}
//		
//		else
//		{
//			if (rowToPopulate <= boardSize - shipLength)
//			{
//				// Populate forward
//				for (
//					int segmentsToBuild = shipLength; 
//					segmentsToBuild > NONE;
//					--segmentsToBuild)
//				{
//					board[rowToPopulate][columnToPopulate] = ship;
//					++rowToPopulate;
//				}
//			}
//			
//			else if (rowToPopulate >= shipLength - OFFSET)
//			{
//				// Populate backward
//				for (
//					int segmentsToBuild = shipLength; 
//					segmentsToBuild > NONE;
//					--segmentsToBuild)
//				{
//					board[rowToPopulate][columnToPopulate] = ship;
//					--rowToPopulate;
//				}
//			}
//		}
	}
	
	void createBoard()
	{
		for (int row = 0; row < boardSize; ++row)
		{
			for (int column = 0; column < boardSize; ++column)
			{
				board[row][column] = ShipSegment.NONE;
			}
		}
	}
	
	String getBoardString()
	{
		StringBuilder boardStringBuilder = new StringBuilder();
		
		boardStringBuilder.append("   ");
		for (int columnNumber = 0; columnNumber < boardSize; ++columnNumber)
		{
			boardStringBuilder.append(" " + columnNumber + "  ");
		}
		boardStringBuilder.append("\n");
		
		StringBuilder rowDelimiter = new StringBuilder();
		rowDelimiter.append("  +");
		for (
			int rowDelimiterUnit = 0;
			rowDelimiterUnit < boardSize; 
			++rowDelimiterUnit)
		{
			rowDelimiter.append("---+");
		}
		rowDelimiter.append('\n');
			
		boardStringBuilder.append(rowDelimiter);
		for (int row = 0; row < boardSize; ++row)
		{
			boardStringBuilder.append(row + " ");
			for (int column = 0; column < boardSize; ++column)
			{
				boardStringBuilder.append(
					"| " + board[row][column].getAbbreviation() + " ");
			}
			boardStringBuilder.append("|\n" + rowDelimiter);
		}
		
		String boardString = boardStringBuilder.toString();
		return boardString;
	}
}
