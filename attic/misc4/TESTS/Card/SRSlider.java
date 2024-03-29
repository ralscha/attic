

import java.awt.*;


public class SRSlider extends Canvas
{

    private static final int THUMB_SIZE = 14;
    private static final int BUFFER = 2;

    private static final int TEXT_HEIGHT = 15;
    private static final int TEXT_BUFFER = 3;

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 15;

    private static final int MIN_WIDTH = 2 * ( THUMB_SIZE + BUFFER + 1 );
    private static final int MIN_HEIGHT = 2 * ( BUFFER + 1 );

    private static final int DEFAULT_MIN = 1;
    private static final int DEFAULT_MAX = 100;

    int value, valueMin, valueMax;
    int pixel, pixelMin, pixelMax;
    Color barColor, thumbColor, lineColor, textColor;

    private Image offImg = null;
    private Graphics offG = null;


    /// Constructor, all default values.
    public SRSlider()
	{
    	setDefaults();
	}

    /// Constructor, some specified values.
    public SRSlider(int valueMin, int valueMax)
	{
	    setDefaults();
    	setMinimum( valueMin );
	    setMaximum( valueMax );
    	setValue( valueMin );
	}

    /// Constructor, some specified values.
    public SRSlider(int valueMin, int valueMax, int width)
	{
	    setDefaults();
    	setMinimum( valueMin );
	    setMaximum( valueMax );
    	setValue( valueMin );
	    setWidth( width );
	}

    private void setDefaults()
	{
    	valueMin = DEFAULT_MIN;
	    valueMax = DEFAULT_MAX;
    	thumbColor = getBackground();
	    if ( thumbColor == null )
	        thumbColor = Color.lightGray;
    	barColor = thumbColor.darker();
	    textColor = getForeground();
    	if ( textColor == null )
	        textColor = Color.black;
    	lineColor = textColor;
	    resize( DEFAULT_WIDTH, DEFAULT_HEIGHT + TEXT_HEIGHT );
    	setValue( valueMin );
	}


    /// This method is called when the "thumb" of the slider is moved by
    // the user.  May be overridden to give the slider some behavior.
    public void motion()
	{
	}

    /// This method is called when the "thumb" of the slider is released
    // after being moved.  May be overridden to give the slider some behavior.
    public void release()
	{
	}


    /// Sets the maximum value for the slider.
    // @param num The new maximum.
    public void setMaximum( int num )
	{
	valueMax = num;
	    if ( valueMin > valueMax )
	        valueMin = valueMax;
    	setValue( value );
	}

    /// Sets the minimum value for the slider.
    // @param num The new minimum.
    public void setMinimum( int num )
	{
    	valueMin = num;
	    if ( valueMax < valueMin )
	        valueMax = valueMin;
    	setValue( value );
	}

    /// Sets the current value for the slider. The thumb will move to
    // reflect the new setting.
    // @param num The new setting for the slider.
    public void setValue( int num )
	{
	    value = num;

    	if ( value < valueMin )
	        value = valueMin;
    	else if ( value > valueMax )
	        value = valueMax;

    	if ( value != valueMin )
	        pixel = (int) ( Math.round(
		    Math.abs(
		        (double) ( value - valueMin ) /
    		    (double) ( valueMax - valueMin ) ) *
	    	(double) ( pixelMax - pixelMin ) ) + pixelMin );
    	else
	        pixel = pixelMin;

    	repaint();
	}

    /// Sets the height of the slider. This is the height of the entire
    // slider canvas, including space reserved for displaying the
    // current value.
    // @param num The new height.
    public void setHeight( int num )
	{
	    if ( num < MIN_HEIGHT + TEXT_HEIGHT )
	        num = MIN_HEIGHT + TEXT_HEIGHT;
    	resize( size().width, num );
	    repaint();
	}

    /// Sets the width of the slider. This is the width of the actual
    // slider box.
    // @param num The new width.
    public void setWidth( int num )
	{
    	if ( num < MIN_WIDTH )
	        num = MIN_WIDTH;
    	resize( num, size().height );
	    repaint();
	}

