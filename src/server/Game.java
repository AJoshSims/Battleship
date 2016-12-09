package server;

import java.util.HashMap;

class Game 
{
	private static HashMap<String, Grid> grids;
	
	private final int boardSize;
	
	Game(int boardSize)
	{
		// TODO error handling here
		this.boardSize = boardSize;
		grids = new HashMap<String, Grid>();
	}
	
	Grid getGrid(String username)
	{
		return grids.get(username);
	}
	
	void addGrid(String username)
	{
		grids.put(username, new Grid(username, boardSize));
	}
	
	boolean attack(String username, int row, int column)
	{
		Grid.Tile[][] board = grids.get(username).getBoard();
		
		boolean hit = board[row][column].shoot();
		
		return hit;
	}
}