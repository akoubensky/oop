import java.util.Iterator;

/**
 * ����� ���������� ���������� ���������� ������
 * @param <E> - ��� ��������� ������
 */
public class RingList<E> implements Iterable<E> {

  /**
   * �����, ������������ ���� ������
   * @param <E> - ��� ��������� ������
   */
  private static class Node<E> {
    // �������������� ����� ����
    public E info;
    // ������ �� ��������� ����
    public Node<E> next;
    // ��� ������������ ����
    public Node(E info, Node<E> next) { this.info = info; this.next = next; }
    public Node(E info) { this(info, null); }
  }

  /**
   * �����, ������������ �������� ��������� ���������� ������.
   * ���� ������� �� ���, ����� �� �����������!
   *
   * @param <E> - ��� ��������� ������
   */
  private static class ListIterator<E> implements Iterator<E> {
    Node<E> start,  // ������� ���� ��� ������ �������� 
            current;  // ������� ���� ��������
    boolean moved = false;  // ������� ����, ��� ���� �� ���� ������� ��� �������
    
    /**
     * �����������
     * @param list - ����������� ������
     */
    public ListIterator(RingList<E> list) { start = current = list.anchor; }
    
    /**
     * ���������� ������������ �������� ���������: ���������, ��� ���� ��
     * ���� ������� ���� � ������ � �� �� ������ ��� ����� ����.
     */
    public boolean hasNext() { return current != null && !(moved && current == start); }
    
    /**
     * ���������� ������������ �������� ���������: ������ ��������� �������
     */
    public E next() {
      moved = true;
      current = current.next;
      return current.info;
    }
    
    // �������� remove �� �����������
    public void remove() { throw new UnsupportedOperationException(); }
  }
  
  // ���������� ������� ���������� ������
  Node<E> anchor = null;

  /**
   * �����, �������� �������� ��������� ������
   */
  public Iterator<E> iterator() { return new ListIterator<E>(this); }
  
  /**
   * ���������� �������� � "������" ������ - ����� ��������
   * @param e - ����������� �������
   */
  public void addHead(E e) {
    Node<E> newNode = new Node<E>(e);
    if (anchor == null) {
      anchor = newNode.next = newNode;
    } else {
      newNode.next = anchor.next;
      anchor.next = newNode;
    }
  }
  
  /**
   * ���������� �������� � "�����" ������: ����� ������� ���������� "����������"
   * @param e - ����������� �������
   */
  public void addTail(E e) {
    addHead(e);
    anchor = anchor.next;
  }
  
  /**
   * �������� ��������, ���������� �� �������
   * @return ��������� �������
   */
  public E removeHead() {
    E res = anchor.next.info;
    if (anchor.next == anchor) {
      anchor = null;
    } else {
      anchor.next = anchor.next.next;
    }
    return res;
  }
  
  /**
   * �������� ������� ������
   * @return
   */
  public boolean empty() { return anchor == null; }
  
  /**
   * ������ ��������, ���������� �� ������� ("������" ������)
   * @return
   */
  public E head() { return anchor.next.info; }
  
  /**
   * ������ �������� �������� ("������") ������
   * @return
   */
  public E tail() { return anchor.info; }
}
