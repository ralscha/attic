

public class TestBean {

	public String getDList() {
		return "Ralph";
	}


	public static void main(String[] args) {
		TestBean tb = new TestBean();
    try {
		  System.out.println((String)PropertyUtils.getSimpleProperty(tb, "DList"));
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}