// Decompiled by Jad v1.5.5.3. Copyright 1997-98 Pavel Kouznetsov.
// Jad home page:      http://web.unicom.com.cy/~kpd/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StylePanel.java

package com.writeme.guyrice.awt;

import java.awt.*;
import java.awt.event.*;

// Referenced classes of package COM.writeme.guyrice.awt:
//            BaseButton, DialogWindow, GridBagHelper, GroupPanel, 
//            StyleManager, Text, TextButton

public class StylePanel extends Panel
    implements ActionListener
{

    public StylePanel()
    {
        this(StyleManager.global);
    }

    public StylePanel(StyleManager stylemanager)
    {
        super(new GridBagLayout());
        GridBagHelper gridbaghelper = new GridBagHelper(this);
        styleManager = stylemanager;
        gridbaghelper.add(getGeneralGroup(), 0, 0, 1, 2, 1, 1.0D, 1.0D);
        gridbaghelper.add(getButtonGroup(), 1, 0, 1, 1, 1, 1.0D, 1.0D);
        gridbaghelper.add(getFolderGroup(), 1, 1, 1, 1, 1, 1.0D, 1.0D);
        gridbaghelper.add(getSetsGroup(), 0, 2, 1, 1, new Insets(2, 6, 6, 0), 17);
        gridbaghelper.add(getActionGroup(), 1, 2, 1, 1, new Insets(2, 0, 6, 6), 13);
    }

    public StylePanel(Frame frame)
    {
        this(frame, StyleManager.global);
    }

    public StylePanel(Frame frame, StyleManager stylemanager)
    {
        this(stylemanager);
        apply.setText("Okay");
        reset.setText("Cancel");
        dialog = new DialogWindow(frame);
        dialog.add(this);
        dialog.pack();
        Dimension dimension = dialog.getToolkit().getScreenSize();
        Dimension dimension1 = dialog.getSize();
        dialog.setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2);
        dialog.show();
    }

    private Container getGeneralGroup()
    {
        GroupPanel grouppanel = new GroupPanel(new GridBagLayout(), "General Settings");
        GridBagHelper gridbaghelper = new GridBagHelper(grouppanel);
        gridbaghelper.insets = new Insets(2, 0, 0, 4);
        Color color = styleManager.getColor();
        tfRed = new TextField(String.valueOf(color.getRed()), 3);
        tfGreen = new TextField(String.valueOf(color.getGreen()), 3);
        tfBlue = new TextField(String.valueOf(color.getBlue()), 3);
        gridbaghelper.add(new Text("Red:"), 0, 0, 1, 1, 17);
        gridbaghelper.add(new Text("Green:"), 0, 1, 1, 1, 17);
        gridbaghelper.add(new Text("Blue:"), 0, 2, 1, 1, 17);
        gridbaghelper.add(tfRed, 1, 0, 1, 1);
        gridbaghelper.add(tfGreen, 1, 1, 1, 1);
        gridbaghelper.add(tfBlue, 1, 2, 1, 1);
        gridbaghelper.add(new Canvas(), 0, 3, 3, 1, new Insets(6, 6, 6, 6), 2, 1.0D, 0.0D);
        cbOutline = new Checkbox("Outline components", styleManager.getOutline());
        gridbaghelper.add(cbOutline, 0, 4, 3, 1, 17);
        gridbaghelper.add(new Canvas(), 0, 5, 3, 1, 1, 1.0D, 1.0D);
        return grouppanel;
    }

    private Color newColor()
    {
        Color color;
        try
        {
            int i = Integer.parseInt(tfRed.getText());
            int j = Integer.parseInt(tfGreen.getText());
            int k = Integer.parseInt(tfBlue.getText());
            return new Color(i, j, k);
        }
        catch(NumberFormatException ex)
        {
            color = styleManager.getColor();
        }
        tfRed.setText(String.valueOf(color.getRed()));
        tfGreen.setText(String.valueOf(color.getGreen()));
        tfBlue.setText(String.valueOf(color.getBlue()));
        return color;
    }

    private Container getButtonGroup()
    {
        GroupPanel grouppanel = new GroupPanel("Button Style");
        Panel panel = new Panel(new GridLayout(0, 1));
        cbgButtons = new CheckboxGroup();
        cbRectangle = new Checkbox("Rectangular with squared corners", styleManager.getButtonStyle() == 0, cbgButtons);
        panel.add(cbRectangle);
        cbRoundRect = new Checkbox("Rectangular with rounded corners", styleManager.getButtonStyle() == 1, cbgButtons);
        panel.add(cbRoundRect);
        grouppanel.add(panel);
        return grouppanel;
    }

    private int newButtonStyle()
    {
        Checkbox checkbox = cbgButtons.getSelectedCheckbox();
        if(checkbox == cbRectangle)
            return 0;
        return checkbox != cbRoundRect ? 0 : 1;
    }

    private Container getFolderGroup()
    {
        GroupPanel grouppanel = new GroupPanel(new GridBagLayout(), "Folder Settings");
        GridBagHelper gridbaghelper = new GridBagHelper(grouppanel);
        gridbaghelper.insets = new Insets(2, 0, 0, 4);
        gridbaghelper.add(new Text("Tab gaps:"), 0, 0, 1, 1);
        tfTabGap = new TextField(String.valueOf(styleManager.getFolderTabGap()), 3);
        gridbaghelper.add(tfTabGap, 1, 0, 1, 1);
        gridbaghelper.add(new Canvas(), 0, 1, 3, 1, 1, 1.0D, 1.0D);
        return grouppanel;
    }

    private int newFolderTabGap()
    {
        int i;
        try
        {
            return Integer.parseInt(tfTabGap.getText());
        }
        catch(NumberFormatException ex)
        {
            i = styleManager.getFolderTabGap();
        }
        tfTabGap.setText(String.valueOf(i));
        return i;
    }

    private Container getSetsGroup()
    {
        Panel panel = new Panel(new GridLayout(1, 0, 8, 0));
        defaults = new TextButton("Defaults");
        defaults.addActionListener(this);
        panel.add(defaults);
        preferred = new TextButton("Preferred");
        preferred.addActionListener(this);
        panel.add(preferred);
        return panel;
    }

    private Container getActionGroup()
    {
        Panel panel = new Panel(new GridLayout(1, 0, 8, 0));
        apply = new TextButton("Apply");
        apply.addActionListener(this);
        panel.add(apply);
        reset = new TextButton("Reset");
        reset.addActionListener(this);
        panel.add(reset);
        return panel;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj == defaults)
        {
            actionSet(getBackground(), false, 0, 0);
            return;
        }
        if(obj == preferred)
        {
            actionSet(getBackground(), true, 1, -1);
            return;
        }
        if(obj == apply)
        {
            actionApply();
            return;
        }
        if(obj == reset)
            actionReset();
    }

    private void actionSet(Color color, boolean flag, int i, int j)
    {
        tfRed.setText(String.valueOf(color.getRed()));
        tfGreen.setText(String.valueOf(color.getGreen()));
        tfBlue.setText(String.valueOf(color.getBlue()));
        cbOutline.setState(flag);
        if(i == 0)
            cbgButtons.setSelectedCheckbox(cbRectangle);
        else
        if(i == 1)
            cbgButtons.setSelectedCheckbox(cbRoundRect);
        tfTabGap.setText(String.valueOf(j));
    }

    private void actionApply()
    {
        styleManager.beginUpdate();
        if(!styleManager.getColor().equals(newColor()))
            styleManager.setColor(newColor());
        if(cbOutline.getState() != styleManager.getOutline())
            styleManager.setOutline(cbOutline.getState());
        if(newButtonStyle() != styleManager.getButtonStyle())
            styleManager.setButtonStyle(newButtonStyle());
        if(newFolderTabGap() != styleManager.getFolderTabGap())
            styleManager.setFolderTabGap(newFolderTabGap());
        styleManager.endUpdate();
        if(dialog != null)
            dialog.dispose();
    }

    private void actionReset()
    {
        actionSet(styleManager.getColor(), styleManager.getOutline(), styleManager.getButtonStyle(), styleManager.getFolderTabGap());
        if(dialog != null)
            dialog.dispose();
    }

    private StyleManager styleManager;
    private DialogWindow dialog;
    private TextField tfRed;
    private TextField tfGreen;
    private TextField tfBlue;
    private Checkbox cbOutline;
    private CheckboxGroup cbgButtons;
    private Checkbox cbRectangle;
    private Checkbox cbRoundRect;
    TextField tfTabGap;
    private TextButton defaults;
    private TextButton preferred;
    private TextButton apply;
    private TextButton reset;
}
