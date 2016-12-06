package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import common.ConnectionInterface;

//TODO no global variables?

import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener
{		
	private final int boardSize;
	
	private Game game;
	
	private ServerSocket welcomeSocket;
	
	private HashMap<String, ConnectionInterface> joinedClients = 
		new HashMap<String, ConnectionInterface>();
		
	// TODO error handling
	BattleServer(int port, int boardSize) throws IOException
	{
		this.boardSize = boardSize;
		game = null;
		welcomeSocket = new ServerSocket(port);
	}
	
	// TODO string builder
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		// TODO change
		if (source instanceof ConnectionInterface)
		{
			ConnectionInterface connectionInterface = 
				(ConnectionInterface) source;
			
			String[] messageSegments = message.split(" ");
			
			// TODO game in progress
			// TODO mag num
			// TODO error handling
			String command = messageSegments[0];
			String username = messageSegments[1];
			switch (command)
			{
				case "/join":
					if (joinedClients.containsKey(username))
					{
						connectionInterface.sendMessage(
							"The username \"" + username + "\" is already " +
							"taken" + 
							"\nEnter /join followed by a different username");
						return;	
					}
					
					joinedClients.put(username, connectionInterface);
					connectionInterface.setUsername(username);
					connectionInterface.sendMessage("!!! " + username + 
						" has joined");
					break;
					
				case "/play":
					if (game == null)
					{
						game = new Game();
						for (
							int gridsToAdd = joinedClients.size(); 
							gridsToAdd > 0;
							--gridsToAdd)
						{
							game.addGrid(
								connectionInterface.getUsername(), boardSize);
						}
					}
					break;
					
				case "/attack":
					int row = Integer.parseInt(messageSegments[2]);
					int column = Integer.parseInt(messageSegments[3]); 
					if (game != null)
					{
						game.attack(username, row, column);
					}
					break;
					
				case "/show":
					if (game != null)
					{
						connectionInterface.sendMessage(
							game.getGridString(username));
					}
					break;
					
				case "/users":
					String joinedClientsUsernames = "";
					for (String joinedClientUsername : joinedClients.keySet())
					{
						joinedClientsUsernames += 
							joinedClientUsername + "\n";
					}
					joinedClientsUsernames = 
						joinedClientsUsernames.substring(
						0, joinedClientsUsernames.length() - 1);
					connectionInterface.sendMessage(joinedClientsUsernames);
					break;
					
				case "/quit":
					
					break;
				case "/help":
					connectionInterface.sendMessage(
						"/join <username>" +
						"\n/play" +
						"\n/attack <username> <[0-9]+> <[0-9]+>" +
						"\n/show <username>" +
						"\n/users" +
						"\n/quit");
					break;
				default:
					connectionInterface.sendMessage(
						"That is an invalid command" +
						"\nEnter /help to get a list of valid commands");
			}						
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
