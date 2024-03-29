/*
 * @(#)JSpinField.java 
 *
 * Copyright 1998 Kai Toedter
 */

package common.ui.spinner;

import java.beans.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * JSpinField is a numeric field with 2 spin buttons to increase or decrease the value.
 *
 * @version 1.0 10/10/98
 * @author  Kai Toedter
 */
public class JSpinField extends JPanel 
   implements CaretListener, AdjustmentListener, ActionListener
{
   /**
    * Default JSpinField constructor.
    */
   public JSpinField()
   {
      super();
      min   = 0;
      max   = 100;
      value = 0;
      darkGreen = new Color( 0, 150, 0 );

      setLayout( new BorderLayout() );
      textField = new JTextField( "0" );
      textField.addCaretListener( this );
      textField.addActionListener( this );
      add( textField, BorderLayout.CENTER );
      scrollBar = new JScrollBar();
      scrollBar.setPreferredSize( new Dimension ( scrollBar.getPreferredSize().width,
						  textField.getPreferredSize().height ) );
      scrollBar.setMinimum( min );
      scrollBar.setMaximum( max );
      scrollBar.setValue( max - value );
      scrollBar.setVisibleAmount( 0 );

      scrollBar.addAdjustmentListener( this );
      add( scrollBar, BorderLayout.EAST );
   }

   protected void setFieldValue( int newValue, boolean updateTextField, boolean updateScrollbar )
   {
      int oldValue = value;

      if( newValue < min )
	 value = min;
      else if( newValue > max )
	 value = max;
      else
	 value = newValue;

      if( updateTextField )
      {
	 textField.setText( Integer.toString( value ) );
	 textField.setForeground( Color.black );
      }

      if( updateScrollbar )
	 scrollBar.setValue( max - value );

      firePropertyChange( "value", oldValue, value );
   }

   /**
    * Sets the value.
    * This is a bound property.
    *
    * @see #getValue
    * @param newValue the new value
    */
   public void setFieldValue( int newValue )
   {
      setFieldValue( newValue, true, true );
   }

   /**
    * Returns the value.
    */
   public int getFieldValue()
   {
      return value;
   }

   /**
    * Sets the minimum value.
    *
    * @see #getMinimum
    * @param newMinimum the new minimum value
    */
   public void setMinimum( int newMinimum )
   {
      min = newMinimum;
      scrollBar.setMinimum( min );
   }

   /**
    * Returns the minimum value.
    */
   public int getMinimum()
   {
      return min;
   }
   
   /**
    * Sets the maximum value.
    *
    * @see #getMaximum
    * @param newMaximum the new maximum value
    */
   public void setMaximum( int newMaximum )
   {
      max = newMaximum;
      scrollBar.setMaximum( max );
   }

   /**
    * Returns the maximum value.
    */
   public int getMaximum()
   {
      return max;
   }
   
   /**
    * Sets the font property.
    * 
    * @param font the new font
    */
   public void setFont( Font font )
   {
      if( textField != null )
	 textField.setFont( font );
   }

   /**
    * Sets the foreground color.
    * 
    * @param fg the new foreground
    */
   public void setForeground( Color fg)
   {
      if( textField != null )
	 textField.setForeground( fg);
   }

   /**
    * After any user input, the value of the textfield is proofed. Depending
    * on being an integer, the value is colored green or red.
    */
   public void caretUpdate( CaretEvent e )
   {
      try
      {
	 int testValue = Integer.valueOf( textField.getText() ).intValue();

	 if( (testValue >= min ) && ( testValue <= max ) )
	 {
	    textField.setForeground( darkGreen );
	 }
	 else
	 {
	    textField.setForeground( Color.red );
	 }
      }
      catch( Exception ex )
      {
	 if( ex instanceof NumberFormatException )
	    textField.setForeground( Color.red );

	 // Ignore all other exceptions, e.g. illegal state exception
      }
      textField.repaint();
   }

   /**
    * The 2 buttons are implemented with a JScrollBar.
    */
   public void adjustmentValueChanged( AdjustmentEvent e )
   {
      setFieldValue( max - e.getValue(), true, false );
   }

   /**
    * After any user input, the value of the textfield is proofed. Depending
    * on being an integer, the value is colored green or red. If the textfield is
    * green, the enter key is accepted and the new value is set.
    */
   public void actionPerformed( ActionEvent e )
   {
      if( textField.getForeground().equals( darkGreen ) )
	 setFieldValue( Integer.valueOf( textField.getText() ).intValue() );
   }

   /**
    * Creates a JFrame with a JSpinField inside and can be used for testing.
    */
   static public void main( String[] s )
   {
      JFrame frame = new JFrame( "JSpinField" );
      frame.getContentPane().add( new JSpinField() );
      frame.pack();
      frame.setVisible( true );
   }

   private JTextField textField;
   private JScrollBar scrollBar;
   private Color      darkGreen;
   private int        min;
   private int        max;
   private int        value;
}
