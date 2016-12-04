package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BattleDriver
{
	public static void main(String[] args)
	{
		// TODO error handling
		InetAddress host = null;
		try
		{
			host = InetAddress.getByName(args[0]);
		}
		catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}
		
		int portNum = Integer.parseInt(args[1]);
		String username = args[2];
		
		BattleClient battleClient = new BattleClient(host, portNum, username);
	}
}
