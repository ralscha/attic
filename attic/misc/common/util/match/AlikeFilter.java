package common.util.match;

import java.io.*;

public class AlikeFilter extends MatchFilter
{
  public AlikeFilter(String pattern)
  {
    super(new Alike(pattern));
  }

  public AlikeFilter(String pattern, int include)
  {
    super(new Alike(pattern), include);
  }
}

