package server;

import java.util.HashMap;
import java.util.Scanner;

// TODO error handlng - board size can be no less than 5.

public class BattleShipDriver
{	
	public static void main(String[] args)
	{		
		Grid.boardSize = 5;
		
		Game game = new Game();
		
		String Joshua = "Joshua";
		
		String Evan = "Evan";
		
		game.addGrid("Joshua");
		
		game.addGrid("Evan");
		
		HashMap<String, Grid> grids = game.getGrids();
		
		grids.get(Joshua).placeShips();
		
		grids.get(Evan).placeShips();
		
		System.out.print(
			"Joshua's grid viewed by Joshua\n" + 
			grids.get(Joshua).getBoardString(Joshua) + "\n");
		
		System.out.print(
			"Evan's grid viewed by Evan\n" + 
			grids.get(Evan).getBoardString(Evan) + "\n");
		
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Evan, please attack Joshua...");
		System.out.print("Specify a row to attack: ");
		int rowJoshua = userInput.nextInt();
		System.out.print("Specify a column to attack: ");
		int columnJoshua = userInput.nextInt();
		System.out.println("attacking...");
		game.attack(Joshua, rowJoshua, columnJoshua);
		System.out.print(
			"Joshua's grid viewed by Evan\n" + 
			grids.get(Joshua).getBoardString(Evan) + "\n");
		
		System.out.println("Joshua, please attack Evan...");
		System.out.print("Specify a row to attack: ");
		int rowEvan = userInput.nextInt();
		System.out.print("Specify a column to attack: ");
		int columnEvan = userInput.nextInt();
		System.out.println("attacking...");
		game.attack(Evan, rowEvan, columnEvan);
		System.out.print(
			"Evan's grid viewed by Joshua\n" + 
			grids.get(Evan).getBoardString(Joshua) + "\n");
	}
}
