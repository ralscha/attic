package common.ui.link;
public class Page
{
  protected String name, file;
  
  public Page(String name, String file)
  {
    this.name = name;
    this.file = file;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getFile()
  {
    return file;
  }
}

