package test;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.config.PropertyPlaceholderConfigurer;

public class ResConf extends PropertyPlaceholderConfigurer {

  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    Properties props = new Properties();
    props.setProperty("test.value", String.valueOf(110));
    processProperties(beanFactory, props);

  }

}
