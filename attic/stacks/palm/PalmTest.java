import java.awt.*;
import java.awt.event.*;
import de.kawt.*;

public class PalmTest extends Frame {

	public PalmTest() {
		//new MiceCanvas(10, 15).start();
	
		setTitle("Palm Test");

		
		Button addButton = new Button("Add");
		
		Button okButton = new Button("Exit");
		okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							System.exit(0);
						}
					});
		
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(okButton);
		add("South", buttonPanel);
		
		CheckboxGroup group = new CheckboxGroup();
		Checkbox maleCb = new Checkbox("male", false, group);
		Checkbox femalCb = new Checkbox("female", false, group);		
		
		Panel cbpanel = new Panel();
		cbpanel.setLayout(new FlowLayout());
		cbpanel.add(maleCb);
		cbpanel.add(femalCb);
		
		
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(7,2));
	
		panel.add(new Label("Sex: "));
		panel.add(cbpanel);

		TextField nameTF = new TextField(10);		
		panel.add(new Label("Name: "));
		panel.add(nameTF);
		
		TextField vornameTF = new TextField(10);		
		panel.add(new Label("Vorname: "));
		panel.add(vornameTF);
		
		TextField emailTF = new TextField(10);		
		panel.add(new Label("Email: "));
		panel.add(emailTF);
		
		TextField cityTF = new TextField(10);		
		panel.add(new Label("City: "));
		panel.add(cityTF);
		
		TextField addressTF = new TextField(10);		
		panel.add(new Label("Address: "));
		panel.add(addressTF);


		Choice countryChoice = new Choice();
		countryChoice.add("Switzerland");
		countryChoice.add("Germany");
		countryChoice.add("Austria");
		countryChoice.add("France");
		countryChoice.add("England");
		countryChoice.add("Scotland");
		countryChoice.add("Wales");
		countryChoice.add("Ireland");
		countryChoice.add("Denmark");
		countryChoice.add("Poland");
		countryChoice.add("Sweden");
		countryChoice.add("Finnland");
		countryChoice.add("Norway");
		countryChoice.add("Italy");
		countryChoice.add("Liechtenstein");
		countryChoice.add("Hungary");
		countryChoice.add("Russia");
		countryChoice.add("USA");
		countryChoice.add("Canada");
		countryChoice.add("Mexico");
		countryChoice.add("Brasil");
		countryChoice.add("Argentina");
		countryChoice.add("Spain");
		countryChoice.add("Portugal");
		countryChoice.add("Netherlands");
		countryChoice.add("Luxembourg");
		countryChoice.add("Australia");														
		
		panel.add(new Label("Country: "));
		panel.add(countryChoice);
		

		add("Center", panel);

		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new PalmTest();
	}
   

}