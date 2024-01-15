import org.exolab.javasource.*;

public class ClassBuilder {

  public static void main(String[] args) {
    JClass testClass = new JClass("Test");

    testClass.addImport("java.util.*");
    testClass.addMember(new JMember(JType.Int, "x"));
    JClass jcString = new JClass("String");

    JMember jMember = new JMember(jcString, "myString");
    jMember.getModifiers().makePrivate();
    testClass.addMember(jMember);

    //-- create constructor
    JConstructor cons = testClass.createConstructor();
    testClass.addConstructor(cons);
    cons.getSourceCode().add("this.x = 6;");

    JMethod jMethod = new JMethod(JType.Int, "getX");
    jMethod.getSourceCode().add("return this.x;");
    testClass.addMethod(jMethod);

    jMethod = new JMethod(null, "setX");
    jMethod.addParameter(new JParameter(JType.Int, "x"));
    jMethod.getSourceCode().add("this.x = x;");
    testClass.addMethod(jMethod);
    

    testClass.print();
  } //-- main

}
