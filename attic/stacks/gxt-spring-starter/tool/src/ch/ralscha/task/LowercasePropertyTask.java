package ch.ralscha.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class LowercasePropertyTask extends Task {

  private String name;
  private String value;

  @Override
  public void execute() throws BuildException {
    if (!"".equals(value)) {
      getProject().setProperty(name, value.toLowerCase());
    }
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setName(String propertyName) {
    this.name = propertyName;
  }
}
