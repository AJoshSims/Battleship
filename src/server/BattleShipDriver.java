package server;

public class BattleShipDriver
{
	public static void main(String[] args)
	{
		Grid grid = new Grid(10);
		
		grid.populateBoardWithEmptySpaces();
		
		System.out.print(grid.getBoardString());
	}
}
