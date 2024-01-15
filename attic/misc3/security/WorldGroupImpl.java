
import java.security.Principal;

public class WorldGroupImpl extends MyGroup
{

    public WorldGroupImpl(String s)
    {
        super(s);
    }

    public boolean isMember(Principal principal)
    {
        return true;
    }
}
