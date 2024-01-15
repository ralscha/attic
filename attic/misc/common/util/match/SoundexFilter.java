package common.util.match;

import java.io.*;

public class SoundexFilter extends MatchFilter
{
  public SoundexFilter(String pattern)
  {
    super(new Soundex(pattern));
  }

  public SoundexFilter(String pattern, int include)
  {
    super(new Soundex(pattern), include);
  }
}

