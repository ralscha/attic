import java.util.*;

public class LRectGroup
{
    private int max, min;
    private Vector numbers;
    private int num;

    LRectGroup(int min, int max) {
        this.max = max;
        this.min = min;
        num = 0;
        numbers = new Vector();
    }

    public Vector getNumbers() {
        return(numbers);
    }

    public boolean isOK() {
        if ((num <= max) && (num >= min))
            return true;
        else
            return false;
    }


    private boolean isMax() {
        if (num == max)
            return true;
        else
            return false;
    }

    public boolean addNumber(String n) {
        if (isMax())
            return(false);
        else {
            if (!numbers.contains(n)) {
                num++;
                numbers.addElement(n);
            }
            return(true);
        }

    }

    public void removeNumber(String n)
    {
        if (numbers.contains(n)) {
            num--;
            numbers.removeElement(n);
        }

    }
}