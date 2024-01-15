
package ch.sr.benchmark;

public interface Reporter {
    public void report(Result result);
    public void start();
    public void finish();
    public static final String[] ENV_PROPS = {
        "java.vm.name",
        "java.runtime.version",
        "os.name",
        "os.arch",
        "os.version"
    };
}
