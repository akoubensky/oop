
/**
 * ���������� ������� � ���� ���������� ������ ���������
 *
 * @param <E> - ��� ��������� �������
 */
public class ListQueue<E> implements Queue<E> {
  RingList<E> body = new RingList<E>();  // ������, �������������� �������� �������
  
  //-------------------------------------------------
  // ���������� ���� �������� ��� �������� �������� � ������ 
  // ��������������� ������� ��������������� ������� ������.
  //-------------------------------------------------
  
  public E dequeue() { return body.removeHead(); }
  
  public boolean empty() { return body.empty(); }

  public E enqueue(E e) {
    body.addTail(e);
    return body.tail();
  }

  public E head() { return body.head(); }
  
  public E tail() { return body.tail(); }

  /**
   * �������, ����������� ����������������� ������ ����������
   * @param args
   */
  public static void main(String[] args) {
    ListQueue<Integer> q = new ListQueue<Integer>();
    // ���������� � ������� ��������������� ���� �����
    for (int i = 1; i < 6; ++i) {
      q.enqueue(i);
    }
    // �������� ����� �� ������� ��������������� � �������� ��.
    while (!q.empty()) {
      System.out.println("extracting from queue: " + q.head());
      q.dequeue();
    }
  }
}