    /// Sets the color for the slider's bar. The "bar" is the rectangle
    // that the thumb slides around in.
    // @param color The new bar color.
    public void setBarColor( Color color )
	{
    	barColor = color;
	    repaint();
	}

    /// Sets the color for the slider's thumb. The "thumb" is the box that
    // the user can slide back and forth.
    // @param color The new thumb color.
    public void setThumbColor( Color color )
	{
    	thumbColor = color;
	    repaint();
	}

    /// Sets the line color for the slider - the little vertical line
    // on the thumb.
    // @param color The new line color.
    public void setLineColor( Color color )
	{
    	lineColor = color;
	    repaint();
	}

    /// Sets the color for the slider`s text.
    // @param color The new text color.
    public void setTextColor( Color color )
	{
    	textColor = color;
	    repaint();
	}


    /// Returns the current value for the slider.
    // @return The current value for the slider.
    public int getValue()
	{
    	return value;
	}

    public void update(Graphics g)
    {
        if (offImg == null)
        {
            offImg = createImage(size().width, size().height);
            offG   = offImg.getGraphics();
        }
        paint(g);
    }

    public void paint(Graphics g)
	{
    	int width = size().width;
	    int height = size().height;

        if (offG != null)
        {
            offG.setColor(getBackground());
            offG.fillRect(0, 0, size().width, size().height);

        	offG.setColor(barColor);
	        offG.fill3DRect(0, TEXT_HEIGHT, width, height - TEXT_HEIGHT, false );

        	offG.setColor(thumbColor);
	        offG.fill3DRect(pixel - THUMB_SIZE, TEXT_HEIGHT + BUFFER,
                	        THUMB_SIZE * 2 + 1, height - 2 * BUFFER - TEXT_HEIGHT, true);

    	    offG.setColor(lineColor);
        	offG.drawLine(pixel, TEXT_HEIGHT + BUFFER + 1, pixel, height - 2 * BUFFER);

        	offG.setColor(textColor);
	        String str = String.valueOf(value);
    	    int strWidth = getFontMetrics(offG.getFont()).stringWidth(str);
    	    offG.drawString( str, pixel - strWidth / 2, TEXT_HEIGHT - TEXT_BUFFER);

	        g.drawImage(offImg, 0, 0, this);
        }
    }

    public void reshape( int x, int y, int width, int height )
	{
	    super.reshape( x, y, width, height );
	    offImg = createImage(width, height);
	    if (offImg != null)
            offG   = offImg.getGraphics();
    	pixelMin = THUMB_SIZE + BUFFER;
	    pixelMax = width - THUMB_SIZE - BUFFER - 1;
    	if ( value != valueMin )
	        pixel = (int) ( Math.round(
		    Math.abs(
		        (double) ( value - valueMin ) /
    		    (double) ( valueMax - valueMin ) ) *
	    	(double) ( pixelMax - pixelMin ) ) + pixelMin );
    	else
	        pixel = pixelMin;
	}

    public boolean mouseDown( Event e, int x, int y )
	{
    	if ( x >= pixel - THUMB_SIZE && x <= pixel + THUMB_SIZE )
	        {
    	        if ( x < pixel )
	        	setValue( value - 1 );
        	    else if ( x > pixel )
        		setValue( value + 1 );
	        }
    	else
	        mouseAction( x );
    	motion();
	    return true;
	}

    public boolean mouseDrag( Event e, int x, int y )
	{
    	mouseAction(x);
	    motion();
    	return true;
	}

    public boolean mouseUp( Event e, int x, int y )
	{
    	release();
	    return true;
	}

    private void mouseAction( int x )
	{
    	double percent;
	    pixel = Math.min( Math.max( x, pixelMin ), pixelMax );

    	if ( pixel != pixelMin )
	        percent = ( (double) pixel - pixelMin ) / ( pixelMax - pixelMin );
    	else
	        percent = 0;

    	value =
	        (int) ( Math.round( percent * (double) ( valueMax - valueMin ) ) ) +
	        valueMin;

    	repaint();
	}

}
