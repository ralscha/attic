

public class Sub extends Super {

  public void two() {
    System.out.println("three");
  }
  
  public static void main(String[] args) {
    new Sub().one();
  }
}
