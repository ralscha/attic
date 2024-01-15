package ch.sr.collectiontest;


import ch.sr.benchmark.*;

public class Main {

  public static void main(String[] args) {

   

    Operation trove = new TroveOperation();
    Operation fast  = new FastUtilOperation();
    
    Reporter reporter = new TextReporter();
    reporter.start();

    Timer tTrove = new Repeater(trove);
    reporter.report(tTrove.run());

    Timer tFast = new Repeater(fast);
    reporter.report(tFast.run());

    reporter.finish();

  }
}
