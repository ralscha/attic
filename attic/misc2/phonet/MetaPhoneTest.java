
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

public class MetaPhoneTest extends Frame implements Runnable
{

    String sources[] = {"", "4946FEMA.LEN", "3897MALE.NAM", "10196PLA.CES"};
    Label label2;
    Choice choice1;
    Label label1;
    TextField inputEdit;
    Label label3;
    TextField edit2;
    TextArea matchTextArea;
    Label label4;
    Label label5;
    Button calculateB;
    int sourceN;
    int lineCt;
    PhoneticList pList;
    Thread myT;


	public MetaPhoneTest() {

        super("MetaPhoneTest");
        setLayout(null);
        setBackground(Color.white);
        resize(301, 282);
        label2 = new Label("Select Word Source");
        add(label2);
        label2.reshape(7, 2, 133, 15);
        choice1 = new Choice();
        add(choice1);
        choice1.reshape(7, 20, 230, 75);
        choice1.addItem("<no list selected>");
        choice1.addItem("4946 Female Names");
        choice1.addItem("3897 Male Names");
        choice1.addItem("10196 Place Names");
        label1 = new Label("Enter Test Word", 2);
        add(label1);
        label1.reshape(14, 87, 119, 15);
        inputEdit = new TextField(17);
        add(inputEdit);
        inputEdit.reshape(140, 80, 147, 22);
        label3 = new Label("Resulting Code", 2);
        add(label3);
        label3.reshape(7, 144, 119, 15);
        edit2 = new TextField(9);
        edit2.setEditable(false);
        add(edit2);
        edit2.reshape(138, 144, 81, 19);
        matchTextArea = new TextArea(6, 17);
        matchTextArea.setEditable(false);
        add(matchTextArea);
        matchTextArea.reshape(140, 165, 147, 98);
        label4 = new Label("MetaPhone", 2);
        add(label4);
        label4.reshape(21, 174, 105, 15);
        label5 = new Label("Matches", 2);
        add(label5);
        label5.reshape(21, 203, 105, 15);
        calculateB = new Button("Lookup");
        add(calculateB);
        calculateB.reshape(140, 106, 147, 26);
        
        addWindowListener(new WindowAdapter() {
        		public void windowClosing(WindowEvent windowevent) {
					System.exit(0);
        		}        
        });
        
        setVisible(true);
    }

    public boolean handleEvent(Event event)
    {
        if(event.id == 1001 && event.target == calculateB)
        {
            clickedCalculateB();
            return true;
        }
        if(event.id == 1001 && event.target == choice1)
        {
            selectedSource();
            return true;
        }
        if(event.target == inputEdit && event.id == 401 && event.key == 10)
        {
            clickedCalculateB();
            return true;
        }
        else
        {
            return super.handleEvent(event);
        }
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRect(0, 0, size().width - 1, size().height - 1);
    }

    public void keyActioninputEdit(Event event)
    {
        System.out.println("keyAction " + inputEdit.getText());
        if(event.key < 32)
            System.out.println("ctrl key");
    }

    public void selectedSource()
    {
        int i = choice1.getSelectedIndex();
        repaint();
        if(i == sourceN)
            return;
        sourceN = i;
        if(i == 0)
        {
            pList = null;
            matchTextArea.setText("No Word File\n");
            return;
        }
        else
        {
            working();
            matchTextArea.setText("reading data...\n");
            myT = new Thread(this);
            myT.start();
            return;
        }
    }

    public void working()
    {
        choice1.setEnabled(false);
        calculateB.setEnabled(false);
    }

    public void ready()
    {
        choice1.setEnabled(true);
        calculateB.setEnabled(true);
        System.out.println("READY");
    }

    public void clickedCalculateB()
    {
        String s = inputEdit.getText();
        edit2.setText("");
        matchTextArea.setText("");
        repaint();
        if(s.length() == 0)
            return;
        edit2.setText(MetaPhone.metaPhone(s));
        if(pList == null)
            return;
        Object aobj[] = pList.match(s);
        if(aobj == null)
        {
            matchTextArea.setText("No Match\n");
            return;
        }
        int i = aobj.length;
        if(i == 0)
        {
            matchTextArea.setText("No Match\n");
            return;
        }
        for(int j = 0; j < i; j++)
            matchTextArea.append((String)aobj[j] + "\n");

    }

    public void run()
    {
        lineCt = 0;
        pList = new PhoneticList();
        try
        {
        	BufferedReader br = new BufferedReader(new FileReader(sources[sourceN]));
        	  for(String s = br.readLine(); s != null; s = br.readLine())
            {
                pList.put(s, s);
                lineCt++;
            }

            matchTextArea.setText("Read " + lineCt + " words\n");
            br.close();
        }
        catch(Exception exception1)
        {
            System.out.println("Run " + exception1);
        }
        finally
        {
            ready();
        }
    }
    
    public static void main(String[] args) {
    	new MetaPhoneTest();
    }

}