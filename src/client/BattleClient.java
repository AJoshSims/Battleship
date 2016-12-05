package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.ConnectionInterface;
import common.MessageListener;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener
{
	ConnectionInterface connectionInterface;
	
	// TODO error handling
	BattleClient(InetAddress host, int port, String username)
		throws IOException
	{
		connectionInterface = new ConnectionInterface(new Socket(host, port));
	}
	
	@Override
	public void messageReceived(String message, MessageSource source)
	{

	}

	@Override
	public void sourceClosed(MessageSource source)
	{

	}
}
