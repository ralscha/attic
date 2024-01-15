package common.log.io;

public interface PrintHandler {
    public void handle(ObjectPrintWriter writer, Value value);
}