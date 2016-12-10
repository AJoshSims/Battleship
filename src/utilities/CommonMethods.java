package utilities;

/**
 * 
 * 
 * @author Evan Arroyo
 * @author Joshua Sims
 * @version 09 December 2016
 */
public final class CommonMethods
{
	public static final int verifyPort(String maybePort)
	{
		int port = CommonConstants.INVALID;
		try
		{
			port = Integer.parseInt(maybePort);
		}
		catch (NumberFormatException e)
		{
			port = CommonConstants.INVALID;
		}
		
		if (
			(port < CommonConstants.PORT_NUM_LOWER_BOUND) 
			|| (port > CommonConstants.PORT_NUM_UPPER_BOUND))
		{
			port = CommonConstants.INVALID;
		}
		
		return port;
	}
}
