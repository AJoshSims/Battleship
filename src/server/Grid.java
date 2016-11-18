package server;

import java.util.Random;

import server.Tile.ShipSegment;

class Grid
{
	private Tile[][] board;
	
	private final int boardSize;
	
	private static final int FIRST_ROW = 0;
	
	private final int finalRow;
	
	private static final int FIRST_COLUMN = 0;
	
	private final int finalColumn;
	
	private static final Random RANDOM_VAL_GENERATOR = new Random();
	
	private static final int NONE = 0;
	
	private static final int OFFSET = 1;
	
	Grid(int boardSize, boolean ownership)
	{
		this.boardSize = boardSize;
		finalRow = boardSize - OFFSET;
		finalColumn = boardSize - OFFSET;
		board = new Tile[boardSize][boardSize];
		createBoard(ownership);
	}
	
	void createBoard(boolean ownership)
	{
		Tile vacantTile = null;
		for (int row = 0; row < boardSize; ++row)
		{
			for (int column = 0; column < boardSize; ++column)
			{
				board[row][column] = new Tile(row, column, ownership);
			}
		}
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
			
			shipLength = ship.getLengthOfWhole();
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
			int rowDelimiterUnitIndex = 0;
			rowDelimiterUnitIndex < boardSize; 
			++rowDelimiterUnitIndex)
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
					"| " + board[row][column].getTileText() + " ");
			}
			boardStringBuilder.append("|\n" + rowDelimiter);
		}
		
		String boardString = boardStringBuilder.toString();
		return boardString;
	}
	
	private final class Tile
	{		
		private final int row;
		
		private final int column;
		
		private boolean ownership;
		
		private final ShipSegment shipSegment;
		
		private boolean hit;
		
		private TileText tileText;
		
		private Tile(int row, int column, boolean ownership)
		{
			this.row = row;
			this.column = column;
			this.ownership = ownership;
			
			shipSegment = ShipSegment.NONE;
			hit = false;
			tileText = shipSegment.getTileText();
		}
		
		private int getRow()
		{
			return row;
		}
		
		private int getColumn()
		{
			return column;
		}
		
		private char getTileText()
		{
			return tileText.getTileText();
		}
		
		private ShipSegment getShipSegment()
		{
			return shipSegment;
		}
		
		private void setShipSegment(ShipSegment shipSegment)
		{
			this.shipSegment = shipSegment;
			tileText = shipSegment.getTileText();
		}
		
		private boolean isHit()
		{
			return hit;
		}
		
		private boolean hit()
		{
			if (shipSegment != ShipSegment.NONE)
			{
				hit = true;
				tileText = TileText.HIT;
			}
			else
			{
				hit = false;
				tileText = TileText.MISS;
			}
		}
	}
}

enum ShipSegment
{
	CARRIER (TileText.SUBMARINE, 5),
	BATTLESHIP (TileText.BATTLESHIP, 4),
	CRUISER (TileText.CRUISER, 3),
	SUBMARINE (TileText.SUBMARINE, 3),
	DESTROYER (TileText.DESTROYER, 2),
	NONE (TileText.NONE_OR_UNKNOWN, 0);
	
	private final TileText tileText;
	
	private final int lengthOfWhole;
	
	ShipSegment(TileText tileText, int lengthOfWhole)
	{
		this.tileText = tileText;
		this.lengthOfWhole = lengthOfWhole;
	}
	
	TileText getTileText()
	{
		return tileText;
	}
	
	int getLengthOfWhole()
	{
		return lengthOfWhole;
	}
}

enum TileText 
{
	NONE_OR_UNKNOWN(' '), 
	CARRIER('C'), BATTLESHIP('B'), CRUISER('R'), SUBMARINE('S'), DESTROYER('D'), 
	HIT('X'), MISS('@'); 
	
	private final char tileText;
	
	TileText(char tileText)
	{
		this.tileText = tileText;
	}
	
	char getTileText()
	{
		return tileText;
	}
}
