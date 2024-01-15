package common.util.match;

/**
 * This class provides a way of comparing strings, returning true
 * if they are sufficiently similar, based on the Levenstein
 * Distance. If the difference in length between the two strings
 * is greater than the threshold value, the method returns false.
 *
 * Original C code from Zwakenberg, Hans G., in the article
 * "Inexact Alphanumeric Comparisons", C Users Journal, May '91.
**/

public class Alike implements Match
{
  private static int MAX_LEN = 20;
  private static int MIN_THRESHOLD = 3;

  private static int ADDITION = 1;
  private static int DELETION = 1;
  private static int CHANGE = 2;

  protected String pattern;

  public Alike(String pattern)
  {
    this.pattern = pattern;
  }

  private static int min(int x, int y, int z)
  {
    return (x < y) ? Math.min(x, z) : Math.min(y, z);
  }

  private static boolean compareCharAt(String one, int atOne, String two, int atTwo)
  {
    return Character.toUpperCase(one.charAt(atOne)) ==
      Character.toUpperCase(two.charAt(atTwo));
  }

  public boolean match(String text)
  {
    return match(pattern, text);
  }
  
  protected boolean match(String one, String two)
  {
    int lenOne = one.length();
    int lenTwo = two.length();

    if (lenOne > MAX_LEN) lenOne = MAX_LEN;
    if (lenTwo > MAX_LEN) lenTwo = MAX_LEN;

    // Minimum threshold of three handles short strings more effectively.
    int threshold = Math.max(MIN_THRESHOLD,
      (int)Math.floor(1.0 + (lenOne + 2) / 4.0));

    if(Math.abs(lenOne - lenTwo) > threshold) return false;

    int[][]	distance = new int[MAX_LEN][MAX_LEN];

    // Start the matrix with zero.
    distance[0][0] = 0;

    // Init vertical edge of the matrix to be 0, 1, 2, etc.
    for(int j = 1; j < MAX_LEN; j++)
      distance[0][j] = distance[0][j - 1] + ADDITION;

    // Init horizontal edge of the matrix to be 0, 1, 2, etc.
    for(int j = 1; j < MAX_LEN; j++)
      distance[j][0] = distance[j - 1][0] + DELETION;

    // Calculate the rest of the matrix based on the comparison
    // at each string position. Always take the smallest value.
    for(int i = 1; i <= lenOne; i++)
    {
      for(int j = 1; j <= lenTwo; j++)
      {
        int dist = compareCharAt(one, i - 1, two, j - 1) ? 0 : CHANGE;
        distance[i][j] = min(
          distance[i - 1][j - 1] + dist,
          distance[i][j - 1] + ADDITION,
          distance[i - 1][j] + DELETION);
      }
    }
    if(distance[lenOne][lenTwo] <= threshold) return true;
    return false;
  }
}

