
package oogui;

import java.util.*;
import javax.swing.*;

public class MaleButton extends JRadioButton implements Command {
    MaleKids kds;
    Mediator med;
    public MaleButton(String title, Kids sw, Mediator md) {
        super(title);
        kds = new MaleKids(sw);
        med = md;
    }
    public void Execute() {
        Enumeration enum = kds.getKids ();
        med.loadList(enum);
    }
}
