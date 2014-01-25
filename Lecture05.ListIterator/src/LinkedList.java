import java.util.Iterator;

//========================================================
// ������� ���������� ����������������� ������ � ����
// ���������� ������ ���������.
//========================================================
public class LinkedList<T> implements List<T> {
  //======================================================
  // ������� ������
  //======================================================
  private static class Node<T> {
    T info; // ���������� �������� ������
    Node<T> next; // ��������� �� ��������� �������
    
    // ������������
    public Node(T info, Node<T> next) {
      this.info = info;
      this.next = next;
    }
    
    public Node(T info) { this(info, null); }
  }

  // ������������� ������
  private Node<T> first = null; // ��������� �� ������ �������
  private int count = 0; // ���������� ��������� ������

  // ������� ������ �������� � ������ ������
  public void insert(T elem) {
    first = new Node<T>(elem, first);
    count++;
  }

  // �������� �������� �� ������ ������
  public T remove() throws IndexOutOfBoundsException {
    if (first == null) {
      // ������ ������ - ������� ������ ������� ����������
      throw new IndexOutOfBoundsException();
    }
    T res = first.info; // ���������� ������� ��������
    first = first.next; // ������ ��������� ����� ��������� �� ���
    count--;
    return res;
  }

  // ���������� ��������� � ������
  public int count() {
    return count;
  }

  //============== ���������� �������� ��������� =========
  // �������� action ����������� ��� ������ ��������� ������
  public void iterate(List.Action<T> action) {
    // ���� �� ��������� ������
    for (Node<T> current = first; current != null; current = current.next) {
      // ���������� ��������
      action.doAction(current.info);
    }
  }

  //============= ������� �������� ��������� ������ ===========
  private static class ListIterator<T> implements Iterator<T> {
    private Node<T> current; // ��������� �� ������� ������� ������

    // ����������� ��������� ���������� � �������� ��������
    // ���������� ��� ��������� �� ������� ������
    public ListIterator(Node<T> first) { current = first; }

    public boolean hasNext() {
      return current != null;
    }

    public T next() {
      T res = current.info;
      current = current.next;
      return res;
    }

    public void remove() {
      // �������� remove � ������ ���������� �� ��������������
      throw new UnsupportedOperationException();
    }
  }
  
  // ����� ������� � ���������� ������� �������� ������ 
  public Iterator<T> iterator() {
    return new ListIterator<T>(first);
  }
  
  //=================================================================
  // �������� ������� ��� �������� ������������ ������
  //=================================================================
  public static void main(String[] args) {
    // ������� ������ �� �����, ���������� � �������� ���������� ���������.
    List<String> ls = new LinkedList<String>();
    for (String arg : args) { ls.insert(arg); }
    // ������ �������� ������� ���������� �������� ������...
    System.out.println("-----------------------");
    ls.iterate(new Action<String>() {
      public void doAction(String element) {
        System.out.println(element);
      }
    });
    // ...� ����� ����� - ������� ��������
    System.out.println("-----------------------");
    for (String elem : ls) {
      System.out.println(elem);
    }
  }
}
