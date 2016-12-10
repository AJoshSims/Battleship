package server;

import java.io.IOException;

import errors.InvalidBoardSizeException;
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
final class BattleShipDriver
{	
	private static int exitCode = 0;
		
	private static final String USAGE_MESSAGE = 
		"Usage: java server/BattleShipDriver <port> <board_size>";
	
	private static final int PORT_ONLY = 1;
	
	private static final int PORT_AND_BOARD_SIZE = 2;
	
	private static final int PORT_INDEX = 0;
	
	private static final int BOARD_SIZE_INDEX = 1;
	
	private static final int BOARD_SIZE_DEFAULT = 10;
	
	private static final int BOARD_SIZE_MIN = 5;
	
	public static final void main(String[] cmdLineArgs)
	{		
		try
		{
			int[] validCmdLineArgs = parseCmdLineArgs(cmdLineArgs);
			int port = validCmdLineArgs[PORT_INDEX];
			int boardSize = validCmdLineArgs[BOARD_SIZE_INDEX];
			
			BattleServer battleServer = new BattleServer(port, boardSize);
			battleServer.listen();	
		}
		
		catch (InvalidNumOfCmdLineArgsException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.INVALID_NUM_OF_CMD_LINE_ARGS;
		}
		catch (InvalidPortException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.INVALID_PORT;
		}
		catch (InvalidBoardSizeException e)
		{
			System.err.println(e.getMessage());
			exitCode = ExitCodes.INVALID_BOARD_SIZE;
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
	
	private static int[] parseCmdLineArgs(String[] cmdLineArgs)
		throws 
		InvalidNumOfCmdLineArgsException,
		InvalidPortException,
		InvalidBoardSizeException
	{
		int port = CommonConstants.INVALID;
		int boardSize = BOARD_SIZE_DEFAULT;
		switch (cmdLineArgs.length)
		{
			case PORT_AND_BOARD_SIZE:
				boardSize = verifyBoardSize(cmdLineArgs[BOARD_SIZE_INDEX]);
				if (boardSize == CommonConstants.INVALID)
				{
					throw new InvalidBoardSizeException(USAGE_MESSAGE);
				}
				
			case PORT_ONLY:
				port = CommonMethods.verifyPort(cmdLineArgs[PORT_INDEX]);
				if (port == CommonConstants.INVALID)
				{
					throw new InvalidPortException(USAGE_MESSAGE);
				}
				break;
				
			default:
				throw new InvalidNumOfCmdLineArgsException(USAGE_MESSAGE);
		}
		
		int[] validCmdLineArgs = {port, boardSize};
		return validCmdLineArgs;
	}
	
	private static final int verifyBoardSize(String maybeBoardSize)
	{
		int boardSize = CommonConstants.INVALID;
		try
		{
			boardSize = Integer.parseInt(maybeBoardSize);
		}
		catch (NumberFormatException e)
		{
			boardSize = CommonConstants.INVALID;
		}
		
		if (boardSize < BOARD_SIZE_MIN)
		{
			boardSize = CommonConstants.INVALID;
		}
		
		return boardSize;
	}
}