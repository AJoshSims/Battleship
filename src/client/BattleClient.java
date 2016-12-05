package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import common.ConnectionInterface;
import common.MessageListener;
import common.MessageSource;

// TODO use string builder

public class BattleClient extends MessageSource implements MessageListener
{	
	private ConnectionInterface connectionInterface;
			
	// TODO error handling
	BattleClient(InetAddress host, int port, String username)
		throws IOException
	{				
		// TODO close socket?
		Socket clientSpecificSocket = new Socket(host, port);
		PrintWriter socketOutput = 
			new PrintWriter(clientSpecificSocket.getOutputStream());
		BufferedReader socketInput = 
			new BufferedReader(
			new InputStreamReader(clientSpecificSocket.getInputStream()));
		
		// TODO run connectionInterface as thread for client?
		connectionInterface = 
			new ConnectionInterface(socketOutput, socketInput);
		connectionInterface.addMessageListener(this);
		Thread thread = new Thread(connectionInterface);
		thread.start();
		
		// TODO error handling for already taken username
		// TODO change
		connectionInterface.sendMessage("");
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		System.out.print(message);
		System.out.flush();
	}

	@Override
	public void sourceClosed(MessageSource source)
	{
		
	}
	
	void sendMessage(String message)
	{
		connectionInterface.sendMessage(message);
	}
}
