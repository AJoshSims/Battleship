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
	
	HashMap<String, ConnectionInterface> connectionInterfaces;
	
	// TODO error handling
	BattleServer(int port) throws IOException
	{
		welcomeSocket = new ServerSocket(port);
		connectionInterfaces = new HashMap<String, ConnectionInterface>(); 
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		String[] messageSegments = message.split(" ");
		String username = messageSegments[0];
		String command = messageSegments[1];
		
		ConnectionInterface connectionInterface = 
			connectionInterfaces.get(username);
		// TODO change
		connectionInterface.sendMessage("server has received your message.");
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
			
			PrintWriter socketOutput = 
				new PrintWriter(clientSpecificSocket.getOutputStream());
			BufferedReader socketInput = 
				new BufferedReader(
				new InputStreamReader(clientSpecificSocket.getInputStream()));
			
			ConnectionInterface connectionInterface = 
				new ConnectionInterface(socketOutput, socketInput);
			connectionInterface.addMessageListener(this);
			// TODO make more readable
			String username = socketInput.readLine().split(" ")[0];
			connectionInterfaces.put(username, connectionInterface);
			
			Thread clientThread = new Thread(connectionInterface);
			clientThread.start();
		}
	}
}
