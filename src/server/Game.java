package server;

import java.util.HashMap;

class Game 
{
	private static HashMap<String, Grid> grids;
	
	Game()
	{
		grids = new HashMap<String, Grid>();
	}
	
	String getGridString(String username)
	{
		return grids.get(username).getBoardString(username);
	}
	
	void addGrid(String username, int boardSize)
	{
		grids.put(username, new Grid(username, boardSize));
	}
	
	void attack(String username, int row, int column)
	{
		Grid.Tile[][] board = grids.get(username).getBoard();
		
		board[row][column].shoot();
	}
}