package server;

import java.io.IOException;

public class BattleShipDriver
{	
	public static void main(String[] args)
	{		
		// TODO error handling
		int port = Integer.parseInt(args[0]);
		int boardSize = Integer.parseInt(args[1]);
		
		BattleServer battleServer;
		try
		{
			battleServer = new BattleServer(port, boardSize);
			battleServer.listen();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
