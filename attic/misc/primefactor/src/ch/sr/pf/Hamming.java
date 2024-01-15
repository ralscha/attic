package ch.sr.pf;

class Node {
  long fst;
  int idx;
  long mul;

  Node(long f, int i, long m) {
    fst = f;
    idx = i;
    mul = m;
  }
  long first() {
    return fst;
  }
  int index() {
    return idx;
  }
  long multp() {
    return mul;
  }
}

class Cell {
  Object object;
  Cell next;

  Cell(Object obj, Cell lst) {
    object = obj;
    next = lst;
  }
  Object getObject() {
    return object;
  }
  Cell getNext() {
    return next;
  }
  void setNext(Cell nxt) {
    next = nxt;
  }
}

class Queue {
  Cell head;
  Cell tail;

  Queue() {
    head = null;
    tail = null;
  }

  void insert(Object obj) {
    Cell ptr;
    if (head == null) {
      head = tail = new Cell(obj, null);
      return;
    }
    if (((Node) (obj)).first() < ((Node) (head.getObject())).first()) {
      head = new Cell(obj, head);
      return;
    }
    for (ptr = head; ptr.getNext() != null; ptr = ptr.getNext()) {
      if (((Node) (obj)).first() < ((Node) (ptr.getNext().getObject())).first()) {
        ptr.setNext(new Cell(obj, ptr.getNext()));
        return;
      }
    }
    tail = new Cell(obj, null);
    ptr.setNext(tail);
  }

  void enqueue(Object obj) {
    if (obj == null)
      return;
    Cell h = new Cell(obj, null);
    if (head == null)
      head = h;
    else
      tail.setNext(h);
    tail = h;
  }

  Object dequeue() {
    if (head == null)
      return null;
    Object obj = head.getObject();
    head = head.getNext();
    if (head == null)
      tail = null;
    return obj;
  }

  void display() {
    System.out.print(">>>");
    for (Cell t = head; t != null; t = t.getNext())
      System.out.print(((Node) (t.getObject())).first() + "\t");
    System.out.println();
  }

  boolean empty() {
    return head == null;
  }
}

public class Hamming {
  static void ham(int p[]) {
    Queue q = new Queue();
    q.insert(new Node(p[0], 0, 1));

    while (true) {
      Node n = (Node) q.dequeue();
      System.out.print(n.first() + "\t");

      q.insert(
        new Node(n.multp() * p[n.index()] * p[n.index()], n.index(), n.multp() * p[n.index()]));

      if (n.multp() * p[n.index() + 1] > 0)
        q.insert(new Node(n.multp() * p[n.index() + 1], n.index() + 1, n.multp()));
    }
  }

  public static void main(String argv[]) {
    /*
      int p[] = new int[11], i;
    
      
    
      System.out.print("prime list: ");
      try
      {
         for (i=1; i < 11; i++)
         {
            p[i] = Integer.parseInt(argv[i-1]);
            System.out.print(p[i] + " ");
         }
      }
      catch (ArrayIndexOutOfBoundsException e) {}
      System.out.println();
    */

    int[] p = { 2, 3, 5, 11 };
    ham(p);
  }
}
