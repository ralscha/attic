
import java.awt.*;
import java.awt.Robot;
import java.awt.event.*;
import javax.swing.*;

public class RobotDemo {
  public static void main(String args[]) throws AWTException {

    // set up frames and panels

    JFrame frame = new JFrame("RobotDemo");
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1));

    // set up fields, labels, and buttons

    final JTextField field = new JTextField(10);
    final JLabel lab = new JLabel();
    field.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                String s = "Length: " + field.getText().length();
                                lab.setText(s);
                              }
                            }
                           );
    JButton button = new JButton("Exit");
    button.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                 System.exit(0);
                               }
                             }
                            );

    // add components to panel and display

    panel.add(field);
    panel.add(lab);
    panel.add(button);
    frame.getContentPane().add(panel);
    frame.setSize(200, 150);
    frame.setLocation(200, 200);
    frame.setVisible(true);

    // create a robot to feed in GUI events

    Robot rob = new Robot();

    // enter some keystrokes

    int keyinput[] = { KeyEvent.VK_T, KeyEvent.VK_E, KeyEvent.VK_S, KeyEvent.VK_T,
                       KeyEvent.VK_I, KeyEvent.VK_N, KeyEvent.VK_G };
    rob.delay(1000);
    rob.keyPress(KeyEvent.VK_SHIFT);
    field.requestFocus();
    for (int i = 0; i < keyinput.length; i++) {
      rob.keyPress(keyinput[i]);
      rob.delay(1000);
    }
    rob.keyRelease(KeyEvent.VK_SHIFT);
    rob.keyPress(KeyEvent.VK_ENTER);

    // move cursor to Exit button

    Point p = button.getLocationOnScreen();
    rob.mouseMove(p.x + 5, p.y + 5);
    rob.delay(2000);

    // press and release left mouse button

    rob.mousePress(InputEvent.BUTTON1_MASK);
    rob.delay(2000);
    rob.mouseRelease(InputEvent.BUTTON1_MASK);
  }
}
