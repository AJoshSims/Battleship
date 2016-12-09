package server;

import java.io.IOException;

public class BattleShipDriver
{	
	public static void main(String[] args)
	{		
		// TODO error handling
		int port = Integer.parseInt(args[0]);
		int boardSize = Integer.parseInt(args[1]);
		
		BattleServer battleServer = null;
		try
		{
			battleServer = new BattleServer(port, boardSize);
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		battleServer.listen();
	}
}
