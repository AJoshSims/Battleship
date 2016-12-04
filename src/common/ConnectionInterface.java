package common;

import java.net.Socket;

public class ConnectionInterface extends MessageSource implements Runnable
{
	Socket clientSpecificSocket;
	
	public ConnectionInterface(Socket clientSpecificSocket)
	{
		this.clientSpecificSocket = clientSpecificSocket;
	}
	
	public void run()
	{
		
	}
}
