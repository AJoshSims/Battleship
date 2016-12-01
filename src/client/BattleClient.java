package client;

import common.MessageListener;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener
{
	@Override
	public void messageReceived(String message, MessageSource source)
	{

	}

	@Override
	public void sourceClosed(MessageSource source)
	{

	}
}
