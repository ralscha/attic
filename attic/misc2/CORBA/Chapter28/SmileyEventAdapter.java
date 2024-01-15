// SmileyEventAdapter.java - Event adapter

import java.awt.*;
import java.awt.event.*;

public class SmileyEventAdapter implements java.awt.event.ActionListener {
    private SmileyBean target;

    SmileyEventAdapter(SmileyBean t) {
        target = t;
    }

    public void actionPerformed(ActionEvent arg0) { 
        target.toggleSmile();
    }
}

