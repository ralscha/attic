
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class State implements java.io.Serializable
{
    private String proc_Center;
    private String order_Number;
    private int gtf_Number;
    private String state_Num;
    private String state_Tran;
    private String message_Type;
    private java.sql.Date state_Date;
    private java.sql.Time state_Time;    

    public State()
    {
        clear();
    }

    public State(String proc_Center, String order_Number, int gtf_Number, String state_Num,
                 String state_Tran, String message_Type, java.sql.Date state_Date,
                 java.sql.Time state_Time)
    {
        this.proc_Center  = proc_Center;
        this.order_Number = order_Number;
        this.gtf_Number   = gtf_Number;
        this.state_Num    = state_Num;
        this.state_Tran   = state_Tran;
        this.message_Type = message_Type;
        this.state_Date   = state_Date;
        this.state_Time   = state_Time;
    }

    public void setProc_Center(String proc_Center)
    {
        this.proc_Center = proc_Center;
    }

    public void setOrder_Number(String order_Number)
    {
        this.order_Number = order_Number;
    }

    public void setGtf_Number(int gtf_Number)
    {
        this.gtf_Number = gtf_Number;
    }

    public void setState_Num(String state_Num)
    {
        this.state_Num = state_Num;
    }

    public void setState_Tran(String state_Tran)
    {
        this.state_Tran = state_Tran;
    }


    public void setMessage_Type(String message_Type)
    {
        this.message_Type = message_Type;
    }
    
    public void setState_Date(java.sql.Date state_Date)
    {
        this.state_Date = state_Date;
    }

    public void setState_Time(java.sql.Time state_Time)
    {
        this.state_Time = state_Time;
    }


    public String getProc_Center()
    {
        return(proc_Center);
    }

    public String getOrder_Number()
    {
        return(order_Number);
    }
    
    public int getGtf_Number()
    {
        return(gtf_Number);
    }

    public String getState_Num()
    {
        return(state_Num);
    }
    
    public String getState_Tran()
    {
        return(state_Tran);
    }

    public String getMessage_Type()
    {
        return(message_Type);
    }
    
    public java.sql.Date getState_Date()
    {
        return(state_Date);
    }
    
    public java.sql.Time getState_Time()
    {
        return(state_Time);
    }

	public void clear()
	{
        proc_Center = null;
        order_Number = null;
        gtf_Number = 0;
        state_Num = null;
        state_Tran = null;
        message_Type = null;
        state_Date = null;
        state_Time = null;
	}


	public String toString()
	{
		return "#State("+proc_Center+" "+order_Number+" "+gtf_Number+" "+state_Num+" "+state_Tran+
		       " "+message_Type+" "+state_Date+" "+state_Time+" "+")";
	}
}
