package server;

// TODO error handlng - board size can be no less than 5.
// TODO default board size of 10

public class BattleShipDriver
{
	public static void main(String[] args)
	{
		Grid grid = new Grid(10);
		
		grid.populateBoard();
		
		System.out.print(grid.getBoardString());
	}
}
