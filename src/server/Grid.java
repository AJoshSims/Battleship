package server;

import java.util.Random;

class Grid
{
	private Ship[][] board;
	
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
		board = new Ship[boardSize][boardSize];
		populateBoardWithEmptySpaces();
	}
	
	void populateBoard()
	{	
		int rowToPopulate = -1;
		int columnToPopulate = -1;
		int shipLength = -1;
		for (Ship ship : Ship.values())
		{
			shipLength = ship.getLength();
			if (ship == Ship.NONE)
			{
				break;
			}
			
			rowToPopulate = RANDOM_VAL_GENERATOR.nextInt(boardSize);
			columnToPopulate = RANDOM_VAL_GENERATOR.nextInt(boardSize);
			
			boolean populateRow = RANDOM_VAL_GENERATOR.nextBoolean();
			
			if (populateRow)
			{	
				if (columnToPopulate < finalRow - shipLength)
				{
					// Populate forward
					for (
						int segmentsToBuild = shipLength; 
						segmentsToBuild > NONE;
						--segmentsToBuild)
					{
						board[rowToPopulate][columnToPopulate] = ship;
						++columnToPopulate;
					}
				}
				
				else if (columnToPopulate >= shipLength - OFFSET)
				{
					// Populate backward
					for (
						int segmentsToBuild = shipLength; 
						segmentsToBuild > NONE;
						--segmentsToBuild)
					{
						board[rowToPopulate][columnToPopulate] = ship;
						--columnToPopulate;
					}
				}
			}
			
			else
			{
				if (rowToPopulate < finalColumn - shipLength)
				{
					// Populate forward
					for (
						int segmentsToBuild = shipLength; 
						segmentsToBuild > NONE;
						--segmentsToBuild)
					{
						board[rowToPopulate][columnToPopulate] = ship;
						++rowToPopulate;
					}
				}
				
				else if (rowToPopulate >= shipLength - OFFSET)
				{
					// Populate backward
					for (
						int segmentsToBuild = shipLength; 
						segmentsToBuild > NONE;
						--segmentsToBuild)
					{
						board[rowToPopulate][columnToPopulate] = ship;
						--rowToPopulate;
					}
				}
			}
			
		}
	}
	
	void populateBoardWithEmptySpaces()
	{
		for (int row = 0; row < boardSize; ++row)
		{
			for (int column = 0; column < boardSize; ++column)
			{
				board[row][column] = Ship.NONE;
			}
		}
	}
	
	String getBoardString()
	{
		StringBuilder boardStringBuilder = new StringBuilder();
		
		String columnNumbers = "    0   1   2   3   4   5   6   7   8   9  \n";
		boardStringBuilder.append(columnNumbers);
		String rowDelimiter = "  +---+---+---+---+---+---+---+---+---+---+\n";
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
