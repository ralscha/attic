import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements HelloInterface {

  public String hello() {
    return "Answer from the Server";
  }
}
