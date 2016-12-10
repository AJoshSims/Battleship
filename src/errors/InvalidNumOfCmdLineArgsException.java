package errors;

import utilities.CommonConstants;

/**
 * 
 * 
 * @author Evan Arroyo
 * @author Joshua Sims
 * @version 09 December 2016
 */
public final class InvalidNumOfCmdLineArgsException extends Exception
{
	public InvalidNumOfCmdLineArgsException(String usageMessage)
	{
		super("You have specified an invalid number of command line " +
			"arguments." +
			"\n" + usageMessage +
			"\n\n" + CommonConstants.ABORT_MESSAGE);
	}
}
