package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionInterface extends MessageSource implements Runnable
{
	private Socket socket;

	private PrintWriter socketOutput;

	private BufferedReader socketInput;
	
	public ConnectionInterface(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		// TODO error handling
		String message = null;
		while (message == null)
		{
			try
			{
				message = socketInput.readLine();
			}
			catch (IOException e)
			{
				message = null;
			}
		}
		
		notifyReceipt(message);
	}
}
