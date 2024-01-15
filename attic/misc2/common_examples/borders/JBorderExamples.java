package borders;


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import common.ui.borders.*;

public class JBorderExamples extends JPanel {
	protected Object[] borders = { JBorder.createThreeDBorder(), "ThreeDBorder",
                               	JBorder.createEdgeBorder(), "EdgeBorder", JBorder.createPatternBorder(), "PatternBorder",
                               	JBorder.createDragBorder(), "DragBorder", JBorder.createShadowBorder(), "ShadowBorder",
                               	JBorder.createGradientBorder(), "GradientBorder",
                               	JBorder.createCurvedBorder(), "CurveBorder", JBorder.createPaintBorder(), "PaintBorder",
                               	JBorder.createStyleBorder(), "StyleBorder", JBorder.createGrooveBorder(), "GrooveBorder",
                               	JBorder.createRoundedBorder(), "RoundedBorder" };

	public JBorderExamples() {
		setBorder(JBorder.createEmptyBorder(5));
		int size = borders.length;
		setLayout(new GridLayout(4, 3, 5, 5));
		for (int i = 0; i < size; i += 2) {
			add(makeLabel((Border) borders[i], (String) borders[i + 1]));
		}
		// PaintBorder using TexturePaint
		ImageIcon icon = new ImageIcon( Toolkit.getDefaultToolkit().createImage(
                                  getClass().getResource("SeaScape.jpg")));

		Image image = icon.getImage();
		int w = image.getWidth(this);
		int h = image.getHeight(this);
		BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(image, 0, 0, this);
		TexturePaint paint = new TexturePaint(buffer, new Rectangle(w, h));
		Border[] list = { JBorder.createShadowBorder(4, Color.black),
                  		JBorder.createLineBorder(Color.black, 1), JBorder.createPaintBorder(paint, 7),
                  		JBorder.createLineBorder(Color.black, 1)};
		add(makeLabel(JBorder.createGroupBorder(list), "GroupBorder"));
	}

	private JLabel makeLabel(Border border, String name) {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(border);
		label.setOpaque(true);
		label.setText(name);
		return label;
	}

	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JFrame frame = new JFrame("JBorder Examples");
		frame.setBounds(50, 50, 400, 300);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER, new JBorderExamples());
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}