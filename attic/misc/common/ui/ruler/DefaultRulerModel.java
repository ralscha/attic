package common.ui.ruler;

public class DefaultRulerModel
  implements RulerModel
{
  protected double offset, anchor, extend, length, increment;
  protected int orientation, precision;
  protected int pixelsPerUnit;
  protected int divisionsPerUnit;
  protected int highlightsPerUnit;
  protected boolean direction;
  
  public DefaultRulerModel(
    int orientation, boolean direction, int precision,
    double offset, double anchor, double extend,
    double length, double increment,
    int pixelsPerUnit, int divisionsPerUnit, int highlightsPerUnit)
  {
    this.orientation = orientation;
    this.direction = direction;
    this.precision = precision;
    
    this.offset = offset;
    this.anchor = anchor;
    this.extend = extend;
    this.length = length;
    this.increment = increment;
    
    this.pixelsPerUnit = pixelsPerUnit;
    this.divisionsPerUnit = divisionsPerUnit;
    this.highlightsPerUnit = highlightsPerUnit;
  }
  
  public int getOrientation()
  {
    return orientation;
  }
  
  public boolean getDirection()
  {
    return direction;
  }
  
  public int getPrecision()
  {
    return precision;
  }
  
  public double getOffset()
  {
    return offset;
  }
  
  public double getAnchor()
  {
    return anchor;
  }
  
  public double getExtend()
  {
    return extend;
  }
  
  public double getLengthInUnits()
  {
    return length;
  }
  
  public double getIncrement()
  {
    return increment;
  }
  
  public double getPixelsPerUnit(double width)
  {
    if (!isFixed()) return pixelsPerUnit;
    return width / (double)(length + anchor + extend);
  }
  
  public int getDivisionsPerUnit()
  {
    return divisionsPerUnit;
  }
  
  public int getHighlightsPerUnit()
  {
    return highlightsPerUnit;
  }

  public boolean isFixed()
  {
    return pixelsPerUnit == ADJUSTABLE;
  }
}

