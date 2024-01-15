package common.util.match;

import java.io.*;

public class RegularExpressionFilter extends MatchFilter
{
  public RegularExpressionFilter(String pattern)
  {
    super(new RegularExpression(pattern));
  }

  public RegularExpressionFilter(String pattern, int include)
  {
    super(new RegularExpression(pattern), include);
  }
}

