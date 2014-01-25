/**
 * Реализация двусвязного упорядоченного списка.
 * @param <E> Тип элементов списка.
 */
public class BiList<E extends Comparable<E>> {
  /**
   * Звено списка.
   * @param <T> Тип информационного поля звена списка.
   */
  private static class Link<T> {
    T info;        // Полезная информация.
    Link<T> pred;  // Ссылка на предыдущий элемент.
    Link<T> next;  // Ссылка на следующий элемент.
    
    /**
     * Конструктор
     * @param info Информационное поле.
     * @param pred Ссылка на предыдущее звено.
     * @param next Ссылка на следующее звено.
     */
    public Link(T info, Link<T> pred, Link<T> next) {
      this.info = info;
      this.pred = pred;
      this.next = next;
    }
  }
  
  /**
   * Голова списка - ссылка на первый элемент.
   */
  private Link<E> head = null;
  
  /**
   * Хвост списка - ссылка на последний элемент.
   */
  private Link<E> tail = null;
  
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
    // Цикл поиска первого звена в списке, содержащего большее или равное значение.
    while (current != null && element.compareTo(current.info) > 0) {
      current = current.next;
    }
    // Предыдущий элемент вычисляем по следующему.
    Link<E> pred = tail;
    if (current != null) pred = current.pred;
    // Создаем новый элемент.
    Link<E> newLink = new Link<E>(element, pred, current);
    length++;
    // Вставляем новый элемент на свое место в списке - перед следующим, после предыдущего.
    if (pred == null) {
      head = newLink;
    } else {
      pred.next = newLink;
    }
    if (current == null) {
      tail = newLink;
    } else {
      current.pred = newLink;
    }
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
  
  /**
   * Удаление элемента из списка, если он там существовал.
   * @param element Удаляемый элемент.
   * @return true, если элемент был удален, false, если его в списке не было.
   */
  public boolean remove(E element) {
    Link<E> current = head;  // Ссылка для поиска удаляемого элемента.
    // Цикл поиска удаляемого элемента.
    while (current != null && element.compareTo(current.info) > 0) {
      current = current.next;
    }
    // Нашли?
    boolean toRemove = current != null && element.equals(current.info);
    if (toRemove) {
      // Вычисляем следующий и предыдущий элементы по найденному.
      Link<E> pred = current.pred;
      Link<E> next = current.next;
      // Удаление звена из списка.
      length--;
      if (pred == null) {
        head = next;
      } else {
        pred.next = next;
      }
      if (next == null) {
        tail = pred;
      } else {
        next.pred = pred;
      }
    }
    // Возврат результата.
    return toRemove;
  }
  
  /**
   * Вспомогательная функция - печать всех элементов списка от начала до конца.
   */
  private void print() {
    // Цикл по элементам списка.
    for (Link<E> current = head; current != null; current = current.next) {
      System.out.print(current.info + " --> ");
    }
    System.out.println("null");
  }

  
  /**
   * Тестовая функция для проверки работоспособности операций.
   * @param args Не используется.
   */
  public static void main(String[] args) {
    // Создаем список
    BiList<String> testList = new BiList<String>();

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
