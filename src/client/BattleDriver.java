package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BattleDriver
{
	public static void main(String[] args)
	{
		InetAddress host = null;
		int port = -1;
		String username = null;
		// TODO error handling
		try
		{
			host = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
			username = args[2];
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		
		BattleClient battleClient = new BattleClient(host, port, username);
	}
}
