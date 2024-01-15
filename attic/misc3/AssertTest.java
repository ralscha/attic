
import java.io.*;
import java.util.*;

class Assert
{
  private static void fail()
  {
    System.err.println( "Assertion failed:" );

    Throwable e = new Throwable();
    e.printStackTrace();

    System.exit( 1 );
  }

  public static void assert( boolean aBoolean ) {
    if ( !aBoolean )
      fail();
  }

  public static void assert( char aChar ) {
    if ( aChar == '\0' )
      fail();
  }

  public static void assert( long aLong ) {
    if ( aLong == 0L )
      fail();
  }

  public static void assert( double aDouble ) {
    if ( aDouble == 0.0 )
      fail();
  }

  public static void assert( Object anObject ) {
    if ( anObject == null )
      fail();
  }
}

class Assert2
{
  private static void fail()
  {
    System.err.println( "Assertion failed:" );

    StringWriter sw = new StringWriter();

    Throwable e = new Throwable();
    e.printStackTrace( new PrintWriter( sw ) );

    StringTokenizer st = new StringTokenizer( sw.toString(), "\n");

    // Don't think about time and space
    st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken();

    while ( st.hasMoreTokens() )
      System.out.println( ( (String) st.nextToken() ).trim() );

    System.exit( 1 );
  }

  public static void assert( boolean aBoolean ) {
    if ( !aBoolean )
      fail();
  }

  public static void assert( char aChar ) {
    if ( aChar == '\0' )
      fail();
  }

  public static void assert( long aLong ) {
    if ( aLong == 0L )
      fail();
  }

  public static void assert( double aDouble ) {
    if ( aDouble == 0.0 )
      fail();
  }

  public static void assert( Object anObject ) {
    if ( anObject == null )
      fail();
  }
}


public class AssertTest
{
  static void foo()
  {
    int i = 0;
    Assert.assert( i < 10 );

    float f = 0.0F;
    Assert2.assert( f );
  }
  
  public static void main( String args[] )
  {
    foo();
  }
}