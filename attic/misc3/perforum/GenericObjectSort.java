
import java.lang.reflect.*;

public class GenericObjectSort
{
	private Class[] parameterTypes;
	private Object[] parameterValues;
	private ICompareTool ict;
	private String methodName;
	
	public GenericObjectSort()
	{
	}
	private Object getTheSortMethodValueFromArray(Object[] objectArray, int index)
	{
		Object currentObject = objectArray[index];
		return(getTheSortMethodValueFromObject(currentObject));
	}
	private Object getTheSortMethodValueFromObject(Object currentObject)
	{
		Object returnValue = null;
		try
		{
			Class c = currentObject.getClass();
			Method method = c.getMethod(methodName, parameterTypes);
			returnValue = method.invoke(currentObject, parameterValues);
		}
		catch(InvocationTargetException ite)
		{
			System.out.println("Inside Catch - GenericObjectSort.InvocationTargetException: " + ite);
		}
		catch(NoSuchMethodException nsme)
		{
			System.out.println("Inside Catch - GenericObjectSort.NoSuchMethodException: " + nsme);
		}
		catch(IllegalAccessException iae)
		{
			System.out.println("Inside Catch - GenericObjectSort.IllegalAccessException: " + iae);
		}
		return returnValue;
	}
	public void sort(Object[] objectArray, String methodName, Class[] parameterTypes, Object[] parameterValues, ICompareTool ict)
	{
		this.parameterTypes = parameterTypes;
		this.parameterValues = parameterValues;
		this.ict = ict;
		this.methodName = methodName;
		sort(objectArray, 0, Array.getLength(objectArray) -1);
	}
	private void sort(Object[] objectArray, int beginingOfArrayForThisSortSequence, int endingOfArrayForThisSortSequence)
	{
		int currentLowElement = beginingOfArrayForThisSortSequence;
		int currentHighElement = endingOfArrayForThisSortSequence;
		Object objectAtMiddleOfSortSequence = null;
		if(endingOfArrayForThisSortSequence > beginingOfArrayForThisSortSequence)
		{
			objectAtMiddleOfSortSequence = Array.get(objectArray, (beginingOfArrayForThisSortSequence + endingOfArrayForThisSortSequence) / 2);
			while(currentLowElement <= currentHighElement)
			{
				while(currentLowElement < endingOfArrayForThisSortSequence && ict.compare(getTheSortMethodValueFromArray(objectArray, currentLowElement), getTheSortMethodValueFromObject(objectAtMiddleOfSortSequence)) < 0)
				{
					++currentLowElement;
				}
				while(currentHighElement > beginingOfArrayForThisSortSequence 
					&& ict.compare(getTheSortMethodValueFromArray(objectArray, currentHighElement), getTheSortMethodValueFromObject(objectAtMiddleOfSortSequence)) > 0)
				{
					--currentHighElement;
				}
				if(currentLowElement <= currentHighElement)
				{
					swap(objectArray, currentLowElement, currentHighElement);
					++currentLowElement;
					--currentHighElement;
				}
			}
			if(beginingOfArrayForThisSortSequence < currentHighElement)
			{
				sort(objectArray, beginingOfArrayForThisSortSequence, currentHighElement);
			}
			if(currentLowElement < endingOfArrayForThisSortSequence)
			{
				sort(objectArray, currentLowElement, endingOfArrayForThisSortSequence);
			}
		}
		
	}
	private void swap(Object a, int i, int j)
	{
		Object t = Array.get(a, i);
		Array.set(a, i, Array.get(a, j));
		Array.set(a, j, t);
	}

}