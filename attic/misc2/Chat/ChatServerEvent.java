
public class ChatServerEvent implements java.io.Serializable {
    public final static int LIST_UPDATE = 1;
    public final static int MESSAGE     = 2;
    public final static int LOGIN       = 3;
    public final static int LOGOUT      = 4;

    int cmd;
    Object args;

    public ChatServerEvent(int cmd, Object args) {
        this.cmd = cmd;
        this.args = args;
    }

    public int getCommand() {
        return cmd;
    }

    public Object getArgs() {
        return args;
    }
}

