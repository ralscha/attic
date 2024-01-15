package common.ui.checktree;

import javax.swing.*;

public class BasicTreeNode
{
  protected String text;
  protected Icon icon, open;
  
  public BasicTreeNode(String text, Icon icon)
  {
    this(text, icon, icon);
  }
  
  public BasicTreeNode(String text, Icon icon, Icon open)
  {
    this.text = text;
    this.icon = icon;
    this.open = open;
  }
  
  public static BasicTreeNode createComputer(String text)
  {
    return new BasicTreeNode(text,
      UIManager.getIcon("FileView.computerIcon"));
  }
  
  public static BasicTreeNode createDrive(String text)
  {
    return new BasicTreeNode(text,
      UIManager.getIcon("FileView.hardDriveIcon"));
  }
  
  public static BasicTreeNode createFile(String text)
  {
    return new BasicTreeNode(text,
      UIManager.getIcon("FileView.fileIcon"));
  }
  
  public static BasicTreeNode createDesk(String text)
  {
    return new BasicTreeNode(text,
      UIManager.getIcon("DesktopIcon.icon"));
  }
  
  public static BasicTreeNode createFolder(String text)
  {
    return new BasicTreeNode(text,
      UIManager.getIcon("Tree.closedIcon"),
      UIManager.getIcon("Tree.openIcon"));
  }

  public String getText()
  {
    return text;
  }
  
  public Icon getIcon()
  {
    return icon;
  }
  
  public Icon getOpen()
  {
    return open;
  }
}

