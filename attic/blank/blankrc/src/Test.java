
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author sr
 */
public class Test {
  public static void main(String[] args) {
    
    System.out.println(DigestUtils.md5Hex("ralph"));
    
//    try {
//      String url = "http://localhost:8081/blankrc/caucho/echo?ticket=120812";
//
//      HessianProxyFactory factory = new HessianProxyFactory();
//      EchoService basic = (EchoService)factory.create(EchoService.class, url);
//
//      System.out.println("hello(): " + basic.echo("Hallo"));
//    } catch (MalformedURLException e) {
//      e.printStackTrace();
//    }

  }
}