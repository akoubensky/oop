/**
 * Author: Alexandre Koubenski
 *
 * ����������� ����, ��������� � ���� ����������
 *
 */
public interface Stack<T> {
    /**
     * �������� ������� ����������, ������� ���������� ��� ������ �� ������.
     * Underflow - ���������� ����� - ������� ������� � ��������� ������� �����
     * Overflow - ������������ ����� - ������� ��������� ������� � ��������� ����������� ����
     */
    @SuppressWarnings("serial")
    static class Underflow extends Exception {
        public Underflow() { super("Stack underflow"); }
    }

    @SuppressWarnings("serial")
    static class Overflow extends RuntimeException {
        public Overflow() { super("Stack overflow"); }
    };

    /**
     * ����������� �������� � ����
     * @param element   - ������������ �������
     */
    T push(T element);

    /**
     * ������������ �������� �� �����
     * @return   - ������������� �������
     * @throws Underflow  - ���������� ����� - ������� ���������� ������� �� ������� �����
     */
    T  pop() throws Underflow;

    /**
     * ������ �������� � ������� �����
     * @return  - ������� ������� �����
     * @throws Underflow  - ���������� ����� - ������� ������� � ��������������� ��������
     */
    T  peek() throws Underflow;

    /**
     * �������� ����� �� �������
     * @return  - true, ���� ���� ����, false - � ��������� ������
     */
    boolean isEmpty();
}
