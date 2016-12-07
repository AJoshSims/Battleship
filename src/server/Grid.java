package server;

import utilities.Constants;
import utilities.MutableInteger;

// TODO make boardSize static

class Grid
{
	private final int boardSize;
	
	private Tile[][] board;
	
	private final String username;
	
	private int remainingShipSegments;
	
	Grid(String username, int boardSize)
	{
		this.boardSize = boardSize;
		board = new Tile[boardSize][boardSize];
		this.username = username;
		createBoard();
		remainingShipSegments = placeShips();
	}
	
	Tile[][] getBoard()
	{
		return board;
	}
	
	int getRemainingShipSegments()
	{
		return remainingShipSegments;
	}
	
	private void decrementRemainingShipSegments()
	{
		--remainingShipSegments;
	}
	
	private void createBoard()
	{
		for (int row = 0; row < boardSize; ++row)
		{
			for (int column = 0; column < boardSize; ++column)
			{
				board[row][column] = new Tile(row, column, ShipSegment.NONE);
			}
		}
	}
	
	private int placeShips()
	{	
		int shipSegmentsPlaced = 0;
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
				shipSegmentTiles = buildShip(ship, shipLength);
			}
			while (shipSegmentTiles == null);
			
			for (Tile shipSegmentTile : shipSegmentTiles)
			{
				board[shipSegmentTile.row][shipSegmentTile.column] =
					shipSegmentTile;
				++shipSegmentsPlaced;
			}
		}
		
		return shipSegmentsPlaced;
	}
	
	private Tile[] buildShip(
		ShipSegment shipSegment, int shipLength)
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
				row.value, column.value, shipSegment);
			
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
	
	String getBoardString(String username)
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
					"| " + board[row][column].getTileText(username) + " ");
			}
			boardStringBuilder.append("|\n" + rowDelimiter);
		}
		
		String boardString = boardStringBuilder.toString();
		return boardString;
	}
	
	final class Tile
	{		
		private final int row;
		
		private final int column;
				
		private final ShipSegment shipSegment;
		
		private boolean hit;
		
		private TileText tileText;
		
		private Tile(int row, int column, ShipSegment shipSegment)
		{
			this.row = row;
			this.column = column;
			
			this.shipSegment = shipSegment;
			hit = false;
			
			tileText = shipSegment.getTileText();
		}
		
		private char getTileText(String username)
		{
			if (
				(!username.equals(Grid.this.username))
				&& ((tileText == TileText.CARRIER)
				|| (tileText == TileText.BATTLESHIP)
				|| (tileText == TileText.CRUISER)
				|| (tileText == TileText.SUBMARINE)
				|| (tileText == TileText.DESTROYER)))
			{
				return TileText.NONE_OR_UNKNOWN.getTileText();
			}
			
			return tileText.getTileText();
		}
		
		boolean shoot()
		{
			if (shipSegment != ShipSegment.NONE)
			{
				hit = true;
				tileText = TileText.HIT;
				decrementRemainingShipSegments();
			}
			else
			{
				hit = false;
				tileText = TileText.MISS;
			}
			
			return hit;
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
