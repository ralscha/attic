package ch.ralscha.mycustomers.client;

import com.extjs.gxt.ui.client.data.BeanModelMarker;
import com.extjs.gxt.ui.client.data.BeanModelMarker.BEAN;

@BEAN(ch.ralscha.mycustomers.data.Customer.class)
public interface CustomerBeanModel extends BeanModelMarker {

}
