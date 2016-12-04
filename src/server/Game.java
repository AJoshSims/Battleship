package server;

import java.util.HashMap;

class Game 
{
	private static HashMap<String, Grid> grids;
	
	Game()
	{
		grids = new HashMap<String, Grid>();
	}
	
	HashMap<String, Grid> getGrids()
	{
		return grids;
	}
	
	void addGrid(String username)
	{
		grids.put(username, new Grid(username));
	}
	
	void attack(String username, int row, int column)
	{
		Grid.Tile[][] board = grids.get(username).getBoard();
		
		board[row][column].shoot();
	}
}