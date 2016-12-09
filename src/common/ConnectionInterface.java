package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionInterface extends MessageSource implements Runnable
{
	private PrintWriter socketOutput;

	private BufferedReader socketInput;
	
	private boolean socketAlive;
		
	private String username;
	
	public ConnectionInterface(Socket clientSpecificSocket) 
		throws IOException
	{
		socketOutput = 
			new PrintWriter(clientSpecificSocket.getOutputStream());
		socketInput = 
			new BufferedReader(
			new InputStreamReader(clientSpecificSocket.getInputStream()));
		socketAlive = true;
		username = "";
	}
	
	public boolean isSocketAlive()
	{
		return socketAlive;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void run()
	{
		while (socketAlive == true)
		{
			// TODO error handling
			String message = "";
			String messageSegment = "";
			while ((socketAlive == true) && !messageSegment.equals("end"))
			{
				try
				{			
					messageSegment = socketInput.readLine();
					message += messageSegment + "\n";
				}
				catch (IOException e)
				{
					// Abandons socket, assuming the remote side has closed
					socketAlive = false;
					closeMessageSource();
				}
			}
			
			if (socketAlive == true)
			{
				message = message.substring(0, message.length() - 4);
				
				notifyReceipt(message);
			}
		}
	}
	
	public void sendMessage(String message)
	{
		if (socketAlive == true)
		{
			socketOutput.print(message + "\nend\n");
			socketOutput.flush();			
		}
	}
}
