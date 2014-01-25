/**
 * Author: Alexandre Koubenski
 *
 * ���������� ������������� ������� � ���� ������� ���������
 *
 */
public class ArrayQueue<E> implements Queue<E> {
  E[] body;  // ������ ��������� �������
  int head = 0,  // ��������� �� �������� ������� �������
      count = 0;  // ������� ����� ��������� � �������
  
  /**
   * ����������� ������� �� �������� ���������� ���������
   * @param maxSize - ���������� ���������
   */
  public ArrayQueue(int maxSize) {
    body = (E[]) new Object[maxSize];
  }
  
  /**
   * ����������� ������� �� 100 ���������
   */
  public ArrayQueue() { this(100); }

  /**
   * �������� �������� �� �������
   * @return ��������� �������
   */
  public E dequeue() {
    if (count == 0) throw new IndexOutOfBoundsException();
    count--;
    if (head == body.length) head = 0;
    return body[head++];
  }

  /**
   * �������� ������� �������
   * @return true, ���� ������� �����, false � ��������� ������.
   */
  public boolean empty() { return count == 0; }

  /**
   * ���������� �������� � �������.
   * @param obj - �������, ������� �� ������ � �������.
   * @return ������ ��� ������������ � ������� �������.
   */
  public E enqueue(E obj) {
    if (count == body.length) {
      // ������� �����������, ��������� ������ �����.
      E[] newBody = (E[])new Object[2*body.length];
      // ������������ �������� �� ������� ���� ������� � �����
      int k = 0;
      for (int i = head; i < body.length; ++i) newBody[k++] = body[i];
      for (int i = 0; i < head; ++i) newBody[k++] = body[i];
      // ��������������� ��������� �������
      body = newBody;
      head = 0;
    }
    count++;
    return body[(head + count - 1) % body.length] = obj;
  }

  /**
   * ������ ������� �������� � ������� ��� ��� ��������
   * @return ������ ������� �� �������
   */
  public E head() {
    // �������� ������� �������; ���� ����� - ��������� �������������� ��������.
    if (count == 0) throw new IndexOutOfBoundsException();
    // ���� ��������� head ������� �� ������� �������, ������������� ��� � ������
    if (head == body.length) head = 0;
    // ������ ������� ��������
    return body[head];
  }

  /**
   * ������ ���������� �������� � �������
   * @return ��������� ������� � �������
   */
  public E tail() {
    // �������� ������� �������; ���� ����� - ��������� �������������� ��������.
    if (count == 0) throw new IndexOutOfBoundsException();
    // ��������� ������ ���������� �������� � ������ ���.
    return body[(head + count) % body.length];
  }

  /**
   * ������� �������� ����������������� ���������
   * @param args
   */
  public static void main(String[] args) {
    ArrayQueue<Integer> q = new ArrayQueue<Integer>();
    // ���������� � ������� ��������������� ���� �����
    for (int i = 1; i < 6; ++i) {
      q.enqueue(i);
    }
    // �������� �����, ���������� � �������, � �������� ��.
    while (!q.empty()) {
      System.out.println("extracting from queue: " + q.head());
      q.dequeue();
    }
  }
}
