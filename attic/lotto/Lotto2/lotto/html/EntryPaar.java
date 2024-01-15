package lotto.html;

public class EntryPaar {
    private int zahl1;
    private int zahl2;
    private int data;

    public EntryPaar(int z1, int z2, int d) {
        zahl1 = z1;
        zahl2 = z2;
        data = d;
    }

    public void setZahl1(int z1) {
        zahl1 = z1;
    }

    public void setZahl2(int z2) {
        zahl2 = z2;
    }


    public void setData(int d) {
        data = d;
    }

    public int getZahl1() {
        return zahl1;
    }

    public int getZahl2() {
        return zahl2;
    }

    public int getData() {
        return data;
    }

    public boolean equals(Object object) {
        
        try { 
            EntryPaar e = (EntryPaar)object;
       	    return ((data == e.getData()) &&
    	            (zahl1 == e.getZahl1()) &&
    	            (zahl2 == e.getZahl2()));
        } catch (ClassCastException e) { }
        
    	return false;

    }

    public int hashCode() {
        return(data);
    }

    public boolean compare1(EntryPaar e) {
        if (e.getData() == data)
            if (e.getZahl1() == zahl1)
                return (zahl2 < e.getZahl2());
            else
                return (zahl1 < e.getZahl1());
        else
            return (data > e.getData());
    }

    public boolean compare2(EntryPaar e) {
        if (e.getData() == data)
            if (e.getZahl1() == zahl1)
                return (zahl2 < e.getZahl2());
            else
                return (zahl1 < e.getZahl1());
        else
            return (data < e.getData());
    }


    public String toString() {
        return (new String(zahl1+" + "+zahl2+" / "+data));
    }
}