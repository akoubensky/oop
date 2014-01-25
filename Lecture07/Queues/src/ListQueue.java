
/**
 * Реализация очереди в виде кольцевого списка элементов
 *
 * @param <E> - тип элементов очереди
 */
public class ListQueue<E> implements Queue<E> {
  RingList<E> body = new RingList<E>();  // Список, представляющий элементы очереди
  
  //-------------------------------------------------
  // Реализация всех операций над очередью сводится к вызову 
  // соответствующих методов представляющего очередь списка.
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
   * Функция, проверяющая работоспособность данной реализации
   * @param args
   */
  public static void main(String[] args) {
    ListQueue<Integer> q = new ListQueue<Integer>();
    // Записываем в очередь последовательно пять чисел
    for (int i = 1; i < 6; ++i) {
      q.enqueue(i);
    }
    // Вынимаем числа из очереди последовательно и печатаем их.
    while (!q.empty()) {
      System.out.println("extracting from queue: " + q.head());
      q.dequeue();
    }
  }
}
