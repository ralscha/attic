
package ch.sr.benchmark;

public class Result {
    long time;
    int iterations;
    String description;
    long memUsage;
        
    public long getTime() {
        return this.time;
    }

    public void setTime(long total){
        this.time = total;
    }


    public long getTimeAvg() {
        return time / iterations;
    }


    public int getIterations() {
        return this.iterations;
    }

    public void setIterations(int argIterations){
        this.iterations = argIterations;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String argDescription){
        this.description = argDescription;
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(getDescription() + "\n");
        b.append("Iterations: " + getIterations() + "\n");
        b.append("Mem Usage (kb): " + (getMemUsage() / 1024) + "\n");
        b.append("Total (msec): " + getTime() + "\n");
        b.append("Average (msec): " + getTimeAvg() + "\n");
        return b.toString();
    }
    
    public long getMemUsage() {
      return memUsage;
    }

    public void setMemUsage(long memUsage) {
      this.memUsage = memUsage;
    }

}
