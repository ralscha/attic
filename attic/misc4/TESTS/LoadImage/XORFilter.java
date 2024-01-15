
import java.awt.image.*;

public class XORFilter extends RGBImageFilter
{
    public XORFilter()
    {
        canFilterIndexColorModel = true;
    }

    public int filterRGB(int x, int y, int rgb)
    {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb >> 0) & 0xff;

        return((0xff << 24) | ((0xff - r) << 16) | ((0xff - g) << 8) | ((0xff - b) << 0));
    }
}