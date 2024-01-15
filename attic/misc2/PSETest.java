
import COM.odi.*;

public class PSETest
{
    public static void main(String args[])
    {
        ObjectStore.initialize(null, null);
        Database db = Database.open("myDb.odb", Database.openReadOnly);

    }
}
