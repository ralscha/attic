package common.ui.ruler;

public interface RulerModel
  extends RulerConstants
{
  public int getOrientation();
  public boolean getDirection();
  public int getPrecision();
  
  public double getOffset();
  public double getAnchor();
  public double getExtend();
  public double getLengthInUnits();
  public double getIncrement();

  public double getPixelsPerUnit(double width);
  public int getDivisionsPerUnit();
  public int getHighlightsPerUnit();

  public boolean isFixed();
}

