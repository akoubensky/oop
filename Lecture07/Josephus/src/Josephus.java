/**
 * ������ ������ ������.
 * N ������� ����� � �����. ��� ������������ �������� �� 1 �� N.
 * ������ ������ ������� ����� ���� (������� ���������� ������� �� �����).
 * ����� ����� ����������� � ����� ��������.
 * 
 * @author Alexander Kubenskiy
 *
 */
public class Josephus {
  /* ������ ������ �������: �������������.
   * ������� �������� ������ �������, � ������� ������ ������ �������.
   */
  
  /**
   * ���� ���������� ������, �������� ����� ������� � �������� ��������.
   */
  private static class Node {
    int num;    // ����� �������
    Node next;  // ������ �����
    
    // �����������
    public Node(int i) {
      num = i; next = null;
    }
  }
 
  /**
   * ��������� ������ �����.
   */
  private static class Circle {
    Node first = null;  // ��������� �� ������ ���� � ������.

    /**
     * ���������� �������� ����� ��������.
     * ����������� ������� ���������� �������.
     * 
     * @param n ����������� �����.
     */
    public void addAfter(int n) {
      Node newNode = new Node(n);
      if (first == null) {
        first = newNode.next = newNode;
      } else {
        newNode.next = first.next;
        first.next = newNode;
      }
      first = newNode;
    }

    /**
     * ����� ��������� �������� �������� ������ �� ���� �������.
     */
    public void shift() {
      if (first != null) {
        first = first.next;
      }
    }
 
    /**
     * ������� �������� ������� ������.
     * @return true, ���� ������ ����, false � ��������� ������.
     */
    public boolean isEmpty() { return first == null; }
    
    /**
     * ������� �������� ��������� �� � ������ ����� ���� �������.
     * @return true, ���� � ������ ���� �������, false � ��������� ������.
     */
    public boolean isSingle() { return first != null && first.next == first; }

    /**
     * �������� ��������, ���������� �� �������;
     * ����� ������� ���������� �������� ������� �� ���������.
     */
    public void kill() {
      if (first != null && first.next != first) {
        first.next = first.next.next;
        first = first.next;
      }
    }
  }
 
  /**
   * �������� ������� ������� �������� ������ ������� � ���������� ��������
   * ������� ������� ��������, ���� � ������ �� ��������� ���� �������.
   *  
   * @param n ����� ����� ������ (�������� n >= 1).
   * @return ����� ����������� � ����� �������.
   */
  public static int Josephus1(int n) {
    assert n > 0;
    Circle people = new Circle();  // �������� ������ ������
    // �������� ���������� ������
    for (int i = 1; i <= n; ++i) {
      people.addAfter(i);
    }
    
    // ��������� � ������� �������.
    people.shift();
    // ������� ������� �������, ���� �� ��������� ���� ������.
    while (!people.isSingle()) {
      people.kill();
    }
    // ������ ����� ����������� � �����.
    return people.first.num;
  }
  
  /**
   * ������ ������ �������: ������� ������������ ������� � ��������� ��.
   */
 
  /**
   * ���������� ������������ �������:
   * J(n) = (J(n-1)+1) % n + 1;
   * 
   * @param n ����� ����� ������ (�������� n >= 1).
   * @return ����� ����������� � ����� �������.
    */
  public static int Josephus2(int n) {
    assert n > 0;
    return n == 1 ? 1 : (Josephus2(n-1) + 1) % n + 1;
  }
  
  /**
   * ���������� ����� ������� ������������ �������:
   * J(2n) = 2*J(n)-1;
   * J(2n+1) = 2*J(n)+1.
   * 
   * @param n ����� ����� ������ (�������� n >= 1).
   * @return ����� ����������� � ����� �������.
    */
  public static int Josephus3(int n) {
    assert n > 0;
    if (n == 1) return 1;
    if ((n & 1) == 0) {
      return 2*Josephus3(n/2)-1;
    } else {
      return 2*Josephus3(n/2)+1;
    }
  }
  
  /**
   * �������, ������������ ������� ����������. ������� ������� ��������� ���
   * � ����� N � ������������ ��� � �����.
   */

  /**
   * ������� ��������� ��� �������
   * @param n ����� ����� ������ (�������� n >= 1).
   * @return ����� ����������� � ����� �������.
   */
  public static int Josephus4(int n) {
    assert(n > 0);
    // ����� �������� ������� � ������� ������� ����� n.
    int mask = Integer.highestOneBit(n);
    // ��������� ���� ������ � ����� �����.
    return ((n - mask) << 1) | 1;
  }
  
  public static void main(String[] args) {
    System.out.println("Josephus1: " + Josephus1(41));
    System.out.println("Josephus2: " + Josephus2(41));
    System.out.println("Josephus3: " + Josephus3(41));
    System.out.println("Josephus4: " + Josephus4(41));
  }
}
