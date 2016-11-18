package server;

import utilities.Constants;
import utilities.MutableInteger;

class Grid
{
	private Tile[][] board;
	
	private final int boardSize;
						
	Grid(int boardSize, boolean ownership)
	{
		this.boardSize = boardSize;
		board = new Tile[boardSize][boardSize];
		createBoard(ownership);
	}
	
	void createBoard(boolean ownership)
	{
		for (int row = 0; row < boardSize; ++row)
		{
			for (int column = 0; column < boardSize; ++column)
			{
				board[row][column] = new Tile(
					row, column, 
					ownership, 
					ShipSegment.NONE);
			}
		}
	}
	
	void placeShips(boolean ownership)
	{	
		int shipLength = -1;
		Tile[] shipSegmentTiles = null;
		for (ShipSegment ship : ShipSegment.values())
		{
			if (ship == ShipSegment.NONE)
			{
				break;
			}
			
			shipLength = ship.getLengthOfWhole();
			
			do
			{
				shipSegmentTiles = buildShip(ship, shipLength, ownership);
			}
			while (shipSegmentTiles == null);
			
			for (Tile shipSegmentTile : shipSegmentTiles)
			{
				board[shipSegmentTile.getRow()][shipSegmentTile.getColumn()] =
					shipSegmentTile;
			}
		}
	}
	
	private Tile[] buildShip(
		ShipSegment shipSegment, int shipLength, 
		boolean ownership)
	{
		Tile[] shipSegmentTiles = new Tile[shipLength];
		
		MutableInteger row = new MutableInteger();
		MutableInteger column = new MutableInteger();
		MutableInteger currentStepAlongPath = null;
		
		final int maxEdge = boardSize - 1;
		final int minEdge = 0;
		final int requiredDistanceFromEdge = shipLength - 1;

		row.value = Constants.RANDOM_VAL_GENERATOR.nextInt(boardSize);
		column.value = Constants.RANDOM_VAL_GENERATOR.nextInt(boardSize);
		
		boolean buildAlongRow = Constants.RANDOM_VAL_GENERATOR.nextBoolean();
		
		if (buildAlongRow == true)
		{
			currentStepAlongPath = column;
		}
		else
		{
			currentStepAlongPath = row;
		}
		
		boolean buildForward = false;
		if (currentStepAlongPath.value <= 
			(maxEdge - requiredDistanceFromEdge))
		{
			buildForward = true;
		}
		else if (currentStepAlongPath.value >= 
			(requiredDistanceFromEdge - minEdge))
		{
			buildForward = false;
		}
		else 
		{
			return null;
		}
		
		for (
			int currentShipSegmentTileIndex = 0; 
			currentShipSegmentTileIndex < shipLength; 
			++currentShipSegmentTileIndex)
		{
			if ((row.value >= boardSize) || (column.value >= boardSize)
				|| (board[row.value][column.value].shipSegment != 
				ShipSegment.NONE))
			{
				return null;
			}
			
			shipSegmentTiles[currentShipSegmentTileIndex] = new Tile(
				row.value, column.value, ownership, shipSegment);
			
			if (buildForward == true)
			{
				++currentStepAlongPath.value;
			}
			
			else
			{
				--currentStepAlongPath.value;
			}
		}
		
		return shipSegmentTiles;
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
		
		private Tile(
			int row, int column, 
			boolean ownership, 
			ShipSegment shipSegment)
		{
			this.row = row;
			this.column = column;
			this.ownership = ownership;
			
			this.shipSegment = shipSegment;
			hit = false;
			
			if (ownership == true)
			{
				tileText = shipSegment.getTileText();	
			}
			else
			{
				tileText = TileText.NONE_OR_UNKNOWN;
			}
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
		
		private boolean isHit()
		{
			return hit;
		}
		
		private void hit()
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
	CARRIER (TileText.CARRIER, 5),
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
