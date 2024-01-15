import org.apache.ecs.html.*;
import org.apache.ecs.*;

public class ECSTest {

	public static void main(String[] args) {
	
		Html html = new Html()
                   .addElement(new Head()
                       .addElement(new Title("Demo")))
                   .addElement(new Body()
                   .addElement(new H1("Demo Header"))
                   .addElement(new H3("Sub Header:"))
                   .addElement(new Font().setSize("+1")
                              .setColor(HtmlColor.WHITE)
                              .setFace("Times")
                              .addElement("The big dog & the little cat chased each other.")));
     System.out.println(html.toString()); 
	}

}