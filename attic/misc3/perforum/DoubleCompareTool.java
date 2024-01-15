
public class DoubleCompareTool implements ICompareTool
{
	public DoubleCompareTool()
	{
	}
	public int compare(Object a, Object b)
	{
		double one = ((Double)a).doubleValue();
		double two = ((Double)b).doubleValue();
		if (one < two)
		{
			return -1;
		}
		else if (one > two) 
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}