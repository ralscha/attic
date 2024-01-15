
import com.companyname.appname.entity.*;
import com.companyname.util.sort.*;
import java.util.*;

public class GenericAccountSort 
{
   public static void main(java.lang.String[] args) 
   {
      long currentTimeInMillis = System.currentTimeMillis();
      System.out.println(currentTimeInMillis);
      Object [] objectArray = new Object[100];
      Calendar c = Calendar.getInstance();
      for(int i =0; i < objectArray.length; i++)
      {
         Account account = new Account();
         c.roll(c.DATE,true);
         account.setDateOpened(c.getTime());
         account.setTotal(Math.random());
         objectArray[i] = account;
      }
      GenericObjectSort gos = new GenericObjectSort();
      ICompareTool ict = new DoubleCompareTool();
      gos.sort(objectArray, "getTotal", new Class[0],  new Object[0], ict);
      for(int x = objectArray.length - 1; x >= 0; x--)
      {
         Account account = (Account)objectArray[x];
         System.out.println(account.getTotal());
      }
      ict = new DateCompareTool();
      gos.sort(objectArray, "getDateOpened", new Class[0],  new Object[0], ict);
      for(int x = objectArray.length - 1; x >= 0; x--)
      {
         Account account = (Account)objectArray[x];
         System.out.println(account.getDateOpened());
      }   
      System.out.println(System.currentTimeMillis() - currentTimeInMillis);
      }
}
