

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.server.*;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.net.MalformedURLException;


public class BidderFrame extends JFrame {
   Bid fCurrItem;
   JLabel fDesc = new JLabel("", JLabel.LEFT);
   JLabel fId = new JLabel("", JLabel.LEFT); 
   JLabel fPrice = new JLabel("", JLabel.LEFT);
   JLabel fDate = new JLabel("", JLabel.LEFT);
   JButton fMakeBid = new JButton("Bid on Item...");
   
   NumberFormat fCurrFormatter = NumberFormat.getCurrencyInstance();
   DateFormat fDateFormatter = DateFormat.getDateTimeInstance();
   Bid fProducts[];
   Auctioneer fAuctioneer;
   BidderImpl fBidder;
   
   public BidderFrame(String host) 
   throws RemoteException, MalformedURLException, NotBoundException
   {
      super("SouthBay Time Sink");
      
      fBidder = new BidderImpl();
      fBidder.connect(host);
      buildUI();
      
      setLocation(100, 100);
      pack();
      show();
      
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            if (fAuctioneer!=null) {
               try {
                  fAuctioneer.removeBidder(fBidder);
               } catch(RemoteException ex) {}
            }
            
            System.exit(0);
         }
      });
   }
   
   void makeBid() {
      String bid = null;
      String curr = null;
      
      try {
         curr = fCurrFormatter.format(fCurrItem.getCurrentBid());
         bid = JOptionPane.showInputDialog(this,
                           "Enter bid for " + fCurrItem.getDescription(),
                           "Current bid is " + curr,
                           JOptionPane.QUESTION_MESSAGE);
         if (bid == null) return;
         
         fCurrItem.updateBid(new Double(bid).doubleValue());
         fAuctioneer.updateBid(fCurrItem);
      } catch(BidException ex) {
         JOptionPane.showMessageDialog(this,
                          ex.getMessage(),
                          "Bid Not Accepted",
                          JOptionPane.ERROR_MESSAGE);
      } catch(RemoteException ex) {
         JOptionPane.showMessageDialog(this,
                          ex.toString(),
                          "RMI Communication Failure",
                          JOptionPane.ERROR_MESSAGE);
      } catch(NumberFormatException ex) {
         JOptionPane.showMessageDialog(this,
                          bid + " is not a valid number",
                          "Invalid Bid Amount",
                          JOptionPane.ERROR_MESSAGE);
      } catch(IllegalArgumentException ex) {
         JOptionPane.showMessageDialog(this,
                          "Bid must be greater than " + curr,
                          "Invalid Bid Amount",
                          JOptionPane.ERROR_MESSAGE);
      }
   }
   
   void buildUI() {
      //create list of products and set first element as current
      final JList list = new JList(fProducts);
      list.setCellRenderer(new BidCellRenderer());
      JScrollPane sp = new JScrollPane(list);
      sp.setBorder(new TitledBorder("Products"));
      getContentPane().add(sp, BorderLayout.WEST);
      list.setSelectedIndex(0);
      updateUI(fProducts[0]);
      
      //when selection changes set new current item
      list.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            int idx = list.getSelectedIndex();
            updateUI(fProducts[idx]);
         }
      });
      
      //create panel to display characteristics of current item
      JPanel info = new JPanel(new DynaGridLayout(4, 2, 5, 5));
      TitledBorder inside = new TitledBorder("Current Product");
      EmptyBorder outside = new EmptyBorder(0, 5, 5, 5);
      CompoundBorder cpBorder = new CompoundBorder(outside, inside);
      info.setBorder(cpBorder);
      info.add(new JLabel("ID: ", JLabel.RIGHT));
      info.add(fId);
      fId.setForeground(Color.black);
      info.add(new JLabel("Description: ", JLabel.RIGHT));
      info.add(fDesc);
      fDesc.setForeground(Color.black);
      info.add(new JLabel("Current bid: ", JLabel.RIGHT));
      info.add(fPrice);
      fPrice.setForeground(Color.black);
      info.add(new JLabel("Date of bid: ", JLabel.RIGHT));
      info.add(fDate);
      fDate.setForeground(Color.black);
      
      //create panel to allow user to enter a bid
      JPanel bidPanel = new JPanel();
      bidPanel.add(fMakeBid);
      fMakeBid.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            makeBid();
         }
      });
      
      //add panels to content pane
      JPanel details = new JPanel(new BorderLayout(5, 5));
      details.setBorder(new EmptyBorder(0, 5, 5, 5));
      details.add(info, BorderLayout.NORTH);
      details.add(bidPanel, BorderLayout.SOUTH);
      getContentPane().add(details);
   }
   
   void updateUI(Bid bid) {
      fCurrItem = bid;
      fDesc.setText(bid.getDescription());
      fId.setText(bid.getId());
      fPrice.setText(fCurrFormatter.format(bid.getCurrentBid()));
      fDate.setText(fDateFormatter.format(bid.getTimeOfBid()));
   }
   
   class BidderImpl extends UnicastRemoteObject implements Bidder {
      BidderImpl() throws RemoteException {}
      
      void connect(String host) 
      throws RemoteException, MalformedURLException, NotBoundException
      {
         //lookup server
         String url = "rmi://" + host + "/SouthBay";
         fAuctioneer = (Auctioneer)Naming.lookup(url);
            
         //get the current bids and register listener
         //if reverse order could get notified before actually receive bids from server
         fProducts = fAuctioneer.getCurrentBids();
         fAuctioneer.addBidder(this);
      }
   
      
      public void updateBid(final Bid b) {
         //locate bid and replace in array
         for (int i=fProducts.length-1; i>=0; i--) {
            if (fProducts[i].equals(b)) {
               fProducts[i] = b;
            }
         }
         
         if (b.equals(fCurrItem)) {
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  updateUI(b);
               }
            });
         }
      }
        
      public void serverShuttingDown() {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               int rc = JOptionPane.showConfirmDialog(BidderFrame.this, 
                                 "Do you wish to close client",
                                 "Server is shutting down",
                                 JOptionPane.YES_NO_OPTION);
               
               if (rc == JOptionPane.YES_OPTION) {
                  System.exit(0);
               }
            }
         });                                       
      }
   }
   
   public static void main(String args[]) throws Exception {
      String host = "localhost";
      if (args.length > 0) {
         host = args[0];
      }
      
      System.out.println("RMI Host: " + host);
      new BidderFrame(host);
   }   
}

