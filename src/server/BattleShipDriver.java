package server;

// TODO error handlng - board size can be no less than 5.
// TODO default board size of 10

public class BattleShipDriver
{
	public static void main(String[] args)
	{		
		String username = "Joshua";
		
		Grid grid = new Grid(5, username);
		
		grid.placeShips();
		
		System.out.print(grid.getBoardString("Bill"));
	}
}
