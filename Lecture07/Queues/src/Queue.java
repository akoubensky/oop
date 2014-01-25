
/**
 * Author: Alexandre Koubenski
 * 
 * ���������, �������� ����������� ��� ������ "�������"
 */
public interface Queue<T> {
  /** 
   * ��������� � ������� ������� � �����
   * @param e - �������, ����������� � �������
   * @return �������, ������ ��� ������������ � �������
   */
  T enqueue(T e);
  
  /**
   * ������� �� ������� �������� �������
   * @return ��������� �������
   */
  T dequeue();
  
  /**
   * ����� ������ ������� �������
   * @return ������ ������� �������
   */
  T head();
  
  /**
   * ����� ��������� ������� �������
   * @return ��������� ������� �������
   */
  T tail();
  
  /**
   * ���������, ����� �� �������
   * @return true, ���� ������� �����, false � ��������� ������
   */
  boolean empty();
}