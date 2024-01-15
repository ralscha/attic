
public class HelloWorld {
  String sayHello() {
    return "Hello World" ;
  }

  public static void main(String[] args) {
    HelloWorld world = new HelloWorld();
    System.out.println(world.sayHello());
  }
}