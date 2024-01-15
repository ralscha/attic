package ch.sr.benchmark;

public class Repeater implements Timer {
    int _count;
    Operation _operation;

    public Repeater(Operation o) {
        _count = o.getIterationCount();
        _operation = o;
    }

    public Result run() {
        MemoryUtil.collectGarbage();
        
        _operation.warmUp();
        
        long initalSize = MemoryUtil.getUsedMemory();

        long time = time();
        
        long finalSize = MemoryUtil.getUsedMemory();
        
        Result r = new Result();
        r.setMemUsage(finalSize - initalSize);
        r.setTime(time);
        r.setIterations(_count);
        r.setDescription(_operation.toString());
        return r;
    }

    public long time() {
            
        long then = System.currentTimeMillis();
        for (int i = 0; i < _count; i++) {
            _operation.execute();
        }
        long now = System.currentTimeMillis();
        return (now -then);
    }


    public Operation getOperation() {
        return _operation;
    }
}
