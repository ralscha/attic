package common.util.match;

import java.io.*;

public class WildcardFilter extends MatchFilter
{
  public WildcardFilter(String pattern)
  {
    super(new Wildcard(pattern));
  }

  public WildcardFilter(String pattern, int include)
  {
    super(new Wildcard(pattern), include);
  }
}

