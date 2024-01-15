package ch.ess.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public final class Constants {

  //Locales
  public static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
  
  public static final NumberFormat SIMPLE_FORMAT = new DecimalFormat("0");
  public static final NumberFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");
  public static final NumberFormat TWO_DECIMAL_FORMAT = new DecimalFormat("#,##0.00");  
  public static final NumberFormat TWO_DECIMAL_NOGROUPING_FORMAT = new DecimalFormat("###0.00");

  public static final DateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MM.yyyy");
  public static final DateFormat YEAR_DATE_FORMAT = new SimpleDateFormat("yyyy");  
  public static final DateFormat WEEK_DATE_FORMAT = new SimpleDateFormat("'W'w.yyyy");  
  
  private static String javascriptDateFormatPattern;
  private static String javascriptDateTimeFormatPattern;
  
  private static String datePattern;
  private static String parseDatePattern;
  private static String dateTimePattern;
  private static String timePattern;  
    
  public static final String BASE_GOOGLE_URL = "http://maps.google.com/maps/geo?q={0}&output=csv&key={1}";
  
  public final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfXpRCANWS4+KPrJx1MVso3yBBhUsouZLjuBpHiJ9URDoyW5px14KfKFeKQO+yDaAOam8xOV3iJ3liN1f8ljCzJkmDS+NCftTslErw/ahdesHDupWQPNZqeJvR6XMjG5VNdGlwq9mge2wFsKb9Rh21wIO33eeMZCGy8PibjRfWoQIDAQAB";
  
  private static Set<String> features = new HashSet<String>();
 
  public static void setDefaultDateFormat(final String pattern, final String parseDatePattern,
      final String dateTimePattern, final String timePattern) {

    Constants.datePattern = pattern;
    Constants.parseDatePattern = parseDatePattern;
    Constants.dateTimePattern = dateTimePattern;
    Constants.timePattern = timePattern;

    javascriptDateFormatPattern = StringUtils.replace(pattern, "dd", "%d");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "MM", "%m");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "yyyy", "%Y");
    javascriptDateFormatPattern = StringUtils.replace(javascriptDateFormatPattern, "yy", "%Y");
    
    javascriptDateTimeFormatPattern = StringUtils.replace(dateTimePattern, "dd", "%d");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "MM", "%m");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "yyyy", "%Y");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "yy", "%Y");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "HH", "%H");
    javascriptDateTimeFormatPattern = StringUtils.replace(javascriptDateTimeFormatPattern, "mm", "%M");
  }

  public static void setFeatures(String encodedFeaturesString) {
    
    String featuresString = null;
    
    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(Constants.PUBLIC_KEY.getBytes()));
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, publicKey);      

      ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(encodedFeaturesString.getBytes()));
      BufferedReader br = new BufferedReader(new InputStreamReader(new CipherInputStream(bis, cipher)));
      
      featuresString = br.readLine();
      br.close();
      
      
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }


    if (featuresString != null) {
      String[] featureArray = StringUtils.split(featuresString, ";");
      features = new HashSet<String>();
      for (String feature : featureArray) {
        features.add(feature);
      }
    }
  }
  
  public static boolean hasFeatures() {
    return !(features == null || features.isEmpty());
  }
  
  public static boolean hasFeature(String feature) {
    return features.contains(feature);
  }

  public static String getJavascriptDateFormatPattern() {
    return javascriptDateFormatPattern;
  }
  
  public static String getJavascriptDateTimeFormatPattern() {
    return javascriptDateTimeFormatPattern;
  }

  public static String getDateFormatPattern() {
    return datePattern;
  }

  public static String getParseDateFormatPattern() {
    return parseDatePattern;
  }

  public static String getDateTimeFormatPattern() {
    return dateTimePattern;
  }

  public static String getTimeFormatPattern() {
    return timePattern;
  }

  public static final SimpleDateFormat DATE_UTC_FORMAT;
  static {
    DATE_UTC_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    DATE_UTC_FORMAT.setTimeZone(UTC_TZ);
  }

  public static final String DATETIME_LOCAL_FORMAT = "yyyyMMdd'T'HHmmss";
  public static final String DATE_LOCAL_FORMAT = "yyyyMMdd";

}
