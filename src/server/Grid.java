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
		createBoard();
	}
	
	void placeShips()
	{	
		int row = -1;
		int column = -1;
		boolean populateRow = true;
		
		int shipLength = -1;
		int[] shipSegments = null;
		for (Ship ship : Ship.values())
		{
			if (ship == Ship.NONE)
			{
				break;
			}
			
			
			shipLength = ship.getLength();
						
			do
			{
				row = RANDOM_VAL_GENERATOR.nextInt(boardSize);
				column = RANDOM_VAL_GENERATOR.nextInt(boardSize);
				
				populateRow = RANDOM_VAL_GENERATOR.nextBoolean();
				
				shipSegments = buildShip(
					shipLength, row, column, populateRow);
				
			}
			// Ship was not built so try again.
			while (shipSegments[] != shipLength);
			
			for (int segment : shipSegments)
			{
				if (populateRow)
				{
					board[row][segment] = ship;
				}
				else
				{
					board[segment][column] = ship;
				}
			}
		}
	}
	
	private int[] buildShip(
		int shipLength, int rowInitial, int columnInitial, boolean populateRow)
	{
		int[] shipSegments = new int[shipLength];
		
		int row = rowInitial;
		int column = columnInitial;
		
		for (
			int shipSegment = 0; 
			shipSegment < shipLength
			&& board[row][column] == Ship.NONE;
			++shipSegment)
		{	
			if (populateRow)
			{
				shipSegments[shipSegment] = column;
				
				if (columnInitial < boardSize - shipLength)
				{
					// Will populate forward on row.
					++column;
				}
				
				else if (columnInitial >= shipLength - OFFSET)
				{
					// Will populate backward on row.
					--column;
				}
				
				else
				{
					break;
				}
			}
			else
			{
				shipSegments[shipSegment] = row;
				
				if (rowInitial < boardSize - shipLength)
				{
					// Will populate forward on column.
					++row;
				}
				
				else if (rowInitial >= shipLength - OFFSET)
				{
					// Will populate backward on column.
					--row;
				}
				
				else
				{
					break;
				}
			}
		}
		
		return shipSegments;
	}
	
	void createBoard()
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
