package oogui;

import java.util.*;
import javax.swing.*;

public class BothButton extends JRadioButton implements Command {
    Kids kds;
    Mediator med;

    public BothButton(String title, Kids sw, Mediator md) {
        super(title);
        kds = sw;
        med = md;
    }
    public void Execute() {
        Enumeration enum = kds.getKids();
        med.loadList (enum);
    }
}
