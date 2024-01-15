import java.util.*;

public interface SMIServerListener extends EventListener
{
    public void newData(SMIServerEvent e);
}