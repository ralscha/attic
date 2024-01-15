package ch.sr.regextest;

import ch.sr.benchmark.*;

public class Main {

  public static void main(String[] args) {

    boolean jdk14IsAvailable;

    try {
      if (null != Class.forName("java.util.regex.Pattern")) {
        jdk14IsAvailable = true;
      } else {
        jdk14IsAvailable = false;
      }
    } catch (Throwable t) {
      jdk14IsAvailable = false;
    }

    Operation opOro = new OroOperation();
    Operation opJRegex = new JRegexOperation();
    Operation opApacheRegex = new ApacheRegexOperation();
    Operation opGnuRegex = new GnuRegexOperation();

    Reporter reporter = new TextReporter();
    reporter.start();

    Timer tOro = new Repeater(opOro);
    reporter.report(tOro.run());

    Timer tJRegex = new Repeater(opJRegex);
    reporter.report(tJRegex.run());

    if (jdk14IsAvailable) {
      Operation opJdk14Regex = new Jdk14Operation();
      Timer tjdk14Regex = new Repeater(opJdk14Regex);
      reporter.report(tjdk14Regex.run());
    }

    Timer tapacheRegex = new Repeater(opApacheRegex);
    reporter.report(tapacheRegex.run());

    Timer tgnuRegex = new Repeater(opGnuRegex);
    reporter.report(tgnuRegex.run());
    
    reporter.finish();

  }
}