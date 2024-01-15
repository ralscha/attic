import org.drools.RuleBase;
import org.drools.WorkingMemory;
import org.drools.rule.Declaration;
import org.drools.rule.Rule;
import org.drools.semantics.java.BlockConsequence;
import org.drools.semantics.java.ExprCondition;
import org.drools.spi.ObjectType;

public class Main {

  public static void main(String[] args) {

    try {
      ObjectType eingangType = new ObjectType() {
        public boolean matches(Object object) {
          return (object instanceof Eingang);
        }
      };
      
      final Declaration eingangDecl = new Declaration(eingangType, "eingang");
      
      RuleBase ruleBase = new RuleBase();    
      
      Rule rule = new Rule("kleiner10");
      rule.addParameterDeclaration( eingangDecl );
      rule.addCondition(new ExprCondition("eingang.getDelta() <= 10", rule.getDeclarationsArray()));
      rule.setConsequence(new BlockConsequence("eingang.setNote(5)"));

      
      Rule rule2 = new Rule("kleiner50undgroesser10");
      rule2.addParameterDeclaration( eingangDecl );
      rule2.addCondition(new ExprCondition("eingang.getDelta() <= 50 && eingang.getDelta() > 10", rule2.getDeclarationsArray()));
      rule2.setConsequence(new BlockConsequence("eingang.setNote(50)"));
      
          
      Rule rule3 = new Rule("grosser50");
      rule3.addParameterDeclaration( eingangDecl );
      rule3.addCondition(new ExprCondition("eingang.getDelta() > 50", rule3.getDeclarationsArray()));
      rule3.setConsequence(new BlockConsequence("eingang.setNote(100)"));
      
        
      ruleBase.addRule(rule);
      ruleBase.addRule(rule2);
      ruleBase.addRule(rule3);
  
      
      WorkingMemory workingMemory = ruleBase.newWorkingMemory();


      long start = System.currentTimeMillis();
      
      for (int i = 0; i < 100; i++) {
        
        Eingang e1 = new Eingang();
        e1.setDelta(5);      
        workingMemory.assertObject(e1);      
        workingMemory.fireAllRules();
              
  
        Eingang e2 = new Eingang();
        e2.setDelta(45);      
        workingMemory.assertObject(e2);      
        workingMemory.fireAllRules();      
  
        Eingang e3 = new Eingang();
        e3.setDelta(55);      
        workingMemory.assertObject(e3);      
        workingMemory.fireAllRules();      
      }
      
      System.out.println((System.currentTimeMillis() - start) + " ms");
      
//      
//      System.out.println(e1.getNote());
//      System.out.println(e2.getNote());
//      System.out.println(e3.getNote());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
