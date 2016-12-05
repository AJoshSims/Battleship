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

public class BattleClient extends MessageSource implements MessageListener
{
	private Socket clientSpecificSocket;
	private ConnectionInterface connectionInterface;
	
	String username;
	
	// TODO error handling
	BattleClient(InetAddress host, int port, String username)
		throws IOException
	{
		clientSpecificSocket = new Socket(host, port);
		PrintWriter socketOutput = 
			new PrintWriter(clientSpecificSocket.getOutputStream());
		BufferedReader socketInput = 
			new BufferedReader(
			new InputStreamReader(clientSpecificSocket.getInputStream()));
		
		connectionInterface = 
			new ConnectionInterface(socketOutput, socketInput);
		connectionInterface.addMessageListener(this);
		connectionInterface.run();
		
		this.username = username;
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		System.out.println(message);
	}

	@Override
	public void sourceClosed(MessageSource source)
	{
		
	}
	
	void sendMessage(String message)
	{
		connectionInterface.sendMessage(username + " " + message);
	}
}
