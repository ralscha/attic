package oogui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;     
import javax.swing.border.*;
import java.util.*;

public class MedChoice extends JxFrame implements ActionListener {
    private FemaleButton female;
    private MaleButton male;
    private BothButton both;
    JawtList kidList;
    Kids kds;
    Mediator med;

    public MedChoice() {
        super("Kid List");
        JPanel jp = new JPanel();   // create interior panel      
        jp.setLayout(new GridLayout(1,2));
        getContentPane().add(jp);
        JPanel lp = new JPanel();
        jp.add(lp);
        lp.setLayout(new BoxLayout(lp, BoxLayout.Y_AXIS));
        jp.add(lp);

        Border bd = BorderFactory.createBevelBorder (BevelBorder.LOWERED );
        TitledBorder tl = BorderFactory.createTitledBorder (bd, "Select sex");
        lp.setBorder(tl);
        kidList = new JawtList(20);
        kidList.setBorder (BorderFactory.createBevelBorder (BevelBorder.LOWERED));

        loadSwimmers();
        med = new Mediator(kidList);

        female = new FemaleButton("Female", kds, med);
        male = new MaleButton("Male", kds, med);
        both = new BothButton("Both", kds, med);
        ButtonGroup grp = new ButtonGroup();
        grp.add(female);
        grp.add(male);
        grp.add(both);

        female.addActionListener (this);
        male.addActionListener (this);
        both.addActionListener (this);
        
        lp.add(Box.createVerticalStrut (30));
        lp.add(female);
        lp.add(Box.createVerticalStrut (5));
        lp.add(male);
        lp.add(Box.createVerticalStrut (5));
        lp.add(both);

        jp.add (kidList);
        
        setBounds(100, 100, 300, 300);
        setVisible(true);
    }
    private void loadSwimmers() {
        InputFile fl = new InputFile("oogui/Swimmers.txt");
        kds = new Kids();
        String s = fl.readLine();
        while(s != null) {
            kds.add(s);
            s = fl.readLine();
        }
    }
    public void actionPerformed(ActionEvent evt) {
        Command cmd = (Command)evt.getSource ();
        cmd.Execute ();
    }
       static public void main(String argv[]) {
        new MedChoice();
    }
}
