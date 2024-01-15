package ch.ess.util.attr;

public interface Attributed {
  Attributed add(Attr newAttr);
  Attr find(String attrName) throws AttributeNotFoundException;
  void remove(String attrName);
  void replace(Attr replAttr);
  void removeAll();
  java.util.Iterator attrs();

}
