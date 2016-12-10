package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import errors.InvalidNumOfCmdLineArgsException;
import errors.InvalidPortException;
import utilities.CommonConstants;
import utilities.CommonMethods;

/**
 * 
 * 
 * @author Evan Arroyo
 * @author Joshua Sims
 * @version 09 December 2016
 */
final class BattleDriver
{
	private static int exitCode = 0;
	
	private static final String USAGE_MESSAGE =
		"Usage: java client/BattleDriver <host> <port> <username>";
	
	private static final int HOST_AND_PORT_AND_USERNAME = 3;
	
	private static final int HOST_INDEX = 0;
	
	private static final int PORT_INDEX = 1;
	
	private static final int USERNAME_INDEX = 2;
	
	public static void main(String[] cmdLineArgs)
	{	
		try (Scanner userInput = new Scanner(System.in))
		{
			Object[] validCmdLineArgs = parseCmdLineArgs(cmdLineArgs);
			InetAddress host = (InetAddress) validCmdLineArgs[HOST_INDEX];
			int port = (int) validCmdLineArgs[PORT_INDEX];
			String username = (String) validCmdLineArgs[USERNAME_INDEX];
			
			BattleClient battleClient = 
				new BattleClient(host, port, username);
			
			String command = null;
			while (battleClient.isConnected())
			{
				command = userInput.nextLine();
				battleClient.sendCommand(command);
			}
			
			userInput.close();
		}
		
		catch (InvalidNumOfCmdLineArgsException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.INVALID_NUM_OF_CMD_LINE_ARGS;
		}
		catch (UnknownHostException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.UNKNOWN_HOST;
		}
		catch (InvalidPortException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.INVALID_PORT;
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.IO_EXCEPTION;
		}
		finally
		{
			System.exit(exitCode);
		}
	}
	
	private static Object[] parseCmdLineArgs(String[] cmdLineArgs)
		throws 
		InvalidNumOfCmdLineArgsException,
		UnknownHostException,
		InvalidPortException
	{
		if (cmdLineArgs.length != HOST_AND_PORT_AND_USERNAME)
		{
			throw new InvalidNumOfCmdLineArgsException(USAGE_MESSAGE);
		}

		InetAddress host = null;
		try
		{
			host = InetAddress.getByName(cmdLineArgs[HOST_INDEX]);			
		}
		catch (UnknownHostException e)
		{
			throw new UnknownHostException(
				"Unknown host: " + e.getMessage() +
				"\n" + USAGE_MESSAGE + 
				"\n\n" + CommonConstants.ABORT_MESSAGE);
		}
		
		Integer port = CommonMethods.verifyPort(cmdLineArgs[PORT_INDEX]);
		if (port == CommonConstants.INVALID)
		{
			throw new InvalidPortException(USAGE_MESSAGE);
		}
		
		Object[] validCmdLineArgs = {host, port, cmdLineArgs[USERNAME_INDEX]};
		return validCmdLineArgs;
	}
}
