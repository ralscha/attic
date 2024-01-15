package common.ui.pattern;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class JPattern extends JPanel
  implements ActionListener, ListSelectionListener
{
  public static final boolean COMBO = true;
  public static final boolean LIST = false;

  protected ArrayList listeners = new ArrayList();
  protected boolean compactDisplay;
  protected JPaintCombo patternCombo;
  protected JPaintCombo foregroundCombo;
  protected JPaintCombo backgroundCombo;
  protected JPaintList patternList;
  protected JPaintList foregroundList;
  protected JPaintList backgroundList;
  protected PreviewPanel preview;

  public JPattern(boolean compactDisplay)
  {
    this.compactDisplay = compactDisplay;
    JPanel choicePanel = new JPanel(new BorderLayout(4, 4));
    preview = new PreviewPanel();
    if (compactDisplay)
    {
      JPanel prompts = new JPanel(new GridLayout(3, 1, 4, 4));
      prompts.add(new JLabel("Pattern:", JLabel.RIGHT));
      prompts.add(new JLabel("Foreground:", JLabel.RIGHT));
      prompts.add(new JLabel("Background:", JLabel.RIGHT));
    
      JPanel menus = new JPanel(new GridLayout(3, 1, 4, 4));
      menus.add(patternCombo = new JPaintCombo(
        PatternConstants.PATTERN_LIST));
      menus.add(foregroundCombo = new JPaintCombo(
        ColorConstants.SHORT_LIST));
      menus.add(backgroundCombo = new JPaintCombo(
        ColorConstants.SHORT_LIST));
    
      patternCombo.addActionListener(this);
      foregroundCombo.addActionListener(this);
      backgroundCombo.addActionListener(this);

      choicePanel.add(BorderLayout.WEST, prompts);
      choicePanel.add(BorderLayout.CENTER, menus);
    
      preview.setPattern((PatternPaint)
        patternCombo.getSelectedPaint());
    }
    else
    {
      JPanel labels = new JPanel(new GridLayout(1, 3, 4, 4));
      labels.add(new JLabel("Pattern:"));
      labels.add(new JLabel("Foreground:"));
      labels.add(new JLabel("Background:"));
      
      patternList = new JPaintList(
        PatternConstants.PATTERN_LIST);
      foregroundList = new JPaintList(
        ColorConstants.SHORT_LIST);
      backgroundList = new JPaintList(
        ColorConstants.SHORT_LIST);

      JPanel lists = new JPanel(new GridLayout(1, 3, 4, 4));
      lists.add(new JScrollPane(patternList,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      lists.add(new JScrollPane(foregroundList,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      lists.add(new JScrollPane(backgroundList,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      
      patternList.addListSelectionListener(this);
      foregroundList.addListSelectionListener(this);
      backgroundList.addListSelectionListener(this);

      choicePanel.add(BorderLayout.NORTH, labels);
      choicePanel.add(BorderLayout.CENTER, lists);
    
      preview.setPattern((PatternPaint)
        patternList.getSelectedPaint());
    }
    
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    setLayout(new BorderLayout(8, 8));
    add(BorderLayout.NORTH, choicePanel);
    add(BorderLayout.CENTER, preview);
  }
  
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == patternCombo)
    {
      preview.setPattern((PatternPaint)
        patternCombo.getSelectedPaint());
    }
    if (source == foregroundCombo)
    {
      preview.setForeground((Color)
        foregroundCombo.getSelectedPaint());
    }
    if (source == backgroundCombo)
    {
      preview.setBackground((Color)
        backgroundCombo.getSelectedPaint());
    }
    fireActionEvent();
    preview.repaint();
  }
  
  public void valueChanged(ListSelectionEvent event)
  {
    if (event.getValueIsAdjusting()) return;
    Object source = event.getSource();
    if (source == patternList)
    {
      preview.setPattern((PatternPaint)
        patternList.getSelectedPaint());
    }
    if (source == foregroundList)
    {
      preview.setForeground((Color)
        foregroundList.getSelectedPaint());
    }
    if (source == backgroundList)
    {
      preview.setBackground((Color)
        backgroundList.getSelectedPaint());
    }
    fireActionEvent();
    preview.repaint();
  }
  
  public PatternPaint getPattern()
  {
    return preview.getPattern();
  }
  
  public void setPattern(PatternPaint pattern)
  {
    preview.setPattern(pattern);
    if (compactDisplay)
    {
      patternCombo.setSelectedItem(
        new PatternPaint(pattern, Color.black, Color.white));
      foregroundCombo.setSelectedItem(pattern.foreground);
      backgroundCombo.setSelectedItem(pattern.background);
    }
    else
    {
      patternList.setSelectedValue(
        new PatternPaint(pattern, Color.black, Color.white), true);
      foregroundList.setSelectedValue(pattern.foreground, true);
      backgroundList.setSelectedValue(pattern.background, true);
    }
    fireActionEvent();
  }
  
  public void addActionListener(ActionListener listener)
  {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener)
  {
    listeners.remove(listener);
  }

  public void fireActionEvent()
  {
    ActionEvent event = new ActionEvent(this,
      ActionEvent.ACTION_PERFORMED, "Pattern");
    ArrayList list = (ArrayList)listeners.clone();
    ActionListener listener;
    for (int i = 0; i < list.size(); i++)
    {
      listener = (ActionListener)list.get(i);
      listener.actionPerformed(event);
    }
  }
}

