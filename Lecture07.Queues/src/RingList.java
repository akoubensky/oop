import java.util.Iterator;

/**
 * Класс определяет реализацию кольцевого списка
 * @param <E> - тип элементов списка
 */
public class RingList<E> implements Iterable<E> {

  /**
   * Класс, определяющий узля списка
   * @param <E> - тип элементов списка
   */
  private static class Node<E> {
    // Информационная часть узла
    public E info;
    // Ссылка на следующий узел
    public Node<E> next;
    // Два конструктора узла
    public Node(E info, Node<E> next) { this.info = info; this.next = next; }
    public Node(E info) { this(info, null); }
  }

  /**
   * Класс, определяющий итерацию элементов кольцевого списка.
   * Надо следить за тем, чтобы не зациклиться!
   *
   * @param <E> - тип элементов списка
   */
  private static class ListIterator<E> implements Iterator<E> {
    Node<E> start,  // текущий узел при старте итерации 
            current;  // текущий узел итерации
    boolean moved = false;  // признак того, что хотя бы один элемент уже пройден
    
    /**
     * Конструктор
     * @param list - итерируемый список
     */
    public ListIterator(RingList<E> list) { start = current = list.anchor; }
    
    /**
     * Реализация интерфейсной операции итератора: проверяем, что хотя бы
     * один элемент есть в списке и мы не прошли уже целый круг.
     */
    public boolean hasNext() { return current != null && !(moved && current == start); }
    
    /**
     * Реализация интерфейсной операции итератора: выдаем очередной элемент
     */
    public E next() {
      moved = true;
      current = current.next;
      return current.info;
    }
    
    // Операция remove не реализована
    public void remove() { throw new UnsupportedOperationException(); }
  }
  
  // выделенный элемент кольцевого списка
  Node<E> anchor = null;

  /**
   * Метод, выдающий итератор элементов списка
   */
  public Iterator<E> iterator() { return new ListIterator<E>(this); }
  
  /**
   * Добавление элемента в "голову" списка - после текущего
   * @param e - добавляемый элемент
   */
  public void addHead(E e) {
    Node<E> newNode = new Node<E>(e);
    if (anchor == null) {
      anchor = newNode.next = newNode;
    } else {
      newNode.next = anchor.next;
      anchor.next = newNode;
    }
  }
  
  /**
   * Добавление элемента в "хвост" списка: новый элемент становится "выделенным"
   * @param e - добавляемый элемент
   */
  public void addTail(E e) {
    addHead(e);
    anchor = anchor.next;
  }
  
  /**
   * Удаление элемента, следующего за текущим
   * @return удаленный элемент
   */
  public E removeHead() {
    E res = anchor.next.info;
    if (anchor.next == anchor) {
      anchor = null;
    } else {
      anchor.next = anchor.next.next;
    }
    return res;
  }
  
  /**
   * Проверка пустоты списка
   * @return
   */
  public boolean empty() { return anchor == null; }
  
  /**
   * Выдача элемента, следующего за текущим ("головы" списка)
   * @return
   */
  public E head() { return anchor.next.info; }
  
  /**
   * Выдача текущего элемента ("хвоста") списка
   * @return
   */
  public E tail() { return anchor.info; }
}
