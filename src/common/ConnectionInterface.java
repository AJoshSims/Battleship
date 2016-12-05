package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConnectionInterface extends MessageSource implements Runnable
{
	private PrintWriter socketOutput;

	private BufferedReader socketInput;
	
	public ConnectionInterface(
		PrintWriter socketOutput, BufferedReader socketInput)
	{
		this.socketOutput = socketOutput;
		this.socketInput = socketInput;
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
	
	public void sendMessage(String message)
	{
		socketOutput.print(message);
		run();
	}
}
