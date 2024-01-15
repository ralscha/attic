
import java.util.*;
import java.lang.ref.*;

public class WeakValueHashMap extends AbstractMap implements Map {

  private Map hash;

  public WeakValueHashMap(int initialCapacity, float loadFactor) {
    hash = new HashMap(initialCapacity, loadFactor);
  }

  public WeakValueHashMap(int initialCapacity) {
    hash = new HashMap(initialCapacity);
  }

  public WeakValueHashMap() {
    hash = new HashMap();
  }

  public WeakValueHashMap(Map t) {
    this(Math.max(2 * t.size(), 11), 0.75f);
    putAll(t);
  }

  public int size() {
    return entrySet().size();
  }

  public boolean isEmpty() {
    return entrySet().isEmpty();
  }

  public boolean containsKey(Object key) {
    return hash.containsKey(key);
  }

  public Object get(Object key) {
    return ((WeakReference)hash.get(key)).get();
  }

  public Object put(Object key, Object value) {
    return hash.put(key, new WeakReference(value));
  }

  public Object remove(Object key) {
    return hash.remove(key);
  }

  public void clear() {
    hash.clear();
  }

  /* Internal class for entries */
  static private class Entry implements Map.Entry {
    private Map.Entry ent;

    Entry(Map.Entry ent) {
      this.ent = ent;
    }

    public Object getKey() {
      return ent.getKey();
    }

    public Object getValue() {
      return ((WeakReference)ent.getValue()).get();
    }

    public Object setValue(Object value) {
      return ent.setValue(new WeakReference(value));
    }

    private static boolean valEquals(Object o1, Object o2) {
      return (o1 == null) ? (o2 == null) : o1.equals(o2);
    }

    public boolean equals(Object o) {
      if (! (o instanceof Map.Entry))
        return false;
      Map.Entry e = (Map.Entry) o;
      return (valEquals(getKey(), e.getKey()) && valEquals(getValue(), e.getValue()));
    }

    public int hashCode() {
      Object v;
      return (((getKey() == null) ? 0 : getKey().hashCode()) ^
              (((v = getValue()) == null) ? 0 : v.hashCode()));
    }

  }


  /* Internal class for entry sets */
  private class EntrySet extends AbstractSet {
    Set hashEntrySet = hash.entrySet();

    public Iterator iterator() {

      return new Iterator() {
               Iterator hashIterator = hashEntrySet.iterator();
               Entry next = null;

               public boolean hasNext() {
                 while (hashIterator.hasNext()) {
                   Map.Entry ent = (Map.Entry)hashIterator.next();
                   next = new Entry(ent);
                   return true;
                 }
                 return false;
               }

               public Object next() {
                 if ((next == null) && !hasNext())
                     throw new NoSuchElementException();
                   Entry e = next;
                   next = null;
                   return e;
               }

               public void remove() {
                 hashIterator.remove();
               }

             };
    }

    public boolean isEmpty() {
      return !(iterator().hasNext());
    }

    public int size() {
      int j = 0;
      for (Iterator i = iterator(); i.hasNext(); i.next())
        j++;
      return j;
    }

    public boolean remove(Object o) {
      if (!(o instanceof Map.Entry))
        return false;

      Map.Entry e = (Map.Entry) o;
      Object ev = e.getValue();
      Object wk = e.getKey();
      Object hv = ((WeakReference)hash.get(wk)).get();

      if ((hv == null) ? ((ev == null) && hash.containsKey(wk)) : hv.equals(ev)) {
        hash.remove(wk);
        return true;
      }
      return false;
    }

    public int hashCode() {
      int h = 0;
      for (Iterator i = hashEntrySet.iterator(); i.hasNext();) {
        Map.Entry ent = (Map.Entry) i.next();
        Object wk = ent.getKey();
        Object v;
        h += (wk.hashCode() ^ (((v = ent.getValue()) == null) ? 0 : v.hashCode()));
      }
      return h;
    }

  }


  private Set entrySet = null;


  public Set entrySet() {
    if (entrySet == null)
      entrySet = new EntrySet();
    return entrySet;
  }

}
