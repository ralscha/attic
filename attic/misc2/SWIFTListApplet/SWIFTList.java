
import java.awt.*;
import java.awt.event.*;
import COM.taligent.widget.*;
import COM.taligent.widget.event.*;
import COM.taligent.util.*;
import COM.objectspace.voyager.*;
import COM.objectspace.voyager.space.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.text.*;


public class SWIFTList extends Frame implements ListboxListener, ItemListener
{
    private MultiColumnListbox listbox;
    String fromDateStr, toDateStr;
    Choice fromDateChoice, toDateChoice;

    SWIFTMessageFrame smf;
    VSWIFTListServer vsls;
    Vector msgHeader;


    public SWIFTList()
    {
        super("SWIFT Input");

        try
        {

            addWindowListener(new Exit());
            setBackground(Color.lightGray);
            setLayout(new BorderLayout());

    	    vsls = (VSWIFTListServer)VObject.forObjectAt("r4006174.itzrh.ska.com:7500/SWIFTListServer");


            fromDateChoice = new Choice();
            toDateChoice   = new Choice();


            fromDateChoice.addItemListener(this);
            toDateChoice.addItemListener(this);

            Button showButton = new Button("Show");
            showButton.addActionListener(new ShowAction());

            fromDateChoice.setFont(new Font("Courier", Font.PLAIN, 12));
            toDateChoice.setFont(new Font("Courier", Font.PLAIN, 12));

    //        long todayMillis = new java.util.Date().getTime();
            long todayMillis = new java.util.Date(97,9,20).getTime();
            long oneDay = 24*60*60*1000;
            java.util.Date hdate;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String fstr = null;

            for (int i = 9; i >= 0; i--)
            {
                hdate = new java.util.Date(todayMillis-(oneDay*i));

                fstr = formatter.format(hdate);

                fromDateChoice.addItem(fstr);
                toDateChoice.addItem(fstr);
            }

            fromDateChoice.select(fromDateChoice.getItemCount()-1);
            toDateChoice.select(toDateChoice.getItemCount()-1);

            fromDateStr = fstr;
        	toDateStr   = fstr;

            Panel p = new Panel();
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            p.add(fromDateChoice);
            p.add(toDateChoice);
            p.add(showButton);
            add("North", p);

            listbox = new MultiColumnListbox();
            listbox.setFont(new Font("Courier", Font.PLAIN, 12));
            listbox.addListboxListener(this);
            //listbox.setVerticalSeparatorVisible(true);

            listbox.addColumn("TOSN");
            listbox.addColumn("Address_Sender");
            listbox.addColumn("MT");
            listbox.addColumn("Duplicate");
            listbox.addColumn("Priority");
            listbox.addColumn("Receive_Date");
            listbox.addColumn("Receive_Time");

            FontMetrics fm = getFontMetrics(listbox.getFont());

            listbox.getColumnInfo(0).setWidth(fm.stringWidth("88888888"));
            listbox.getColumnInfo(1).setWidth(fm.stringWidth("Address_SenderAS"));
            listbox.getColumnInfo(2).setWidth(fm.stringWidth("99999"));
            listbox.getColumnInfo(3).setWidth(fm.stringWidth("DuplicateDD"));
            listbox.getColumnInfo(4).setWidth(fm.stringWidth("PriorityPP"));
            listbox.getColumnInfo(5).setWidth(fm.stringWidth("Receive_DateRD"));
            listbox.getColumnInfo(6).setWidth(fm.stringWidth("Receive_TimeRT"));

            listbox.getColumnInfo(3).setAlignment(Alignment.CENTER);
            listbox.getColumnInfo(4).setAlignment(Alignment.CENTER);
            // Resize CaptionBar height
            // listbox.setCaptionBarHeight(listbox.getCaptionBarHeight() * 2);

            msgHeader = vsls.selectSWIFTHeaders(fromDateStr, toDateStr);
            Enumeration e = msgHeader.elements();
            while(e.hasMoreElements())
            {
                listbox.addRow((Object[]) e.nextElement());
            }

            for (int i = 0; i < listbox.getColumnCount(); i++)
                listbox.getColumnInfo(i).setSorter(new SelectionSorter());

            add("Center", listbox);

            setSize(610,300);
            setVisible(true);
        }
        catch( VoyagerException exception )
        {
            System.err.println( exception );
     	    System.exit(0);
        }

    }

    public void rowSelected(ListboxEvent evt)
    {
        String TOSN;

        try
        {
            if (evt.getClickCount() == 2) /* Doppelclick */
            {
                TOSN = (String)(listbox.getRow(evt.getRow())[0]);
                if (smf != null)
                {
                    smf.setText(vsls.selectSWIFTMsg(TOSN), TOSN);
                    smf.showMsg();
                }
                else
                    smf = new SWIFTMessageFrame(vsls.selectSWIFTMsg(TOSN), TOSN);
            }
        }

        catch( VoyagerException exception )
        {
            System.err.println( exception );
     	    System.exit(0);
        }



    }

    public void rowDeselected(ListboxEvent evt) { }


    public void itemStateChanged(ItemEvent i )
    {
        if (i.getSource() instanceof Choice)
        {
            Choice h = (Choice)i.getSource();
            if (h == fromDateChoice)
                fromDateStr = fromDateChoice.getSelectedItem();
            else if (h == toDateChoice)
                toDateStr = toDateChoice.getSelectedItem();
        }

    }


    public static void main(String args[])
    {
        new SWIFTList();
    }

    class ShowAction implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            try
            {
                listbox.removeAllRows();
                msgHeader = vsls.selectSWIFTHeaders(fromDateStr, toDateStr);
                Enumeration e = msgHeader.elements();
                while(e.hasMoreElements())
                {
                    listbox.addRow((Object[])e.nextElement());
                }
            }
            catch( VoyagerException exception )
            {
                System.err.println( exception );
     	        System.exit(0);
            }

        }

    }

    class Exit extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            System.exit(0);
        }
    }


}
