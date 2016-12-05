package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionInterface extends MessageSource implements Runnable
{
	Socket clientSpecificSocket;
	
	BufferedReader fromClient;
	
	PrintWriter toClient;
	
	// TODO error handling
	public ConnectionInterface(Socket clientSpecificSocket)
		throws IOException
	{
		this.clientSpecificSocket = clientSpecificSocket;
		
		fromClient = new BufferedReader(new InputStreamReader(
			clientSpecificSocket.getInputStream()));
        toClient = new PrintWriter(clientSpecificSocket.getOutputStream());
	}
	
	public void run()
	{
		// TODO forever loop
		while (true)
		{
			// TODO error handling
			// TODO different read?
			String message = null;
			while (message == null)
			{
				try
				{
					message = fromClient.readLine();
				}
				catch (IOException e)
				{
					message = null;
				}
			}
			
			notifyReceipt(message);
		}
	}
}
