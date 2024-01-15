package ch.sr.lottowin.db;

public class DrawUtil {


  public static int getWin(Draw draw, long[] tip) {
    int i, win = 0;

    for (i = 0; i < 6; i++) {
      if ((tip[i] == draw.getZ1().longValue()) || 
          (tip[i] == draw.getZ2().longValue()) || 
          (tip[i] == draw.getZ3().longValue()) || 
          (tip[i] == draw.getZ4().longValue()) ||
          (tip[i] == draw.getZ5().longValue()) || 
          (tip[i] == draw.getZ6().longValue()))
        win++;
    }

    if (win == 5) {
      for (i = 0; i < 6; i++)
        if (tip[i] == draw.getZz().longValue())
          return(51);
    }

    return win;
  }

  public static int getJokerWin(Draw draw, String j) {
    int win = 0;

    for (int i = 5; i >= 0; i--) {
      if (j.substring(i, i + 1).equals(draw.getJoker().substring(i, i + 1))) {
        win++;
      } else {
        break;
      }
    }

    return win;
  }

}
