
/**
 * ���������� ����� � ���� �������� ����������������� ������ ���������
 *
 * @param <E> - ��� ��������� �����
 */
public class ListStack<E> implements Stack<E>{
  List<E> body = new List<E>();  // ������, �������������� �������� �����.

  //-------------------------------------------------
  // ���������� ���� �������� �������� �������� � ������ 
  // ��������������� ������� ��������������� ���� ������.
  //-------------------------------------------------
  
  public boolean isEmpty() { return body.empty(); }
  
  public E peek() throws Stack.Underflow {
    if (isEmpty()) throw new Stack.Underflow();
    return body.head();
  }

  public E pop() throws Stack.Underflow {
    if (isEmpty()) throw new Stack.Underflow();
    return body.removeHead();
  }

  public E push(E element) {
    body.addHead(element);
    try {
      return peek();
    } catch (Stack.Underflow e) {
      return null;
    }
  }

  /**
   * �������, ����������� ����������������� ������ ����������
   * @param args
   */
  public static void main(String[] args) {
    ListStack<Integer> s = new ListStack<Integer>();
    // ���������� � ���� ��������������� ���� �����
    for (int i = 1; i < 6; ++i) {
      s.push(i);
    }
    // ����������� �����, ���������� � ����, � �������� ��.
    try {
      while (!s.isEmpty()) {
        System.out.println("extracting from stack: " + s.peek());
        s.pop();
      }
    } catch (Stack.Underflow x) {
      // �������... ���������� � ���� ��������� ���� �� ������!
      System.out.println("Stack underflow!");
    }
  }
}
