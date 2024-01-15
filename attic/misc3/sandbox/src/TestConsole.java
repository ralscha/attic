import java.awt.Color;

import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;


public class TestConsole {

  public static void main(String[] args) {
    TextAttributes attrs = new TextAttributes(Color.BLUE, Color.WHITE);
    Console s_console = Enigma.getConsole("Hello World");
    s_console.setTextAttributes(attrs);
    System.out.println("hier steht der text");
    
  }
}
