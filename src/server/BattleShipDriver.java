package server;

// TODO error handlng - board size can be no less than 5.
// TODO default board size of 10

public class BattleShipDriver
{
	public static void main(String[] args)
	{
		boolean ownership = true;
		
		Grid grid = new Grid(5, ownership);
		
		grid.placeShips(ownership);
		
		System.out.print(grid.getBoardString());
	}
}
