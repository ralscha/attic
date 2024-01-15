import java.io.*;
import java.util.*;


public class InTest2
{
    private String stringconv(String in)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++)
        {
            if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.'))
            {
                sb.append(in.charAt(i));
            }
        }
        return (sb.toString());
    }


    public InTest2()
    {
        String test1 = new String("100'500.55");
        String test2 = new String("100,100.00");
        System.out.println(stringconv(test1));
        System.out.println(stringconv(test2));
    }

    public static void main(String args[])
    {
        new InTest2();
    }
}
