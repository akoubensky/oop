/**
 * Задача Иосифа Флавия.
 * N человек стоят в круге. Они занумерованы номерами от 1 до N.
 * Каждый второй человек будет убит (мертвых немедленно убирают из круга).
 * Найти номер оставшегося в живых человека.
 * 
 * @author Alexander Kubenskiy
 *
 */
public class Josephus {
  /* Первый способ решения: моделирование.
   * Создаем круговой список номеров, и удаляем каждый второй элемент.
   */
  
  /**
   * Узел кольцевого списка, содержит номер солдата в качестве элемента.
   */
  private static class Node {
    int num;    // Номер солдата
    Node next;  // Ссылка назад
    
    // Конструктор
    public Node(int i) {
      num = i; next = null;
    }
  }
 
  /**
   * Кольцевой список узлов.
   */
  private static class Circle {
    Node first = null;  // Указатель на первый узел в списке.

    /**
     * Добавление элемента после текущего.
     * Добавленный элемент становится текущим.
     * 
     * @param n добавляемый номер.
     */
    public void addAfter(int n) {
      Node newNode = new Node(n);
      if (first == null) {
        first = newNode.next = newNode;
      } else {
        newNode.next = first.next;
        first.next = newNode;
      }
      first = newNode;
    }

    /**
     * Сдвиг указателя текущего элемента вперед на один элемент.
     */
    public void shift() {
      if (first != null) {
        first = first.next;
      }
    }
 
    /**
     * Функция проверки пустоты списка.
     * @return true, если список пуст, false в противном случае.
     */
    public boolean isEmpty() { return first == null; }
    
    /**
     * Функция проверки находится ли в списке ровно один элемент.
     * @return true, если в списке один элемент, false в противном случае.
     */
    public boolean isSingle() { return first != null && first.next == first; }

    /**
     * Удаление элемента, следующего за текущим;
     * новым текущим становится следущий элемент за удаленным.
     */
    public void kill() {
      if (first != null && first.next != first) {
        first.next = first.next.next;
        first = first.next;
      }
    }
  }
 
  /**
   * Основная функция создает круговой список номеров и моделирует удаление
   * каждого второго элемента, пока в списке не останется один элемент.
   *  
   * @param n общее число солдат (полагаем n >= 1).
   * @return номер оставшегося в живых солдата.
   */
  public static int Josephus1(int n) {
    assert n > 0;
    Circle people = new Circle();  // круговой список солдат
    // Создание начального списка
    for (int i = 1; i <= n; ++i) {
      people.addAfter(i);
    }
    
    // Сдвинемся к первому солдату.
    people.shift();
    // Убиваем каждого второго, пока не останется один солдат.
    while (!people.isSingle()) {
      people.kill();
    }
    // Выдаем номер оставшегося в живых.
    return people.first.num;
  }
  
  /**
   * Второй способ решения: находим рекуррентную формулу и реализуем ее.
   */
 
  /**
   * Реализация рекуррентной формулы:
   * J(n) = (J(n-1)+1) % n + 1;
   * 
   * @param n общее число солдат (полагаем n >= 1).
   * @return номер оставшегося в живых солдата.
    */
  public static int Josephus2(int n) {
    assert n > 0;
    return n == 1 ? 1 : (Josephus2(n-1) + 1) % n + 1;
  }
  
  /**
   * Реализация более быстрой рекуррентной формулы:
   * J(2n) = 2*J(n)-1;
   * J(2n+1) = 2*J(n)+1.
   * 
   * @param n общее число солдат (полагаем n >= 1).
   * @return номер оставшегося в живых солдата.
    */
  public static int Josephus3(int n) {
    assert n > 0;
    if (n == 1) return 1;
    if ((n & 1) == 0) {
      return 2*Josephus3(n/2)-1;
    } else {
      return 2*Josephus3(n/2)+1;
    }
  }
  
  /**
   * Решение, использующее битовую арифметику. Находим старший ненулевой бит
   * в числе N и переставляем его в конец.
   */

  /**
   * Функция реализует это решение
   * @param n общее число солдат (полагаем n >= 1).
   * @return номер оставшегося в живых солдата.
   */
  public static int Josephus4(int n) {
    assert(n > 0);
    // Маска содержит единицу в старшем разряде числа n.
    int mask = Integer.highestOneBit(n);
    // Переносим этот разряд в конец числа.
    return ((n - mask) << 1) | 1;
  }
  
  public static void main(String[] args) {
    System.out.println("Josephus1: " + Josephus1(41));
    System.out.println("Josephus2: " + Josephus2(41));
    System.out.println("Josephus3: " + Josephus3(41));
    System.out.println("Josephus4: " + Josephus4(41));
  }
}
