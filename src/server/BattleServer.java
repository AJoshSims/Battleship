package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import common.ConnectionInterface;

//TODO no globl variables?

import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener
{
	ServerSocket welcomeSocket;
	
	// TODO error handling
	BattleServer(int port) throws IOException
	{
		welcomeSocket = new ServerSocket(port);
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		
	}

	@Override
	public void sourceClosed(MessageSource source)
	{
		
	}
	
	// TODO error handling
	void listen() throws IOException
	{
		Socket clientSpecificSocket = null;
		// TODO no forever loop
		while (true)
		{
			clientSpecificSocket = welcomeSocket.accept();
			
			ConnectionInterface client = 
				new ConnectionInterface(clientSpecificSocket);
			client.addMessageListener(this);
			Thread clientThread = new Thread(client);
			clientThread.start();
		}
	}
}
