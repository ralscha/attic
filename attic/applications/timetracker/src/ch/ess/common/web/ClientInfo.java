package ch.ess.common.web;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:16 $ 
  */
public class ClientInfo {
  
  private String appName;
  private String appVersion;
  private String initialWindowWidth;
  private String initialWindowHeight;
  private String screenWidth;
  private String screenHeight;
  private String chromeWidth;
  private String chromeHeight;
  private String colorDepth;
  private String jsVersion;
  private String initialWindowHasToolBar;
  private String initialWindowHasMenuBar;
  private String initialWindowHasStatusBar;
  private String timeZoneOffset;
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }  

  public String getAppName() {
    return appName;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public String getChromeHeight() {
    return chromeHeight;
  }

  public String getChromeWidth() {
    return chromeWidth;
  }

  public String getColorDepth() {
    return colorDepth;
  }

  public String getInitialWindowHasMenuBar() {
    return initialWindowHasMenuBar;
  }

  public String getInitialWindowHasStatusBar() {
    return initialWindowHasStatusBar;
  }

  public String getInitialWindowHasToolBar() {
    return initialWindowHasToolBar;
  }

  public String getInitialWindowHeight() {
    return initialWindowHeight;
  }

  public String getInitialWindowWidth() {
    return initialWindowWidth;
  }

  public String getJsVersion() {
    return jsVersion;
  }

  public String getScreenHeight() {
    return screenHeight;
  }

  public String getScreenWidth() {
    return screenWidth;
  }

  public String getTimeZoneOffset() {
    return timeZoneOffset;
  }

  public void setAppName(String string) {
    appName = string;
  }

  public void setAppVersion(String string) {
    appVersion = string;
  }

  public void setChromeHeight(String string) {
    chromeHeight = string;
  }

  public void setChromeWidth(String string) {
    chromeWidth = string;
  }

  public void setColorDepth(String string) {
    colorDepth = string;
  }

  public void setInitialWindowHasMenuBar(String string) {
    initialWindowHasMenuBar = string;
  }

  public void setInitialWindowHasStatusBar(String string) {
    initialWindowHasStatusBar = string;
  }

  public void setInitialWindowHasToolBar(String string) {
    initialWindowHasToolBar = string;
  }

  public void setInitialWindowHeight(String string) {
    initialWindowHeight = string;
  }

  public void setInitialWindowWidth(String string) {
    initialWindowWidth = string;
  }

  public void setJsVersion(String string) {
    jsVersion = string;
  }

  public void setScreenHeight(String string) {
    screenHeight = string;
  }

  public void setScreenWidth(String string) {
    screenWidth = string;
  }

  public void setTimeZoneOffset(String string) {
    timeZoneOffset = string;
  }

}
