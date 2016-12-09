package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import common.ConnectionInterface;
import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener
{		
	private final int boardSize;
	
	private Game game;
	
	private ServerSocket welcomeSocket;
	
	private HashMap<String, ConnectionInterface> clientsJoined;
	
	private HashMap<String, ConnectionInterface> clientsInGame;
	
	private String[] clientsStanding;
	
	private int clientsStandingMax;
	
	private int clientsStandingActual;
	
	private int actingClient;
		
	// TODO error handling
	BattleServer(int port, int boardSize) throws IOException
	{
		clientsJoined = new HashMap<String, ConnectionInterface>();
		clientsInGame = new HashMap<String, ConnectionInterface>();
		clientsStanding = null;
		clientsStandingMax = -1;
		clientsStandingActual = -1;
		actingClient = -1;
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
				if (arg04.isEmpty())
				{
					invalidCommand = true;
				}
				
				else if (arg04.charAt(arg04.length() - 1) == '\n')
				{
					arg04 = arg04.substring(0, arg04.length() - 1);
					
					if (arg04.isEmpty())
					{
						invalidCommand = true;
					}
				}
			case 3:
				arg03 = messageSegments[2];
				if (arg03.isEmpty())
				{
					invalidCommand = true;
				}
				
				else if (arg03.charAt(arg03.length() - 1) == '\n')
				{
					arg03 = arg03.substring(0, arg03.length() - 1);
					
					if (arg03.isEmpty())
					{
						invalidCommand = true;
					}
				}
			case 2:
				arg02 = messageSegments[1];
				if (arg02.isEmpty())
				{
					invalidCommand = true;
				}
				
				else if (arg02.charAt(arg02.length() - 1) == '\n')
				{
					arg02 = arg02.substring(0, arg02.length() - 1);
					
					if (arg02.isEmpty())
					{
						invalidCommand = true;
					}
				}
			case 1:
				arg01 = messageSegments[0];
				if (arg01.isEmpty())
				{
					invalidCommand = true;
				}
				
				else if (arg01.charAt(arg01.length() - 1) == '\n')
				{
					arg01 = arg01.substring(0, arg01.length() - 1);
					
					if (arg01.isEmpty())
					{
						invalidCommand = true;
					}
				}
				break;
				
			default:
				invalidCommand = true;
		}
		
		// TODO robust error handling for invalid commands
		String messageBroadcast = null;
		ConnectionInterface clientThat = null;
		
		if (invalidCommand == false)
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

					// TODO user has to be joined to execute commands?
					else if (usernameSource.equals(""))
					{
						connectionInterface.sendMessage(
							"You must join first");
					}
					
					else if (game != null)
					{
						connectionInterface.sendMessage(
							"Game already in progress");
					}
					
					else if (clientsJoined.size() < 2)
					{
						connectionInterface.sendMessage("Not enough players " +
							"to play the game");
					}
					
					else
					{
						clientsStandingActual = 
							clientsStandingMax = 
							clientsJoined.size();
						clientsStanding = new String[clientsStandingMax];
						clientsJoined.keySet().toArray(clientsStanding);
						actingClient = 0;
						game = new Game();
						for (String username : clientsStanding)
						{	
							game.addGrid(username, boardSize);
							clientsInGame.put(username, clientsJoined.get(username));

							clientThat = clientsInGame.get(username);
							clientThat.sendMessage(
								"The game begins" +
								"\n" + clientsStanding[actingClient] + 
								" it is your turn");
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
						int row = -1;
						int column = -1;
						char[] maybeRow = arg03.toCharArray();
						char[] maybeColumn = arg04.toCharArray();
				    	for (char character : maybeRow)
				    	{
				    		if (!Character.isDigit(character))
				    		{
				    			invalidCommand = true;
				    		}
				    	}
				    	for (char character : maybeColumn)
				    	{
				    		if (!Character.isDigit(character))
				    		{
				    			invalidCommand = true;
				    		}
				    	}
				    	
				    	if (invalidCommand == true)
				    	{
				    		// TODO or do nothing
				    		break;
				    	}
						
						else if (usernameSource.equals(""))
						{
							connectionInterface.sendMessage(
								"You must join first");
						}
				    	
				    	else if (game == null)
						{
							connectionInterface.sendMessage(
								"Game not in progress");
						}
						
						else if (clientsInGame.get(usernameSource) == null)
						{
							connectionInterface.sendMessage(
								"You are not in the current game");
						}
						
						else if (!clientsInGame.containsKey(arg02))
						{
							connectionInterface.sendMessage(
								"No such user entered this game");
						}
						
						else if (!clientStandingExists(arg02))
						{
							connectionInterface.sendMessage(
								"That user has already been eliminated");
						}
						
						else
						{
				    		row = Integer.parseInt(arg03);
				    		column = Integer.parseInt(arg04);
							
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
								--clientsStandingActual;
								
								if (clientsStandingActual == 1)
								{
									String winner = null;
									while (
										(winner = clientsStanding[actingClient])
										== null)
									{
										++actingClient;
										if (actingClient >= clientsStandingMax)
										{
											actingClient = 0;
										}
									}
									
									game = null;
									messageBroadcast += "\nGAME OVER: " + 
										winner + " wins!";
									clientsStanding = null;
									clientsStandingActual = -1;
									clientsStandingMax = -1;
									
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
												"Game has ended" +
												"\nA new game may begin");
										}
									}
									
									clientsInGame.clear();
									break;
								}
							}
							
							++actingClient;
							if (actingClient >= clientsStandingMax)
							{
								actingClient = 0;
							}
							while (clientsStanding[actingClient] == null)
							{
								++actingClient;
								if (actingClient >= clientsStandingMax)
								{
									actingClient = 0;
								}
							}
							messageBroadcast += 
								"\n" + clientsStanding[actingClient] + 
								" it is your turn";
							
							for (String username : clientsInGame.keySet())
							{
								clientThat = clientsInGame.get(username);
								if (clientThat != null)
								{
									clientThat.sendMessage(messageBroadcast);
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
					
					else if (usernameSource.equals(""))
					{
						connectionInterface.sendMessage(
							"You must join first");
					}
					
					else if (arg02 != null)
					{
						if (game == null)
						{
							connectionInterface.sendMessage(
								"Game not in progress");
							return;
						}
						
						else if (clientsInGame.get(usernameSource) == null)
						{
							connectionInterface.sendMessage(
								"You are not in the current game");
						}
						
						else if (!clientsInGame.containsKey(arg02))
						{
							connectionInterface.sendMessage(
								"No such user entered this game");
						}
						
						else if (!clientStandingExists(arg02))
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
					
					else if (usernameSource.equals(""))
					{
						connectionInterface.sendMessage(
							"You must join first");
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
					
					else if (usernameSource.equals(""))
					{
						connectionInterface.sendMessage(
							"You must join first");
					}
					
					else if (game == null)
					{
						connectionInterface.sendMessage(
							"Game not in progress");
					}
					
					else if (clientsInGame.get(usernameSource) == null)
					{
						connectionInterface.sendMessage(
							"You are not in the current game");
					}
					
					else
					{
						--clientsStandingActual;
						clientsInGame.replace(usernameSource, null);
						
						messageBroadcast = 
							"!!! " + usernameSource + " surrendered";
						
						if (clientsStandingActual == 1)
						{
							game = null;

							String winner = null;
							while (
								(winner = clientsStanding[actingClient])
								== null)
							{
								++actingClient;
								if (actingClient >= clientsStandingMax)
								{
									actingClient = 0;
								}
							}
							
							messageBroadcast += "\nGAME OVER: " + winner + 
								" wins!";
							clientsStanding = null;
							clientsStandingActual = -1;
							clientsStandingMax = -1;
							
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
										"Game has ended" +
										"\nA new game may begin");
								}
							}
							
							clientsInGame.clear();
							break;
						}
						
						++actingClient;
						if (actingClient >= clientsStandingMax)
						{
							actingClient = 0;
						}
						while (clientsStanding[actingClient] == null)
						{
							++actingClient;
							if (actingClient >= clientsStandingMax)
							{
								actingClient = 0;
							}
						}
						messageBroadcast += 
							"\n" + clientsStanding[actingClient] + 
							" it is your turn";
						
						for (String username : clientsInGame.keySet())
						{
							clientThat = clientsInGame.get(username);
							if (clientThat != null)
							{
								clientThat.sendMessage(messageBroadcast);
							}
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
	
	private boolean clientStandingExists(String username)
	{
		for (
			int usernameIndex = 0;
			usernameIndex < clientsStanding.length;
			++usernameIndex)
		{
			if (clientsStanding[usernameIndex] == username)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private void removeClientStanding(String username)
	{
		for (
			int usernameIndex = 0;
			usernameIndex < clientsStanding.length;
			++usernameIndex)
		{
			if (clientsStanding[usernameIndex] == username)
			{
				clientsStanding[usernameIndex] = null;
			}
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
			removeClientStanding(usernameSource);
			clientsInGame.replace(usernameSource, null);
			
			String 	messageBroadcast = 
				"!!! " + usernameSource + " surrendered";
			
			ConnectionInterface clientThat = null;
			if (clientsStandingActual == 1)
			{
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
							"Game has ended" +
							"\nA new game may begin");
					}
				}
				
				clientsInGame.clear();
				return;
			}
			
			for (String username : clientsInGame.keySet())
			{
				clientThat = clientsInGame.get(username);
				if (clientThat != null)
				{
					clientThat.sendMessage(messageBroadcast);
				}
			}
		}
	}
	
	void listen() 
	{
		Socket clientSpecificSocket = null;
		boolean isAlive = true;
		while (isAlive == true)
		{
			try
			{
				clientSpecificSocket = welcomeSocket.accept();				
						
				ConnectionInterface connectionInterface = 
					new ConnectionInterface(clientSpecificSocket);
				connectionInterface.addMessageListener(this);

				Thread clientThread = new Thread(connectionInterface);
				clientThread.start();
			}
			
			catch (IOException | SecurityException e)
			{
				System.err.println(e.getMessage());
				isAlive = false;
			}
		}
	}
}
