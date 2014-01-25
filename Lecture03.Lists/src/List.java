import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация простого односвязного упорядоченного списка.
 * @param <E> Тип элементов списка.
 */
public class List<E extends Comparable<E>> {

  /**
   * Звено списка.
   * @param <T> Тип информационного поля звена списка.
   */
  private static class Link<T> {
    T info;        // Полезная информация.
    Link<T> next;  // Ссылка на следующее звено.
    
    /**
     * Конструктор
     * @param info Информационное поле.
     * @param next Ссылка на следующее звено.
     */
    public Link(T info, Link<T> next) {
      this.info = info;
      this.next = next;
    }
  }

  /**
   * Голова списка - ссылка на первый элемент.
   */
  private Link<E> head = null;
  
  /**
   * Длина списка.
   */
  private int length = 0;
  
  /**
   * Функция доступа: длина списка.
   * @return Длина списка.
   */
  public int length() { return length; }
  
  /**
   * Вставка нового элемента в упорядоченный список.
   * @param element Вставляемая информация.
   */
  public void insert(E element) {
    Link<E> current = head;  // Ссылка для поиска места вставки.
    Link<E> pred = null;     // Ссылка на предыдущее звено.
    // Цикл поиска первого звена в списке, содержащего большее или равное значение.
    while (current != null && element.compareTo(current.info) > 0) {
      pred = current;
      current = current.next;
    }
    // Создаем новый элемент.
    Link<E> newLink = new Link<E>(element, current);
    // Вставляем новый элемент на свое место в списке - после предыдущего.
    if (pred == null) {
      head = newLink;
    } else {
      pred.next = newLink;
    }
    length++;
  }
  
  /**
   * Поиск элемента в упорядоченном списке. Список - это не массив, поэтому
   * ничего более эффективного, чем линейный поиск, не придумать...
   * @param element Искомый элемент.
   * @return true, если элемент есть в списке, false в противном случае.
   */
  public boolean has(E element) {
    Link<E> current = head;
    // Цикл поиска первого звена в списке, содержащего большее или равное значение.
    while (current != null && element.compareTo(current.info) > 0) {
      current = current.next;
    }
    // Возврат результата.
    return current != null && element.equals(current.info);
  }
  
  @SuppressWarnings("serial")
  static class FoundElementException extends RuntimeException {}
  @SuppressWarnings("serial")
  static class NotFoundElementException extends RuntimeException {}
  
  public boolean search(final E element) {
    try {
      iterate(new Command<E>() {
        public void execute(E elem) {
          int compare = elem.compareTo(element);
          if (compare > 0) {
            throw new NotFoundElementException();
          } else if (compare == 0) {
            throw new FoundElementException();
          }
        }
      });
    } catch (FoundElementException e) {
      return true;
    } catch (NotFoundElementException e) {
      return false;
    }
    return false;
  }

  public Iterator<E> iterator() {
    return new Iterator<E>() {
      Link<E> next = head;
      
      @Override
      public boolean hasNext() {
        return next != null;
      }
  
      @Override
      public E next() {
        if (next == null) throw new NoSuchElementException();
        Link<E> current = next;
        next = next.next;
        return current.info;
      }
  
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  
  /**
   * Удаление элемента из списка, если он там существовал.
   * @param element Удаляемый элемент.
   * @return true, если элемент был удален, false, если его в списке не было.
   */
  public boolean remove(E element) {
    Link<E> current = head;  // Ссылка для поиска удаляемого элемента.
    Link<E> pred = null;     // Ссылка на предыдущий элемент.
    // Цикл поиска удаляемого элемента.
    while (current != null && element.compareTo(current.info) > 0) {
      pred = current;
      current = current.next;
    }
    // Нашли?
    boolean toRemove = current != null && element.equals(current.info);
    if (toRemove) {
      // Удаление звена из списка.
      length--;
      if (pred == null) {
        head = current.next;
      } else {
        pred.next = current.next;
      }
    }
    // Возврат результата.
    return toRemove;
  }
 
  /**
   * Внутренняя итерация элементов списка.
   * @param action Действие, которое надо произветси с каждым элементом списка.
   */
  public void iterate(Command<E> action) {
    for (Link<E> current = head; current != null; current = current.next) {
      action.execute(current.info);
    }
  }

  /**
   * Вспомогательная функция - печать всех элементов списка от начала до конца.
   */
  private void print() {
    // Итерация элементов списка.
    iterate(new Command<E>() {
      public void execute(E element) {
        System.out.print(element + " --> ");
      }
    });
    System.out.println("null");
  }

  /**
   * Тестовая функция для проверки работоспособности операций.
   * @param args Не используется.
   */
  public static void main(String[] args) {
    // Создаем список
    List<String> testList = new List<String>();

    // Добавляем в него элементы.
    testList.insert("Дима");
    testList.insert("Вова");
    testList.insert("Валя");
    testList.insert("Юра");
    testList.insert("Денис");
    testList.insert("Виталик");
    testList.print();
 
    // Проверяем, есть ли в списке заданные элементы.
    String testString = "Юра";
    System.out.format("В списке %s %s\n",
        testList.has(testString) ? "есть элемент" : "нет элемента",
        testString);
    
    // Еще один способ поиска - с помощью внутреннего итератора
    System.out.format("В списке %s %s\n",
        testList.search(testString) ? "есть элемент" : "нет элемента",
        testString);

    testString = "Саша";
    System.out.format("В списке %s %s\n",
        testList.has(testString) ? "есть элемент" : "нет элемента",
        testString);
    
    // Удаляем из списка некоторые элементы.
    testList.remove("Юра");
    testList.remove("Саша");
    testList.remove("Вова");
    testList.print();
    System.out.format("В списке осталось %d элементов\n", testList.length());
  }

}
