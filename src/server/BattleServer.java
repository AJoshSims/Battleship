package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
			
			String arg04 = null;
			String arg03 = null;
			String arg02 = null;
			String arg01 = null;
			switch (messageSegments.length)
			{
				case 4:
					arg04 = messageSegments[3];
					if (arg04.charAt(arg04.length() - 1) == '\n')
					{
						arg04 = arg04.substring(0, arg04.length() - 1);
					}
				case 3:
					arg03 = messageSegments[2];
					if (arg03.charAt(arg03.length() - 1) == '\n')
					{
						arg03 = arg03.substring(0, arg03.length() - 1);
					}
				case 2:
					arg02 = messageSegments[1];
					if (arg02.charAt(arg02.length() - 1) == '\n')
					{
						arg02 = arg02.substring(0, arg02.length() - 1);
					}
				case 1:
					arg01 = messageSegments[0];
					if (arg01.charAt(arg01.length() - 1) == '\n')
					{
						arg01 = arg01.substring(0, arg01.length() - 1);
					}
				case 0:
					break;
			}
			
			// TODO remove
			System.out.println(arg01);
			
			switch (arg01)
			{
				case "/join":
					// TODO remove
					System.out.println(arg02);
					
					if (joinedClients.containsKey(arg01))
					{
						connectionInterface.sendMessage(
							"The username \"" + arg02 + "\" is already " +
							"taken" + 
							"\nEnter /join followed by a different username");
						return;	
					}
					
					joinedClients.put(arg02, connectionInterface);
					connectionInterface.setUsername(arg02);
					connectionInterface.sendMessage("!!! " + arg02 + 
						" has joined");
					break;
					
				case "/play":
					if (game == null)
					{
						game = new Game();
						for (String guy : joinedClients.keySet())
						{	
							game.addGrid(
								joinedClients.get(guy).getUsername(), boardSize);
						}
					}
					break;
					
				case "/attack":
					int row = Integer.parseInt(arg03);
					int column = Integer.parseInt(arg04); 
					if (game != null)
					{
						game.attack(arg02, row, column);
					}
					break;
					
				case "/show":
					if (game != null)
					{
						connectionInterface.sendMessage(
							game.getGridString(
							arg02, connectionInterface.getUsername()));
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
