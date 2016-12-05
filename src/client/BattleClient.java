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

// TODO no globals?

public class BattleClient extends MessageSource implements MessageListener
{
	// TODO error handling
	BattleClient(InetAddress host, int portNum, String username)
		throws IOException
	{

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
