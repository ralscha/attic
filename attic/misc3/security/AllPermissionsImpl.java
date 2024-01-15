
import java.security.acl.Permission;


public class AllPermissionsImpl extends MyPermission
{

    public AllPermissionsImpl(String s)
    {
        super(s);
    }

    public boolean equals(Permission permission)
    {
        return true;
    }
}
