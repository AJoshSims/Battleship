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
	
	private HashMap<String, ConnectionInterface> clientsJoined;
	
	private HashMap<String, ConnectionInterface> clientsInGame;
	
	private HashMap<String, ConnectionInterface> clientsStanding;
		
	// TODO error handling
	BattleServer(int port, int boardSize) throws IOException
	{
		clientsJoined = new HashMap<String, ConnectionInterface>();
		clientsInGame = new HashMap<String, ConnectionInterface>();
		clientsStanding = new HashMap<String, ConnectionInterface>();
		this.boardSize = boardSize;
		game = null;
		welcomeSocket = new ServerSocket(port);
	}
	
	// TODO string builder
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		// TODO change
		if (!(source instanceof ConnectionInterface))
		{
			return;
		}
		
		ConnectionInterface connectionInterface = 
			(ConnectionInterface) source;
		
		String usernameSource = connectionInterface.getUsername();
					
		String[] messageSegments = message.split(" ");
		
		// TODO game in progress
		// TODO mag num
		// TODO error handling
		boolean invalidCommand = false;
		
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
				break;
				
			default:
				invalidCommand = true;
		}
		
		// TODO robust error handling for invalid commands
		String messageBroadcast = null;
		ConnectionInterface clientThat = null;
		
		if (arg01 == null)
		{
			invalidCommand = true;
		}
		
		else
		{
			switch (arg01)
			{
				case "/join":
					if ((arg02 == null) || (arg03 != null))
					{
						invalidCommand = true;
					}
					
					else if (!usernameSource.equals(""))
					{
						connectionInterface.sendMessage(
							"You have already joined");
					}
					
					else if (clientsJoined.containsKey(arg02) || arg02.equals(""))
					{
						connectionInterface.sendMessage(
							"The username \"" + arg02 + "\" is unavailable" + 
							"\nEnter /join followed by a different username");
					}
					
					else
					{
						clientsJoined.put(arg02, connectionInterface);
						connectionInterface.setUsername(arg02);
						
						for (String username : clientsJoined.keySet())
						{
							if (!clientsInGame.containsKey(username))
							{			
								clientThat = clientsJoined.get(username);
								clientThat.sendMessage(
									"!!! " + arg02 + " has joined");
							}
						}
					}
					break;
					
				case "/play":
					if (arg02 != null)
					{
						invalidCommand = true;
					}
					
					else if (game != null)
					{
						connectionInterface.sendMessage(
							"Game already in progress");
					}
					
					else
					{
						game = new Game();
						for (String username : clientsJoined.keySet())
						{	
							game.addGrid(username, boardSize);
							clientsInGame.put(arg02, connectionInterface);
							clientsStanding.put(username, clientsInGame.get(username));

							clientThat = clientsInGame.get(username);
							clientThat.sendMessage("The game begins");
						}
					}
					break;
					
				case "/attack":
					if ((arg02 == null) || (arg03 == null) || (arg04 == null))
					{
						invalidCommand = true;
					}
					
					else
					{	
						if (game == null)
						{
							connectionInterface.sendMessage(
								"Game not in progress");
						}
						
						else if (!clientsInGame.containsKey(arg02))
						{
							connectionInterface.sendMessage(
								"No such user has joined this game");
						}
						
						else if (clientsStanding.get(arg02) == null)
						{
							connectionInterface.sendMessage(
								"That user has already been eliminated");
						}
						
						else
						{
							int row = -1;
							int column = -1;
							char[] maybeRow = arg03.toCharArray();
							char[] maybeColumn = arg04.toCharArray();
					    	for (char character : maybeRow)
					    	{
					    		// If part of the third command line argument is not a digit, 
					    		// then the third command line argument cannot be a port number.
					    		if (!Character.isDigit(character))
					    		{
					    			invalidCommand = true;
					    		}
					    	}
					    	for (char character : maybeColumn)
					    	{
					    		// If part of the third command line argument is not a digit, 
					    		// then the third command line argument cannot be a port number.
					    		if (!Character.isDigit(character))
					    		{
					    			invalidCommand = true;
					    		}
					    	}				    	
					    	if (invalidCommand == false)
					    	{
					    		row = Integer.parseInt(arg03);
					    		column = Integer.parseInt(arg04);
					    	}
							
							game.attack(arg02, row, column);
							
							messageBroadcast = 
								"Shots Fired at " + arg02 +
								" by " + usernameSource;
							
							if (
								game.getGrid(arg02).getRemainingShipSegments()
								== 0)
							{
								messageBroadcast += 
									"\n!!! All ships belonging to " + arg02 + 
									" have been destroyed";
								clientsStanding.remove(arg02);
								
								if (clientsStanding.size() == 1)
								{
									// TODO switch turn message before win?
									game = null;
									messageBroadcast += "\nGAME OVER: " + 
										clientsStanding.keySet().toArray()[0] +
										" wins!";
									clientsStanding.clear();
									
									for (String username : clientsJoined.keySet())
									{
										clientThat = clientsJoined.get(username);
										
										if (clientsInGame.get(username) != null)
										{								
											clientThat.sendMessage(messageBroadcast);
										}
										
										else
										{
											clientThat.sendMessage(
												"Game has ended -- " +
												"a new game may begin");
										}
									}
									
									clientsInGame.clear();
								}
							}
						}
					}
					break;
					
				case "/show":
					if ((arg02 == null) || (arg03 != null))
					{
						invalidCommand = true;
					}
					
					else if (arg02 != null)
					{
						if (game == null)
						{
							connectionInterface.sendMessage(
								"Game not in progress");
							return;
						}
						
						else if (!clientsInGame.containsKey(arg02))
						{
							connectionInterface.sendMessage(
								"No such user has joined this game");
						}
						
						else if (clientsStanding.get(arg02) == null)
						{
							connectionInterface.sendMessage(
								"That user has already been eliminated");
						}
						
						else
						{
							connectionInterface.sendMessage(
								game.getGrid(arg02)
								.getBoardString(
								usernameSource));
						}
					}
					break;
					
				case "/users":
					if (arg02 != null)
					{
						invalidCommand = true;
					}
					
					else
					{
						String users = "";
						for (String username : clientsJoined.keySet())
						{
							users += 
								username + "\n";
						}		
						users = 
							users.substring(
							0, users.length() - 1);
						
						connectionInterface.sendMessage(users);
					}
					break;
					
					//TODO handle client side of quit
				case "/quit":
					if (arg02 != null)
					{
						invalidCommand = true;
					}
					
					else if (game == null)
					{
						connectionInterface.sendMessage(
							"Game not in progress");
					}
					
					else
					{
						clientsStanding.remove(usernameSource);
						clientsInGame.replace(usernameSource, null);
						
						messageBroadcast = 
							"!!! " + usernameSource + " surrendered";
						
						if (clientsStanding.size() == 1)
						{
							// TODO switch turn message before win?
							game = null;
							messageBroadcast += "\nGAME OVER: " + 
								clientsStanding.keySet().toArray()[0] +
								" wins!";
							clientsStanding.clear();
							
							for (String username : clientsJoined.keySet())
							{
								clientThat = clientsJoined.get(username);
								
								if (clientsInGame.get(username) != null)
								{								
									clientThat.sendMessage(messageBroadcast);
								}
								
								else
								{
									clientThat.sendMessage(
										"Game has ended -- " +
										"a new game may begin");
								}
							}
							
							clientsInGame.clear();
						}
					}
					break;
					
				case "/help":
					if (arg02 != null)
					{
						invalidCommand = true;
					}
					
					else
					{
						connectionInterface.sendMessage(
							"/join <username>" +
							"\n/play" +
							"\n/attack <username> <[0-9]+> <[0-9]+>" +
							"\n/show <username>" +
							"\n/users" +
							"\n/quit");	
					}
					break;
					
				default:
					invalidCommand = true;
			}
		}
		
		if (invalidCommand == true)
		{
			connectionInterface.sendMessage(
				"Invalid command: " + message);
		}		
	}

	@Override
	public void sourceClosed(MessageSource source)
	{
		if (source instanceof ConnectionInterface)
		{
			ConnectionInterface connectionInterface =
				(ConnectionInterface) source;
			String usernameSource = connectionInterface.getUsername();

			connectionInterface.removeMessageListener(this);
			
			clientsJoined.remove(usernameSource);
		}
	}
	
	// TODO error handling
	void listen() throws IOException
	{
		Socket clientSpecificSocket = null;
		// TODO no forever loop
		// while welcomesocket is working?
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
