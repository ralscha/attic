

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;
import common.ui.spinner.*;
import java.io.*;

public class JFrameTest extends MinSizeJFrame {

//	private JTriStateCheckBox check1, check2, check3;

	
	public JFrameTest() {
		
		super("Frame Test", 200, 200);
		
		new PlafPanel(this);
		PlafPanel.setNativeLookAndFeel(true);
		
		
    Container pane = getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.WEST;

    pane.add(new JButton("Button 1"), gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(new JButton("Button 2"), gbc);

    gbc.gridwidth = 2;
    
    pane.add(new JButton("Button 3"), gbc);

    gbc.gridwidth = 3;
    gbc.gridheight = 3;
    pane.add(new JButton("Button 4"), gbc);
    
    gbc.gridheight = 1;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(new JButton("Button 5"), gbc);
    
    gbc.gridheight = 1;
    gbc.gridwidth = 1;
    pane.add(new JButton("Button 6"), gbc);

    gbc.gridwidth = 2;
    gbc.gridheight = 2;
    pane.add(new JButton("Button 7"), gbc);
    
    gbc.gridheight = 3;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(new JButton("Button 8"), gbc);
    
    gbc.gridheight = 1;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(new JButton("Button 9"), gbc);
    
    gbc.gridheight = 1;
    gbc.gridwidth = 1;
    pane.add(new JButton("Button 10"), gbc);
    pane.add(new JButton("Button 11"), gbc);
    pane.add(new JButton("Button 12"), gbc);
    pane.add(new JButton("Button 13"), gbc);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(new JButton("Button 14"), gbc);

    gbc.gridwidth = 3;
    pane.add(new JButton("Button 15"), gbc);

    
    gbc.gridheight = 1;
    gbc.gridwidth=GridBagConstraints.RELATIVE;
    pane.add(new JButton("Button 16"), gbc);
    
    gbc.gridwidth = 1;
    pane.add(new JButton("Button 17"), gbc);
    

		
/*
		JSpinField spinner = new JSpinField();
		spinner.setMinimum(1);
		spinner.setMaximum(20000);
		spinner.setFieldValue(9888);
		getContentPane().add(spinner, BorderLayout.SOUTH);
		
		ImageIcon ii = new ImageIcon("figure02.png");
		JLabel l = new JLabel(ii);
		getContentPane().add(l, BorderLayout.CENTER);
*/				
		pack();
		setVisible(true);

		
/*
		
		try {
			DataInputStream bis = new DataInputStream(new ProgressInputStream(new FileInputStream(file), progress));
			String line = null;
			while((line = bis.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException not) {
			System.err.println(not);
		}
	*/	
		/*
		
		final CalendarBean.JCalendar calendarBean = new CalendarBean.JCalendar();
		getContentPane().add(calendarBean, BorderLayout.CENTER);		


		JButton show;
		getContentPane().add(show = new JButton("Show Date"), BorderLayout.SOUTH);
		
		show.addActionListener(new ActionListener() {
		
					public void actionPerformed(ActionEvent ae) {
						System.out.println(calendarBean.getCalendar().getTime());
					}
		});
		*/
		/*
		getContentPane().add(new PaintComponent());

		getContentPane().setLayout(new FlowLayout());
		JTextArea ta = new JTextArea("Dies ist ein Test",3,10);/* {
					
				public void paste() {             
               if(!isEditable() || !isEnabled()) {
               	return;
               }
               super.paste();
               }						};
		ta.setEditable(false);
		getContentPane().add(ta);
		*/
		/*
		check1 = new JTriStateCheckBox("not selected");
		check2 = new JTriStateCheckBox("partially selected", JTriStateCheckBox.PARTIALLY_SELECTED);
		check3 = new JTriStateCheckBox("not enabled");
		check3.setEnabled(false);

		
		final JIconCheckBox jicb = new JIconCheckBox(UIManager.getIcon("Tree.openIcon"), "TEST");
		final JIconCheckBox jicb2 = new JIconCheckBox(UIManager.getIcon("Tree.closedIcon"), "TEST2");
		
	
		JButton action = new JButton("Action");
		action.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								
								if (check2.isFullySelected()) 								
									check2.setSelected(JTriStateCheckBox.PARTIALLY_SELECTED);	
								else if (check2.isPartiallySelected())
									check2.setSelected(JTriStateCheckBox.DESELECTED);
								else 
									check2.setSelected(JTriStateCheckBox.FULLY_SELECTED);
									
								jicb.setText("TEST TEST");
								
							}
						});
		
		Box box = Box.createVerticalBox();
		box.add(check1);
		box.add(check2);
		box.add(check3);
				
		

		box.add(jicb);
		box.add(jicb2);
		
		
		JButton test = new JButton("<html><b><font size=+2>Damn the torpedoes!<br><i>Full speed ahead!");
		JLabel l = new JLabel("<html> When filling out the form, please observe these rules: "
								+ "<ul>"
								+ "<li>Write in the <em>third<em> person"
								+ "<li>Use complete sentences"
								+ "<li>Check your spelling and grammar before submitting"
								+ "<li>Do <strong>not<strong> abbreviate"
								+ "<li>Leave fields that do not apply to you blank. "
								+ "Do not write \"N/A\" or \"not available\"."
								+ "</ul>"
								+ " Remember, the more editing that's required the longer "
								+ " it will be before your submission is posted.");

		box.add(action);
		//box.add(l);
		//box.add(test);
		getContentPane().add(box, BorderLayout.CENTER);
		*/
		
		/*
		pack();
		setVisible(true);
		*/
	}

	public static void main(String[] args) {
		//UIManager.installLookAndFeel("3D Look & Feel", "swing.addon.plaf.threeD.ThreeDLookAndFeel");
		new JFrameTest();
	}


	/* eine Datei einlesen */
	private void go(ActionEvent evt) {
		Thread t = new Thread(new Runnable() {
			public void run() {
	
		/* Datei mit FileDialog auswaehlen */
		FileDialog fd = new FileDialog (JFrameTest.this, "Open Input File ...", FileDialog.LOAD);
  	   fd.show();
  	   if(fd.getFile() == null)  return;
  	   
		String fn = new String(fd.getDirectory()+File.separator+fd.getFile());
      /* Datei soll in einen StringBuffer eingelesen werden */
   	StringBuffer content = new StringBuffer();
  	   try {
	    	InputStream in = new BufferedInputStream(new ProgressMonitorInputStream(JFrameTest.this,fd.getFile(),new FileInputStream (fn)));
	      while(in.available() > 0) {
	         content.append(in.read());
	      }
	      in.close();
	   } catch (IOException e) {}
	}
		});
		t.start();
}



	class PaintComponent extends Component {

		public void paint(Graphics g) {
			int x = 40;
			int y = 40;
			g.setColor(new Color(50, 50, 50));
			g.drawString("Shadow", shiftEast(x, 2), shiftSouth(y, 2));
			g.setColor(new Color(220, 220, 220));
			g.drawString("Shadow", x, y);

			y += 20;
			g.setColor(new Color(220, 220, 220));
			g.drawString("Engrave", shiftEast(x, 1), shiftSouth(y, 1));
			g.setColor(new Color(50, 50, 50));
			g.drawString("Engrave", x, y);

			y += 20;
			g.setColor(Color.red);
			g.drawString("Outline", shiftWest(x, 1), shiftNorth(y, 1));
			g.drawString("Outline", shiftWest(x, 1), shiftSouth(y, 1));
			g.drawString("Outline", shiftEast(x, 1), shiftNorth(y, 1));
			g.drawString("Outline", shiftEast(x, 1), shiftSouth(y, 1));
			g.setColor(Color.yellow);
			g.drawString("Outline", x, y);

			y += 20;
			g.setColor(Color.black);
			g.drawString("Hollow", shiftWest(x, 1), shiftNorth(y, 1));
			g.drawString("Hollow", shiftWest(x, 1), shiftSouth(y, 1));
			g.drawString("Hollow", shiftEast(x, 1), shiftNorth(y, 1));
			g.drawString("Hollow", shiftEast(x, 1), shiftSouth(y, 1));
			g.setColor(getBackground());
			g.drawString("Hollow", x, y);

			y += 20;
			int w = (g.getFontMetrics()).stringWidth("Segment");
			int h = (g.getFontMetrics()).getHeight();
			int d = (g.getFontMetrics()).getDescent();
			g.setColor(new Color(220, 220, 220));
			g.drawString("Segment", x, y);
			g.setColor(getBackground());
			for (int i = 0; i < h; i += 3)
				g.drawLine(x, y + d - i, x + w, y + d - i);

			y += 20;
			Color top_color = new Color(200, 200, 0);
			Color side_color = new Color(100, 100, 0);
			for (int i = 0; i < 5; i++) {
				g.setColor(top_color);
				g.drawString("3-Dimension", shiftEast(x, i), shiftNorth(shiftSouth(y, i), 1));
				g.setColor(side_color);
				g.drawString("3-Dimension", shiftWest(shiftEast(x, i), 1), shiftSouth(y, i));
			}
			g.setColor(Color.yellow);
			g.drawString("3-Dimension", shiftEast(x, 5), shiftSouth(y, 5));

			y += 50;

			int speed = 2;
			for (int i = 0; i < 20; i++) {
				int font_size = 12 + i;
				g.setFont(new Font("TimesRoman", Font.PLAIN, font_size));
				w = (g.getFontMetrics()).stringWidth("Motion");
				g.setColor(new Color(0, 65 + i * 10, 0));
				g.drawString("Motion", (getWidth() - w) / 2, shiftSouth(y, speed * i));
			}
			
			g.setColor(Color.black);
			g.drawLine(30, 40, 30, 100);
			int radius = 5;
			g.fillOval(30-radius, 100-radius, 2*radius, 2*radius);

		}

		public Dimension getPreferredSize() {
			return new Dimension(200, 300);
		}

		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		int shiftNorth(int p, int distance) {
			return (p - distance);
		}

		int shiftSouth(int p, int distance) {
			return (p + distance);
		}

		int shiftEast(int p, int distance) {
			return (p + distance);
		}

		int shiftWest(int p, int distance) {
			return (p - distance);
		}

	}
}