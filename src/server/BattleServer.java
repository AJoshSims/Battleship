package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.ConnectionInterface;

//TODO no globl variables?

import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener
{
	ServerSocket welcomeSocket;
		
	// TODO error handling
	BattleServer(int portNum) throws IOException
	{
		welcomeSocket = new ServerSocket(portNum);
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
	void listen(int portNum) throws IOException
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
