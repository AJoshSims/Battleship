package server;

import java.io.IOException;

public class BattleShipDriver
{	
	public static void main(String[] args)
	{		
		// TODO error handling
		// -board size no less than 5
		// -valid port num
		int portNum = Integer.parseInt(args[0]);
		int boardSize = Integer.parseInt(args[1]);
		
		// TODO error handling
		BattleServer battleServer = null;
		try
		{
			battleServer = new BattleServer(portNum);
			battleServer.listen(portNum);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
}
