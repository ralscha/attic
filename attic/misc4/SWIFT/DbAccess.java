
import java.io.PrintStream;
import java.sql.*;
import java.util.Vector;

public class DbAccess
{
    protected Connection conn;
    protected String driver;
    protected String url;
    protected String user;
    protected String password;
    private Vector srs;

    
    class SRPair
    {
        public Statement statement;
        public ResultSet resultset;
        public boolean inuse;

        public SRPair(Statement s, ResultSet r, boolean i)
        {
            statement = s;
            resultset = r;
            inuse = i;
        }
    }


    public DbAccess(String driver, String url, String user, String password)
        throws SQLException, ClassNotFoundException
    {
        srs = new Vector(2);
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        connect();
    }

    public DbAccess(Connection conn)
    {
        srs = new Vector(2);
        this.conn = conn;
    }

    public DbAccess()
    {
        srs = new Vector(2);
    }

    public void connect()
        throws SQLException, ClassNotFoundException
    {
        if(conn == null)
        {
            Class.forName(driver);
            if(user == null || password == null)
                conn = DriverManager.getConnection(url);
            else
                conn = DriverManager.getConnection(url, user, password);
        }
    }

    public Connection getConnection()
    {
        return conn;
    }
    
    public void setConnection(Connection conn)
    {
        this.conn = conn;
    }

    public String getDriver()
    {
        return driver;
    }

    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }
    
    public PreparedStatement getPreparedStatement(String sqlString) throws SQLException
    {
        return(conn.prepareStatement(sqlString));
    }
    
    public ResultSet executePreparedStatement(PreparedStatement ps) throws SQLException
    {
        return(ps.executeQuery());
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ResultSet executeQuery(String query)
        throws SQLException
    {
        SRPair obj = getSRPair();
        obj.resultset = obj.statement.executeQuery(query);
        return obj.resultset;
    }

    public int executeUpdate(String sql)
        throws SQLException
    {
        SRPair obj = getSRPair();
        int val = obj.statement.executeUpdate(sql);
        obj.inuse = false;
        return val;
    }

    public void commit() throws SQLException
    {
        conn.commit();
    }
    

    public void close(ResultSet rs)
        throws SQLException
    {
        boolean found = false;
        int i = 0;
        while ((i < srs.size()) && (!found))
        {
            SRPair obj = (SRPair)srs.elementAt(i);
            if (obj.resultset == rs)
            {
                obj.inuse = false;
                found = true;
            }
            i++;
        }
        rs.close();
    }

    private synchronized SRPair getSRPair()
        throws SQLException
    {
        int len = srs.size();
        SRPair obj;
        for(int i = 0; i < len; i++)
        {
            obj = (SRPair)srs.elementAt(i);
            if(!obj.inuse)
            {
                obj.inuse = true;
                return obj;
            }
        }

        obj = new SRPair(conn.createStatement(), null, true);
        srs.addElement(obj);
        return obj;
    }

    
}
