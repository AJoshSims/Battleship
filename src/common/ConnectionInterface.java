package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConnectionInterface extends MessageSource implements Runnable
{
	private PrintWriter socketOutput;

	private BufferedReader socketInput;
		
	private String username;
	
	public ConnectionInterface(
		PrintWriter socketOutput, BufferedReader socketInput)
	{
		this.socketOutput = socketOutput;
		this.socketInput = socketInput;
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
		// TODO no forever loop
		// test if socketInputreadline return null would work
		// also only run while ^ that and while this guy has listeners
		while (true)
		{
			// TODO error handling
			String message = "";
			String messageSegment = "";
			while (!messageSegment.equals("end"))
			{
				try
				{			
					messageSegment = socketInput.readLine();
					message += messageSegment + "\n";
				}
				catch (IOException e)
				{
					// Try again.
				}
			}
			
			message = message.substring(0, message.length() - 4);
			
			notifyReceipt(message);
		}
	}
	
	public void sendMessage(String message)
	{
		socketOutput.print(message + "\nend\n");
		socketOutput.flush();
	}
}
