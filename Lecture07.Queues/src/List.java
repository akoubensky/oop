
import java.util.Iterator;

/**
 * Класс определяет реализацию простого однонаправленного списка
 */
public class List<E> implements Iterable<E> {
  /**
   * Узел списка
   * @param <E> - тип элементов списка
   */
  private static class Node<E> {
    public E info;
    public Node<E> next;
    
    public Node(E info, Node<E> next) { this.info = info; this.next = next; }
    public Node(E info) { this(info, null); }
  }

  /**
   * Внешний итератор элементов списка
   * @param <E> - тип элементов списка, выдаваемых итератором.
   */
  private static class ListIterator<E> implements Iterator<E> {
    // Указатель на текущий элемент списка
    Node<E> current;
    
    // Конструктор
    public ListIterator(List<E> list) { current = list.root; }
    
    // Реализация интерфейсных методов
    public boolean hasNext() { return current != null; }
    public E next() {
      E res = current.info;
      current = current.next;
      return res;
    }
    // Метод remove не реализован.
    public void remove() { throw new UnsupportedOperationException(); }
  }

  // Головной элемент списка
  Node<E> root = null;

  /**
   * Метод, выдающий итератор элементов списка
   */
  public Iterator<E> iterator() { return new ListIterator<E>(this); }
  
  /**
   * Добавление элемента в начало списка
   * @param e - добавляемый элемент
   */
  public void addHead(E e) { root = new Node<E>(e, root); }
  
  /**
   * Удаление первого элемента из списка
   * @return - удаленный элемент
   */
  public E removeHead() { 
    E res = root.info;
    root = root.next;
    return res;
  }
  
  /**
   * Проверка пустоты списка
   * @return true, если список пуст, false в противном случае.
   */
  public boolean empty() { return root == null; }
  
  /**
   * Выдача первого элемента списка
   * @return первый элемент в списке.
   */
  public E head() { return root.info; }
}
