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
	
	private static final Random RANDOM_NUM_GENERATOR = new Random();
	
	Grid(int boardSize)
	{
		this.boardSize = boardSize;
		finalRow = boardSize - 1;
		finalColumn = boardSize - 1;
		board = new Ship[boardSize][boardSize];
	}
	
	void populateBoard()
	{	
		int rowToPopulate = -1;
		int columnToPopulate = -1;
		for (Ship ship : Ship.values())
		{
			if (ship == Ship.NONE)
			{
				break;
			}
			
			rowToPopulate = RANDOM_NUM_GENERATOR.nextInt(boardSize);
			columnToPopulate = RANDOM_NUM_GENERATOR.nextInt(boardSize);
			
			for (int row = 0; row < boardSize; ++row)
			{
				for (int column = 0; column < boardSize; ++column)
				{
					
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
				boardStringBuilder.append("| " + board[row][column].getAbbreviation() + " ");
			}
			boardStringBuilder.append("|\n" + rowDelimiter);
		}
		
		String boardString = boardStringBuilder.toString();
		return boardString;
	}
}
