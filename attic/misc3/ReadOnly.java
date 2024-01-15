
import org.apache.tools.ant.BuildException; 
import org.apache.tools.ant.Task;
import java.io.*; 

/** 
 * Sets the file named in property "file" to be read-only 
 * This capability is already available in Ant using the 
 * chmod task, but chmod works on Unix only. */
public class ReadOnly extends Task { 
	private String file; 
	public void execute() throws BuildException { 
		File f = new File(file);
		if (false == f.setReadOnly()) { 
			throw new BuildException("File " + f + 
				" could not be set read-only"); 
		} 
		System.out.println("Set " + f + " to read-only"); 
	} 
	public void setFile(String file) { 
		this.file = file; 
	}
}
