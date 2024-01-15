package common.util.match;

import java.io.*;

public class MatchFilter implements FilenameFilter
{
  public static final int INCLUDE_FILE = 1;
  public static final int INCLUDE_PATH = 2;
  public static final int INCLUDE_BOTH = 3;

  protected Match match;
  protected boolean includeFile, includePath;

  public MatchFilter(Match match)
  {
    this(match, INCLUDE_FILE);
  }
  
  public MatchFilter(Match match, int include)
  {
    this.match = match;
    includeFile = ((include & INCLUDE_FILE) == INCLUDE_FILE);
    includePath = ((include & INCLUDE_PATH) == INCLUDE_PATH);
  }

  public boolean accept(File dir, String name)
  {
    File file = new File(dir, name);
    if (includeFile && !file.isFile())
      return false;
    if (includePath && !file.isDirectory())
      return false;
    return match.match(file.getName());
  }
}

