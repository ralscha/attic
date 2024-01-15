
import java.awt.Point;

class Area
{
    int area;
    int x1, y1, x2, y2;

    public Area(int x1, int y1, int x2, int y2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        int w = x2 - x1 + 1;
        int h = y2 - y1 + 1;

        area = w * h;
    }

    public int getArea()
    {
        return area;
    }


    public String toString()
    {
        return ("x1 = " + x1 + "/y1 = " + y1 + "   x2 = " + x2 + "/y2 = " + y2 + "   AREA = " + area);
    }

    public boolean equals(Area a)
    {
        if (this.x1 == a.x1 && this.x2 == a.x2 &&
            this.y1 == a.y1 && this.y2 == a.y2)
            return true;
        else
            return false;

    }
}