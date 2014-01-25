
/**
 * Реализация стека в виде простого однонаправленного списка элементов
 *
 * @param <E> - тип элементов стека
 */
public class ListStack<E> implements Stack<E>{
  List<E> body = new List<E>();  // Список, представляющий элементы стека.

  //-------------------------------------------------
  // Реализация всех стековых операций сводится к вызову 
  // соответствующих методов представляющего стек списка.
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
   * Функция, проверяющая работоспособность данной реализации
   * @param args
   */
  public static void main(String[] args) {
    ListStack<Integer> s = new ListStack<Integer>();
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
