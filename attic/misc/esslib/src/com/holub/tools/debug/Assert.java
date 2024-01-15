package com.holub.tools.debug;

public class Assert
{
	public final static void failure( String message )
	{	throw new Assert.Failed(message);
	}

	public final static void is_true( boolean expression )
	{	if( !expression )
			throw( new Assert.Failed() );	
	}

	public final static void is_false( boolean expression )
	{	if( expression )
			throw( new Assert.Failed() );	
	}

	public final static void is_true( boolean expression, String message)
	{	if( !expression )
			throw( new Assert.Failed(message) );	
	}

	public final static void is_false( boolean expression, String message)
	{	if( expression )
			throw( new Assert.Failed(message) );	
	}

	static public class Failed extends RuntimeException
	{	public Failed(			  ){ super("Assert Failed"); }
		public Failed( String msg ){ super(msg); 			 }
	}
}	
