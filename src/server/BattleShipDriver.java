package server;

public class BattleShipDriver
{	
	public static void main(String[] args)
	{		
		// TODO error handling
		// -board size no less than 5
		// -valid port num
		int portNum = Integer.parseInt(args[0]);
		int boardSize = Integer.parseInt(args[1]);
		
		BattleServer battleServer = new BattleServer();
		battleServer.listen(portNum);
	}
}
