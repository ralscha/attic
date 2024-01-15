package oogui;

 import java.util.*;
import javax.swing.*;

public class FemaleButton extends JRadioButton implements Command {
    FemaleKids kds;
    Mediator med;

    public FemaleButton(String title, Kids sw, Mediator md) {
        super(title);
        kds = new FemaleKids(sw);
        med = md;
    }
    public void Execute() {
        Enumeration enum = kds.getKids ();
        med.loadList (enum);
    }
}
