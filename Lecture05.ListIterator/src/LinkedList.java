import java.util.Iterator;

//========================================================
// Простая реализация однонаправленного списка в виде
// связанного списка элементов.
//========================================================
public class LinkedList<T> implements List<T> {
  //======================================================
  // Элемент списка
  //======================================================
  private static class Node<T> {
    T info; // Содержимое элемента списка
    Node<T> next; // Указатель на следующий элемент
    
    // Конструкторы
    public Node(T info, Node<T> next) {
      this.info = info;
      this.next = next;
    }
    
    public Node(T info) { this(info, null); }
  }

  // Представление списка
  private Node<T> first = null; // Указатель на первый элемент
  private int count = 0; // Количество элементов списка

  // Вставка нового элемента в начало списка
  public void insert(T elem) {
    first = new Node<T>(elem, first);
    count++;
  }

  // Удаление элемента из начала списка
  public T remove() throws IndexOutOfBoundsException {
    if (first == null) {
      // Список пустой - удалить первый элемент невозможно
      throw new IndexOutOfBoundsException();
    }
    T res = first.info; // Содержимое первого элемента
    first = first.next; // Первым элементом будет следующий за ним
    count--;
    return res;
  }

  // Количество элементов в списке
  public int count() {
    return count;
  }

  //============== Внутренняя итерация элементов =========
  // Действие action выполняется над каждым элементом списка
  public void iterate(List.Action<T> action) {
    // Цикл по элементам списка
    for (Node<T> current = first; current != null; current = current.next) {
      // Выполнение действия
      action.doAction(current.info);
    }
  }

  //============= Внешний итератор элементов списка ===========
  private static class ListIterator<T> implements Iterator<T> {
    private Node<T> current; // Указатель на текущий элемент списка

    // Конструктор итератора запоминает в качестве текущего
    // переданный ему указатель на элемент списка
    public ListIterator(Node<T> first) { current = first; }

    public boolean hasNext() {
      return current != null;
    }

    public T next() {
      T res = current.info;
      current = current.next;
      return res;
    }

    public void remove() {
      // Операция remove в данной реализации не поддерживается
      throw new UnsupportedOperationException();
    }
  }
  
  // Метод создает и возвращает внешний итератор списка 
  public Iterator<T> iterator() {
    return new ListIterator<T>(first);
  }
  
  //=================================================================
  // Тестовая функция для проверки правильности работы
  //=================================================================
  public static void main(String[] args) {
    // Создаем список из строк, переданных в качестве аргументов программе.
    List<String> ls = new LinkedList<String>();
    for (String arg : args) { ls.insert(arg); }
    // Теперь запустим сначала внутренний итератор списка...
    System.out.println("-----------------------");
    ls.iterate(new Action<String>() {
      public void doAction(String element) {
        System.out.println(element);
      }
    });
    // ...и после этого - внешний итератор
    System.out.println("-----------------------");
    for (String elem : ls) {
      System.out.println(elem);
    }
  }
}
