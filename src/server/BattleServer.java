package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import common.ConnectionInterface;

//TODO no globl variables?

import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener
{
	ServerSocket welcomeSocket;
		
	// TODO error handling
	BattleServer(int port, int boardSize) throws IOException
	{
		welcomeSocket = new ServerSocket(port);
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		// TODO change
		if (source instanceof ConnectionInterface)
		{
			ConnectionInterface connectionInterface = 
				(ConnectionInterface) source;
						
			connectionInterface.sendMessage("thxclient");
		}
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
			// TODO remove
			System.out.println("server: before acceptance");
			
			clientSpecificSocket = welcomeSocket.accept();

			System.out.println("server: after acceptance");
			
			PrintWriter socketOutput = 
				new PrintWriter(clientSpecificSocket.getOutputStream());
			BufferedReader socketInput = 
				new BufferedReader(
				new InputStreamReader(clientSpecificSocket.getInputStream()));
			
			System.out.println("server: after createwriter and reader");
			
			ConnectionInterface connectionInterface = 
				new ConnectionInterface(socketOutput, socketInput);
			
			System.out.println("server: after create connectionInterface");

			connectionInterface.addMessageListener(this);
			
			System.out.println("server: after addMessageListener");
									
			Thread clientThread = new Thread(connectionInterface);
			clientThread.start();
			
			System.out.println("server: after connectionInterface thread started");
		}
	}
}
