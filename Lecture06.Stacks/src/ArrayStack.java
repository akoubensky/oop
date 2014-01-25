/**
 * Author: Alexandre Koubenski
 *
 * Реализация представления стека в виде массива элементов
 *
 */
public class ArrayStack<T> implements Stack<T> {
  /**
   * Конструктор ограниченного стека на 100 элементов
   */
  public ArrayStack() {
      this(100);
  }

  /**
   * Конструктор ограниченного стека на заданное число элементов
   * @param maxElems  - максимальное число элементов стека
   */
  public ArrayStack(int maxElems) {
      stack = (T[])new Object[maxElems];
      topPtr = 0;
  }

  /**
   * Проверка стека на пустоту
   * @return  - true, если стек пуст, false - в противном случае
   */
  public boolean isEmpty() {
      return topPtr == 0;
  }

  /**
   * Выталкивание элемента из стека
   * @return   - выталкиваемый элемент
   * @throws Underflow  - исчерпание стека - попытка вытолкнуть элемент из пустого стека
   */
  public T pop() throws Underflow {
      if (topPtr == 0) throw new Underflow();
      return stack[--topPtr];
  }

  /**
   * Вталкивание элемента в стек
   * @param element   - вталкиваемый элемент
   */
  public T push(T element) {
      if (topPtr == stack.length) throw new Overflow();
      return stack[topPtr++] = element;
  }

  /**
   * Взятие элемента с вершины стека
   * @return  - верхний элемент стека
   * @throws Underflow  - исчерпание стека - попытка доступа к несуществующему элементу
   */
  public T peek() throws Underflow {
      if (topPtr == 0) throw new Underflow();
      return stack[topPtr-1];
  }

  private T[] stack;     // Массив стековых элементов
  private int topPtr;    // Число элементов в стеке - указатель вершины
  
  /**
   * Функция проверки работоспособности программы
   * @param args
   */
  public static void main(String[] args) {
    ArrayStack<Integer> s = new ArrayStack<Integer>();
    // Вталкиваем в стек последовательно пять чисел
    for (int i = 1; i < 6; ++i) {
      s.push(i);
    }
    // Выталкиваем числа, записанные в стек, и печатаем их.
    try {
      while (!s.isEmpty()) {
        System.out.println("extracting from stack: " + s.peek());
        s.pop();
      }
    } catch (Stack.Underflow x) {
      // Странно... прерывания в этой программе быть не должно!
      System.out.println("Stack underflow!");
    }
  }
}
