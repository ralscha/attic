
/**
 * Example01 illustrates some basics of Java 2D.
 * This version is compliant with Java 1.2 Beta 3, Jun 1998.
 * Please refer to: <BR>
 * http://www.javaworld.com/javaworld/jw-07-1998/jw-07-media.html
 * <P>
 * @author Bill Day <bill.day@javaworld.com>
 * @version 1.0
 * @see java.awt.Graphics2D
**/

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Java2DExample extends Frame {
	/**
	       * Instantiates an Example01 object.
	       **/
	public static void main(String args[]) {
		new Java2DExample();
	}

	/**
	       * Our Example01 constructor sets the frame's size, adds the
	       * visual components, and then makes them visible to the user.
	       * It uses an adapter class to deal with the user closing
	       * the frame.
	       **/
	public Java2DExample() {
		//Title our frame.
		super("Java 2D Example01");

		//Set the size for the frame.
		setSize(400, 300);

		//We need to turn on the visibility of our frame
		//by setting the Visible parameter to true.
		setVisible(true);

		//Now, we want to be sure we properly dispose of resources
		//this frame is using when the window is closed.  We use
		//an anonymous inner class adapter for this.
		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent e) {
                  				dispose();
                  				System.exit(0);
                  			}
                  		}
                 		);
	}

	/**
	       * The paint method provides the real magic.  Here we
	       * cast the Graphics object to Graphics2D to illustrate
	       * that we may use the same old graphics capabilities with
	       * Graphics2D that we are used to using with Graphics.
	       **/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		//This time, we want to use anti-aliasing if possible
		//to avoid the jagged edges that were so prominent in
		//our last example. With ask the Java 2D rendering
		//engine (Graphics2D) to do this using a "rendering hint".
		g2d.setRenderingHints( new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                       		RenderingHints.VALUE_ANTIALIAS_ON));


		Image myImage = Toolkit.getDefaultToolkit().getImage("day.jpg");

		GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		path.moveTo(0.0f, 0.0f);
		path.lineTo(0.0f, 125.0f);
		path.quadTo(100.0f, 100.0f, 225.0f, 125.0f);
		path.curveTo(260.0f, 100.0f, 130.0f, 50.0f, 225.0f, 0.0f);
		path.closePath();

		AffineTransform at = new AffineTransform();
		double rotation = -Math.PI / 8.0;
		at.rotate(rotation);
		at.translate(0.0f, 150.0f);
		g2d.transform(at);
		g2d.setColor(Color.green);
		g2d.fill(path);



		//Now, let's place a sheared and scaled copy of our image
		//overlapping somewhat with the green shape. We first undue
		//the previous rotation of our User Space so that the image is
		//not rotated relative to the Device Space. Then, we do a
		//User Space translation to set the image off from the shape
		//object. Lastly, we shear and scale the Image Space.
		at.setToRotation(-rotation);
		at.translate(50.0f, 0.0f);
		g2d.transform(at);
		AffineTransform atImageSpace = new AffineTransform();
		atImageSpace.shear(0.2f, 0.1f);
		atImageSpace.scale(1.4f, 1.4f);

		//We draw our image with its alpha channel set so that it is
		//50% transparent (or 50% opaque, depending on how you look at it).
		AlphaComposite myAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2d.setComposite(myAlpha);
		g2d.drawImage(myImage, atImageSpace, this);


	}
}
