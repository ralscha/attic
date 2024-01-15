/***
 * $RCSfile: PrintBits.java,v $ $Revision: 1.1 $ $Date: 1999/10/08 08:08:58 $
 *
 * QUESTION:
 * How can I get the binary representation of a letter or number?  For
 * example, the letter "A" is 100 0001, the letter "B" is 100 0010, etc.
 ***/                       

public class PrintBits {

  // Doesn't do bounds checking
  public static void printBits(int number, int begin, int length) {
    int bitmask;

    bitmask = 0x80000000 >>> begin;
    length+=begin;

    for(int i=begin; i < length; ++i) {
      if((number & bitmask) == 0)
        System.out.print('0');
      else
        System.out.print('1');
      bitmask >>>= 1;
    }
    System.out.println();
  }


  public static void main(String[] args) {
    for(char ch='A'; ch <= 'Z'; ++ch)
      // Print the last 8 bits of the character.
      printBits(ch, 24, 8);
  }
}
