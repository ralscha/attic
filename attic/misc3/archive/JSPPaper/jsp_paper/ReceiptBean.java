package jsp_paper;

import javax.servlet.*;
import javax.servlet.http.*;

public class ReceiptBean {
    private String name_ = null;
    private String email_ = null;
    private String street1_ = null;
    private String street2_ = null;
    private String city_ = null;
    private String state_ = null;
    private String phone_ = null;
	
    public ReceiptBean() {
    }

    public void setName(String name) {
	name_ = name;
    }
    
    public String getName() {
	return name_;
    }

    public void setEmail(String email) {
	email_ = email;
    }
    
    public String getEmail() {
	return email_;
    }

    public void setStreet1(String street1) {
	street1_ = street1;
    }
    
    public String getStreet1() {
	return street1_;
    }
    
    public void setStreet2(String street2) {
	street2_ = street2;
    }
    
    public String getStreet2() {
	return street2_;
    }

    public void setCity(String city) {
	city_ = city;
    }
    
    public String getCity() {
	return city_;
    }

    public void setState(String state) {
	state_ = state;
    }
    
    public String getState() {
	return state_;
    }

    public void setPhone(String phone) {
	phone_ = phone;
    }
    
    public String getPhone() {
	return phone_;
    }
}
