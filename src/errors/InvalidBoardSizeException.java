package errors;

import utilities.CommonConstants;

/**
 * 
 * 
 * @author Evan Arroyo
 * @author Joshua Sims
 * @version 09 December 2016
 */
public final class InvalidBoardSizeException extends Exception
{
	public InvalidBoardSizeException(String usageMessage)
	{
		super("You have specified an invalid board size. The board size " +
			"must be a positive \ninteger greater than 4." +
			"\n" + usageMessage +
			"\n\n" + CommonConstants.ABORT_MESSAGE);
	}
}
