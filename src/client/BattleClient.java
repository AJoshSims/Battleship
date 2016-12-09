package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.ConnectionInterface;
import common.MessageListener;
import common.MessageSource;

// TODO use string builder

class BattleClient extends MessageSource implements MessageListener
{	
	private ConnectionInterface connectionInterface;
		
	// TODO error handling
	BattleClient(InetAddress host, int port, String username)
		throws IOException
	{					
		// TODO close socket?
		Socket clientSpecificSocket = new Socket(host, port);
		
		// TODO run connectionInterface as thread for client?
		connectionInterface = new ConnectionInterface(clientSpecificSocket);
		connectionInterface.addMessageListener(this);
		Thread thread = new Thread(connectionInterface);
		thread.start();
		
		sendCommand("/join " + username);
	}
	
	boolean isConnected()
	{
		return connectionInterface.isSocketAlive();
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{
		System.out.println(message);
		System.out.flush();
	}

	@Override
	public void sourceClosed(MessageSource source)
	{
		
	}
	
	void sendCommand(String message)
	{
		connectionInterface.sendMessage(message);
	}
}
