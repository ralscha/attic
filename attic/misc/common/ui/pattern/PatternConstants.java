package common.ui.pattern;

import java.awt.*;
import java.awt.image.*;

public class PatternConstants
{
  public static final PatternPaint
    DEFAULT = new PatternPaint(4, 4, new int[]
      {1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1});
  public static final PatternPaint
    CROSS = new PatternPaint(4, 4, new int[]
      {0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0});
  public static final PatternPaint
    VERT = new PatternPaint(4, 4, new int[]
      {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0});
  public static final PatternPaint
    HORZ = new PatternPaint(4, 4, new int[]
      {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1});
  public static final PatternPaint
    GRID = new PatternPaint(4, 4, new int[]
    {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1});
  public static final PatternPaint
    ROUND = new PatternPaint(4, 4, new int[]
      {0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0});
  public static final PatternPaint
    NW_TRIANGLE = new PatternPaint(4, 4, new int[]
      {1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
  public static final PatternPaint
    NE_TRIANGLE = new PatternPaint(4, 4, new int[]
      {0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0});
  public static final PatternPaint
    SW_TRIANGLE = new PatternPaint(4, 4, new int[]
      {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0});
  public static final PatternPaint
    SE_TRIANGLE = new PatternPaint(4, 4, new int[]
      {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1});
  public static final PatternPaint
    DIAMOND = new PatternPaint(4, 4, new int[]
      {0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0});
  public static final PatternPaint
    DOTS = new PatternPaint(4, 4, new int[]
      {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0});
  public static final PatternPaint
    DOT = new PatternPaint(4, 4, new int[]
      {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0});
  public static final PatternPaint
    SLASH = new PatternPaint(4, 4, new int[]
      {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1});
  public static final PatternPaint
    BACKSLASH = new PatternPaint(4, 4, new int[]
      {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0});
  public static final PatternPaint
    THICK_VERT = new PatternPaint(4, 4, new int[]
      {0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0});
  public static final PatternPaint
    THICK_HORZ = new PatternPaint(4, 4, new int[]
      {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0});
  public static final PatternPaint
    THICK_GRID = new PatternPaint(4, 4, new int[]
      {0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0});
  public static final PatternPaint
    THICK_SLASH = new PatternPaint(4, 4, new int[]
    {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1});
  public static final PatternPaint
    THICK_BACKSLASH = new PatternPaint(4, 4, new int[]
      {0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1});

  public static PatternPaint[] PATTERN_LIST =
  {
    DEFAULT,
    CROSS,
    VERT,
    HORZ,
    GRID,
    ROUND,
    NW_TRIANGLE,
    NE_TRIANGLE,
    SW_TRIANGLE,
    SE_TRIANGLE,
    DIAMOND,
    DOTS,
    DOT,
    SLASH,
    BACKSLASH,
    THICK_VERT,
    THICK_HORZ,
    THICK_GRID,
    THICK_SLASH,
    THICK_BACKSLASH
  };
}

