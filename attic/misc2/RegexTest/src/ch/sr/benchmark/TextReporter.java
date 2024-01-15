
package ch.sr.benchmark;

import java.io.*;

public class TextReporter implements Reporter {
    PrintWriter out;

    public TextReporter() {
        this(new PrintWriter(new OutputStreamWriter(System.out),true));
    }

    public TextReporter(PrintWriter out) {
        this.out = out;
    }

    public void report(Result result) {
        out.print(result);
        out.println("--------------------------------");
    }

    public void start() {
        out.println("--------------------------------");
        out.println("Regex Benchmark suite");
        out.println("--------------------------------");
        for (int i = 0; i < ENV_PROPS.length; i++) {
            String key = ENV_PROPS[i];
            out.println(key + "=" + System.getProperty(key));
        }
        out.println("--------------------------------");
    }

    public void finish() {
        out.println("done");
    }
}
