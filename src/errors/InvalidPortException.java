package errors;

import utilities.CommonConstants;

/**
 * 
 * 
 * @author Evan Arroyo
 * @author Joshua Sims
 * @version 09 December 2016
 */
public final class InvalidPortException extends Exception
{
	public InvalidPortException(String usageMessage)
	{
		super("You have specified an invalid port. The port number must " +
			"be an integer \nbetween " + 
			CommonConstants.PORT_NUM_LOWER_BOUND +
			" and " + CommonConstants.PORT_NUM_UPPER_BOUND +
			"\n" + usageMessage +
			"\n\n" + CommonConstants.ABORT_MESSAGE);
	}
}
