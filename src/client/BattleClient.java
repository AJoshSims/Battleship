package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.ConnectionInterface;
import common.MessageListener;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener
{
	private ConnectionInterface connectionInterface;
	
	String username;
	
	// TODO error handling
	BattleClient(InetAddress host, int port, String username)
		throws IOException
	{
		connectionInterface = new ConnectionInterface(new Socket(host, port));
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
	
	private void sendMessage(String message)
	{
		connectionInterface.sendMessage(message);
	}
}
