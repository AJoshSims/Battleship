package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

// TODO close things

public class BattleDriver
{
	public static void main(String[] args)
	{
		InetAddress host = null;
		int port = -1;
		String username = null;
		BattleClient battleClient = null;
		// TODO error handling
		try
		{
			host = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
			username = args[2];
			battleClient = 
				new BattleClient(host, port, username);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Scanner userInput = new Scanner(System.in);
		String command = "";
		// TODO forever loop
		while (true)
		{
			command = userInput.nextLine();
			battleClient.sendCommand(command);
		}
		
		// TODO insert
//		userInput.close();
	}
}
