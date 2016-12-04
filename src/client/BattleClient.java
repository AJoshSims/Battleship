package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.MessageListener;
import common.MessageSource;

// TODO no globals?

public class BattleClient extends MessageSource implements MessageListener
{
	Socket clientSpecificSocket;
	
	ConnectionInterface connectionInterface;
	
	// TODO error handling
	BattleClient(InetAddress host, int portNum, String username)
		throws IOException
	{
		clientSpecificSocket = new Socket(host, portNum);
		connectionInterface 
	}
	
	@Override
	public void messageReceived(String Smessage, MessageSource source)
	{
		
	}

	@Override
	public void sourceClosed(MessageSource source)
	{

	}
}
