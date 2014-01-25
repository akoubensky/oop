/**
 * Author: Alexandre Koubenski
 *
 * Реализация представления очереди в виде массива элементов
 *
 */
public class ArrayQueue<E> implements Queue<E> {
  E[] body;  // массив элементов очереди
  int head = 0,  // указатель на головной элемент очереди
      count = 0;  // счетчик числа элементов в очереди
  
  /**
   * Конструктор очереди на заданное количество элементов
   * @param maxSize - количество элементов
   */
  public ArrayQueue(int maxSize) {
    body = (E[]) new Object[maxSize];
  }
  
  /**
   * Конструктор очереди на 100 элементов
   */
  public ArrayQueue() { this(100); }

  /**
   * Удаление элемента из очереди
   * @return удаленный элемент
   */
  public E dequeue() {
    if (count == 0) throw new IndexOutOfBoundsException();
    count--;
    if (head == body.length) head = 0;
    return body[head++];
  }

  /**
   * Проверка пустоты очереди
   * @return true, если очередь пуста, false в противном случае.
   */
  public boolean empty() { return count == 0; }

  /**
   * Постановка элемента в очередь.
   * @param obj - элемент, который мы ставим в очередь.
   * @return только что поставленный в очередь элемент.
   */
  public E enqueue(E obj) {
    if (count == body.length) {
      // Очередь переполнена, расширяем массив вдвое.
      E[] newBody = (E[])new Object[2*body.length];
      // переписываем элементы из старого тела очереди в новое
      int k = 0;
      for (int i = head; i < body.length; ++i) newBody[k++] = body[i];
      for (int i = 0; i < head; ++i) newBody[k++] = body[i];
      // восстанавливаем состояние очереди
      body = newBody;
      head = 0;
    }
    count++;
    return body[(head + count - 1) % body.length] = obj;
  }

  /**
   * Выдача первого элемента в очереди без его удаления
   * @return первый элемент из очереди
   */
  public E head() {
    // Проверка пустоты очереди; если пуста - возникает исключительная ситуация.
    if (count == 0) throw new IndexOutOfBoundsException();
    // Если указатель head сдвинут за пределы массива, устанавливаем его в начало
    if (head == body.length) head = 0;
    // выдача первого элемента
    return body[head];
  }

  /**
   * Выдача последнего элемента в очереди
   * @return последний элемент в очереди
   */
  public E tail() {
    // Проверка пустоты очереди; если пуста - возникает исключительная ситуация.
    if (count == 0) throw new IndexOutOfBoundsException();
    // Вычисляем индекс последнего элемента и выдаеи его.
    return body[(head + count) % body.length];
  }

  /**
   * Функция проверки работоспособности программы
   * @param args
   */
  public static void main(String[] args) {
    ArrayQueue<Integer> q = new ArrayQueue<Integer>();
    // Записываем в очередь последовательно пять чисел
    for (int i = 1; i < 6; ++i) {
      q.enqueue(i);
    }
    // Вынимаем числа, записанные в очередь, и печатаем их.
    while (!q.empty()) {
      System.out.println("extracting from queue: " + q.head());
      q.dequeue();
    }
  }
}
