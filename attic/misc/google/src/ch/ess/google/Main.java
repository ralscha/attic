package ch.ess.google;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

import org.apache.axis.utils.*;

import EDU.oswego.cs.dl.util.concurrent.misc.*;
import ch.ess.google.wsdl.*;
import ch.ess.util.*;

public class Main extends JFrame {
   
  
  private JLabel status;
  private Blip blip;
  SwingWorker worker;

  private JButton searchButton;
  private JButton cancelButton;
  JTextField searchText;
  JTable resultTable;
  Mediator mediator;
  JEditorPane html;
  
  public Main() {

    super("Google Search");
    Locale.setDefault(Locale.GERMAN);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    
        
    
    //Status    
    blip = new Blip ();
    status = new JLabel();
    
    JPanel jPanel = new JPanel();

    jPanel.setLayout(new BorderLayout());


    status.setToolTipText("Status");
    status.setBorder(new CompoundBorder(
                        new CompoundBorder(new EmptyBorder(new Insets(1, 1, 1, 1)),
                        new LineBorder(Color.gray)), new EmptyBorder(new Insets(0, 2, 0, 0))));
    status.setText("Bereit");
    jPanel.add(status, BorderLayout.CENTER);

    
    blip.setToolTipText("Activity Indicator");
    blip.setPreferredSize(new Dimension(23, 23));
    blip.setOpaque(true);
    blip.setBorder(new CompoundBorder (
        new CompoundBorder (
        new EmptyBorder(new Insets(1, 1, 1, 1)),
        new LineBorder(Color.gray)),
        new EmptyBorder(new Insets(1, 1, 1, 1))));
    blip.setForeground(new Color(153, 153, 204));
    
    jPanel.add(blip, BorderLayout.EAST);
    
    getContentPane().add(jPanel, BorderLayout.SOUTH);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    JLabel label = new JLabel("Suchbegriff: ");
    inputPanel.add(label);
    
    searchText = new JTextField(35);
    searchButton = new JButton("Suchen");
    cancelButton = new JButton("Abbrechen");
    cancelButton.setEnabled(false);
    
    SearchResultTableModel tableModel = new SearchResultTableModel();
    resultTable = new JTable(tableModel);
    
    mediator = new Mediator();    
    mediator.setBlip(blip);
    mediator.setStartButton(searchButton);
    mediator.setCancelButton(cancelButton);
    mediator.setResultTable(resultTable);
    mediator.setStatus(status);     
    mediator.setTableModel(tableModel);   
    
    try {
      GoogleSearchServiceLocator service = new GoogleSearchServiceLocator();      
      GoogleSearchPort port = service.getGoogleSearchPort();      
      
      mediator.setSearchService(port);            
   

    } catch (Exception re) {
      System.err.println(re);
      System.exit(1);
    }
    
    
    
        
    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        mediator.setQueryString(searchText.getText());
        mediator.start();
        mediator.setStatus("Suchen...");
        worker = new SearchWorker(mediator);
        worker.start();
             
      }
    });
    
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        if (worker != null) {
          worker.interrupt();
          worker = null;
        }                     
      }
    });
    
  
    resultTable.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 2) {
          try {
            int row = resultTable.getSelectedRow();
            String urlStr = (String)resultTable.getValueAt(row, 1);
            
            URL url = new URL(urlStr);
            mediator.setUrl(url);
            
            mediator.getBlip().start();
            mediator.setStatus("HTML laden...");
            
            SwingWorker wrk = new HTMLWorker(mediator);
            wrk.start();
          } catch (MalformedURLException ex) {
            System.err.println(ex);  
          }          
        }
      }
    });
  
    
    
    inputPanel.add(searchText);
    inputPanel.add(searchButton);
    inputPanel.add(cancelButton);
    inputPanel.setBorder(BorderFactory.createTitledBorder("Eingabe"));
    
    mainPanel.add(inputPanel, BorderLayout.NORTH);
    

    JScrollPane sp = new JScrollPane(resultTable);
    sp.setBorder(BorderFactory.createTitledBorder("Ergebnis"));
    
        
    mainPanel.add(sp, BorderLayout.CENTER);
    
    JPanel htmlPanel = new JPanel();
    htmlPanel.setLayout(new BorderLayout());
    htmlPanel.setBorder(BorderFactory.createTitledBorder("HTML"));
    
    html = new JEditorPane(); 
    
    mediator.setHtmlPane(html);
    html.addHyperlinkListener(createHyperLinkListener());
    html.setEditable(false); 
    
     
    JScrollPane scroller = new JScrollPane(html); 
    htmlPanel.add(scroller, BorderLayout.CENTER);

    
    //Create a split pane with the two scroll panes in it.
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                               mainPanel, htmlPanel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(300);    

    
    getContentPane().add(splitPane, BorderLayout.CENTER);    
    
    
    //Menu
    addMenu();
    
    setSize(700, 600);
    //pack();
    setVisible(true);


  }

  
  private void addMenu() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("Datei");   
    fileMenu.setActionCommand("Datei");
    fileMenu.setMnemonic('a');

    JMenuItem exitMenuItem = new JMenuItem("Beenden");
    exitMenuItem.setActionCommand("Beenden");
    exitMenuItem.setMnemonic('e');    
    //exitMenuItem.setFont(menuFont);
    exitMenuItem.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
                doExit();
              }
            });
    
    fileMenu.add(exitMenuItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
  }
  
  public HyperlinkListener createHyperLinkListener() { 
    return new HyperlinkListener() { 
      public void hyperlinkUpdate(HyperlinkEvent e) { 
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) { 
          if (e instanceof HTMLFrameHyperlinkEvent) { 
            ((HTMLDocument)html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)e); 
          } else { 
            mediator.setUrl(e.getURL());
            mediator.getBlip().start();
            mediator.setStatus("HTML laden...");
            
            SwingWorker wrk = new HTMLWorker(mediator);
            wrk.start();
            /*          
            try { 
              html.setPage(e.getURL()); 
            } catch (IOException ioe) { 
              System.out.println("IOE: " + ioe); 
            } */
            
          } 
        } 
      } 
    }; 
  }   
  
  protected void doExit() {
    
    int n = JOptionPane.showConfirmDialog(this, 
          "Wirklich beenden?",
          "Applikation beenden",
          JOptionPane.YES_NO_OPTION);
    
    if (n == JOptionPane.YES_OPTION) {
      WindowEvent windowClosingEvent;
      windowClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);      
      this.dispatchEvent(windowClosingEvent);
    }
  }

  
  
  
  public static void main(String[] argv) {
    //PlafPanel.setNativeLookAndFeel(false);    
   
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception cnfe) {
      System.err.println(cnfe);
    }
    
    new tcpmon(8080, null, 0);
        
    System.getProperties().put("http.proxyHost", "localhost");
    System.getProperties().put("http.proxyPort", "8080");    
    
    new Main();
  }
  
}