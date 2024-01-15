/*
 * Blip.java
 * 
 * Originally written by Joseph Bowbeer and released into the public domain.
 * This may be used for any purposes whatsoever without acknowledgment.
 */

package ch.ess.util;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Simple animation in the style of a radar screen.
 * Used to indicate that background tasks are running. 
 *
 * @author  Joseph Bowbeer
 * @version 1.0
 */
public class Blip extends JComponent implements ActionListener {

    public static final int DELAY = 50;
    public static final int ANGLE = 36;

    private final Timer timer = new Timer(DELAY, this);
    private int startAngle;
    private int endAngle;

    /** Starts the animation timer. */
    public void start() {
        timer.start();
    }

    /** Stops the animation timer. */
    public void stop() {
        timer.stop();
        startAngle = endAngle = 0;
        repaint();
    }

    /** Advances animation to next frame. Called by timer. */
    public void actionPerformed(ActionEvent e) {
        endAngle += ANGLE;
        if (endAngle > 360) {
            endAngle = 360;
            startAngle += ANGLE;
            if (startAngle > 360) {
                startAngle = endAngle = 0;
            }
        }
        repaint();
    }

    /** Paints the current frame. */
    public void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, w, h);
            g.setColor(getForeground());
        }
        Insets ins = getInsets();
        w -= (ins.left + ins.right);
        h -= (ins.top + ins.bottom);
        g.fillArc(ins.left, ins.top, w, h,
            90 - startAngle, startAngle - endAngle);
    }

}
