
package ch.sr.benchmark;

public interface Timer {
    public Operation getOperation();
    public long time();
    public Result run();
}
