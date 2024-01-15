package lotto.html;

public class Entry {
    private int zahl;
    private int data;

    public Entry(int z, int d) {
        zahl = z;
        data = d;
    }

    public void setZahl(int z) {
        zahl = z;
    }

    public void setData(int d) {
        data = d;
    }

    public int getZahl() {
        return zahl;
    }

    public int getData() {
        return data;
    }

    public boolean equals(Object object) {
        try { 
            Entry e = (Entry)object;
            return ((data == e.getData()) && (zahl == e.getZahl()));
        } catch(ClassCastException e) { }
    	return false;
    }

    public int hashCode() {
        return(data);
    }

    public boolean compare(Entry e) {
        if (e.getData() == data)
            return (zahl < e.getZahl());
        else
            return (data > e.getData());
    }

    public String toString() {
        return (new String(zahl+" / "+data));
    }
}