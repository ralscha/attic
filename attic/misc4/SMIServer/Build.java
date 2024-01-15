// Copyright(c) 1997 ObjectSpace, Inc.

import COM.objectspace.voyager.*;

public class Build
  {
  public static void main( String args[] )
    {
    try
      {
	      VSMIServer vss = new VSMIServer("r4006174.itzrh.ska.com:7000/SMIServer" );           
      	vss.liveForever();
		Voyager.shutdown();
      }
      catch( VoyagerException exception )
      {
      System.err.println( exception );
      }
    }
  }