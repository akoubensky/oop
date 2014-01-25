/**
 * Реализация кольцевого неупорядоченного списка.
 * @param <E> Тип элементов списка.
 */
public class CycList<E> {

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
   * Поиск элемента в списке.
   * @param element Искомый элемент.
   * @return true, если элемент есть в списке, false в противном случае.
   */
  public boolean has(E element) {
    if (tail == null) {
      // В пустом списке элементов нет.
      return false;
    }
    Link<E> current = tail;
    // Цикл поиска.
    do {
      current = current.next;
      if (element.equals(current.info)) {
        // Искомый элемент найден!
        return true;
      }
    } while (current != tail);
    // Прошли полный цикл по кольцу, а элемент так и не найден.
    return false;
  }

  /**
   * Вспомогательная функция вставки нового элемента после последнего.
   * @param element Информация для нового элемента.
   * @return Ссылка на вставленный элемент.
   */
  private Link<E> insert(E element) {
    // Создаем новый элемент.
    Link<E> newElement = new Link<E>(element, null);
    length++;
    // Вставляем его после последнего.
    if (tail == null) {
      tail = newElement;
      newElement.next = newElement;
    } else {
      newElement.next = tail.next;
    }
    // Возвращаем вставленный элемент.
    return tail.next = newElement;
  }

  /**
   * Вставка нового элемента в голову списка.
   * @param element Информация для вставки.
   */
  public void addHead(E element) {
    // Первый элемент вставляется после последнего.
    insert(element);
  }
  
  /**
   * Вставка нового элемента в хвост списка.
   * @param element Информация для вставки.
   */
  public void addTail(E element) {
    // Новый элемент вставляется после последнего и сам становится последним.
    tail = insert(element);
  }

  /**
   * Удаление элемента из головы списка.
   */
  public void removeHead() {
    // Удалять элемент имеет смысл только из непустого списка.
    if (tail != null) {
      length--;
      if (tail.next == tail) {
        // Это последний элемент в списке.
        tail.next = null;  // убираем ссылку на себя.
        tail = null; 
      } else {
        tail.next = tail.next.next;
      }
    }
  }
  
  /**
   * Вспомогательная функция - печать всех элементов списка от начала до конца.
   */
  private void print() {
    if (tail != null) {
      // Цикл по элементам списка.
      Link<E> current = tail;
      // Цикл поиска.
      do {
        current = current.next;
        System.out.print(current.info + " --> ");
      } while (current != tail);
    }
    System.out.println("null");
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // Создаем список
    CycList<String> testList = new CycList<String>();

    // Добавляем в него элементы.
    testList.addTail("Дима");
    testList.addHead("Вова");
    testList.addHead("Валя");
    testList.addTail("Юра");
    testList.addTail("Денис");
    testList.addHead("Виталик");
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
    testList.removeHead();
    testList.removeHead();
    testList.print();
    System.out.format("В списке осталось %d элементов\n", testList.length());
    
    // Удаляем оставшиеся элементы
    while (testList.length() > 0) {
      testList.removeHead();
    }
    testList.print();
    System.out.format("В списке осталось %d элементов\n", testList.length());
  }

}
