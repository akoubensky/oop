/**
 * Реализация некоторых простых алгоритмов над простыми однонаправленными списками.
 * 
 * @param <E>	Тип элементов списка. Предполагается, что элементы списка можно сравнивать
 * 				(то есть тип элементов удовлетворяет интерфейсу <code>{@link java.util.Comparable}</code>) 
 */
public class AList<E extends Comparable<E>> {

	  /**
	   * Звено списка.
	   * 
	   * @param <T> Тип информационного поля звена списка.
	   */
	  private static class Link<T> {
	    T info;        			// Полезная информация.
	    Link<T> next = null;	// Ссылка на следующее звено.
	    
	    /**
	     * Конструктор
	     * 
	     * @param info Информационное поле.
	     * @param next Ссылка на следующее звено.
	     */
	    Link(T info, Link<T> next) {
	      this.info = info;
	      this.next = next;
	    }
	    
	    /**
	     * Конструктор последнего элемента (<code>next = <strong>null</strong></code>)
	     * 
	     * @param info Информационное поле.
	     */
	    Link(T info) {
	      this.info = info;
	    }
	  }

	  /**
	   * Голова списка - ссылка на первый элемент.
	   */
	  private Link<E> head = null;

	  /**
	   * Переворачивание списка без копирования звеньев
	   */
	  public void reverse() {
		  Link<E> newHead = null;
		  Link<E> current = head;
		  while (current != null) {
			  Link<E> next = current.next;
			  current.next = newHead;
			  newHead = current;
			  current = next;
		  }
		  head = newHead;
	  }
	  
	  /**
	   * Копирование звеньев списка с построением нового списка.
	   * 
	   * @return	Копия списка.
	   */
	  public AList<E> copy() {
		  AList<E> copy = new AList<E>();
		  Link<E> pred = null;
		  for (Link<E> current = head; current != null; current = current.next) {
			  Link<E> linkCopy = new Link<>(current.info);
			  if (pred == null) {
				  copy.head = linkCopy;
			  } else {
				  pred.next = linkCopy;
			  }
			  pred = linkCopy;
		  }
		  return copy;
	  }
	  
	  /**
	   * Слияние двух упорядоченных списков. Звенья списков не копируются, так что
	   * в результате работы исходные списки оказываются &quot;испорченными&quot;,
	   * и поэтому в конце работы объявляются пустыми.
	   * 
	   * @param list1	Первый список (предполагается упорядоченным по возрастанию)
	   * @param list2	Второй список (предполагается упорядоченным по возрастанию)
	   * @return		Упорядоченный объединенный список
	   */
	  public static <E extends Comparable<E>> AList<E> merge(AList<E> list1, AList<E> list2) {
		  AList<E> merged = new AList<>();	// Новый список
		  Link<E> pred = null;				// Последний элемент в новом списке
		  Link<E> current1 = list1.head;	// Очередной элемент в первом списке
		  Link<E> current2 = list2.head;	// Очередной элемент во втором списке
		  
		  // Цикл продолжается, пока есть элементы в обоих списках
		  while (current1 != null && current2 != null) {
			  // 1. Определяем, какое из звеньев надо присоединить в качестве очередного
			  Link<E> toInsert;
			  if (current1.info.compareTo(current2.info) < 0) {
				  toInsert = current1;
				  current1 = current1.next;
			  } else {
				  toInsert = current2;
				  current2 = current2.next;
			  }
			  // 2. Присоединяем очередное звено.
			  if (pred == null) {
				  merged.head = toInsert;
			  } else {
				  pred.next = toInsert;
			  }
			  // 3. Новое звено становится последним
			  pred = toInsert;
		  }
		  
		  // Если остались необработанные звенья, то их надо присоединить в хвост списка.
		  Link<E> rest = current1 == null ? current2 : current1;
		  if (pred == null) {
			  merged.head = rest;
		  } else {
			  pred.next = rest;
		  }
		  
		  // Опустошаем исходные "испорченные" списки.
		  list1.head = list2.head = null;
		  
		  return merged;
	  }
	  
	  /**
	   * Слияние двух упорядоченных списков с копированием элементов.
	   * 
	   * @param list1	Первый список (предполагается упорядоченным по возрастанию)
	   * @param list2	Второй список (предполагается упорядоченным по возрастанию)
	   * @return		Упорядоченный объединенный список
	   */
	  public static <E extends Comparable<E>> AList<E> mergeCopy(AList<E> list1, AList<E> list2) {
		  AList<E> merged = new AList<>();	// Новый список
		  Link<E> pred = null;				// Последний элемент в новом списке
		  Link<E> current1 = list1.head;	// Очередной элемент в первом списке
		  Link<E> current2 = list2.head;	// Очередной элемент во втором списке
		  
		  // Цикл продолжается, пока есть элементы в обоих списках
		  while (current1 != null && current2 != null) {
			  // 1. Определяем, какое из звеньев надо присоединить в качестве очередного, и копируем его.
			  Link<E> toInsert;
			  if (current1.info.compareTo(current2.info) < 0) {
				  toInsert = new Link<>(current1.info);
				  current1 = current1.next;
			  } else {
				  toInsert = new Link<>(current2.info);
				  current2 = current2.next;
			  }
			  // 2. Присоединяем очередное звено.
			  if (pred == null) {
				  merged.head = toInsert;
			  } else {
				  pred.next = toInsert;
			  }
			  // 3. Новое звено становится последним
			  pred = toInsert;
		  }
		  
		  // Если остались необработанные звенья, то их копии надо присоединить в хвост списка.
		  for (Link<E> rest = current1 == null ? current2 : current1; rest != null; rest = rest.next) {
			  Link<E> toInsert = new Link<E>(rest.info);
			  if (pred == null) {
				  merged.head = toInsert;
			  } else {
				  pred.next = toInsert;
			  }
			  pred = toInsert;
		  }
		  
		  return merged;
	  }
	  
	  /*
	   * Вывод элементов списка в виде массива
	   */
	  @Override
	  public String toString() {
		  StringBuilder builder = new StringBuilder("[");
		  boolean notEmpty = false;
		  for (Link<E> current = head; current != null; current = current.next) {
			  if (notEmpty) {
				  builder.append(", ");
			  }
			  builder.append(current.info);
			  notEmpty = true;
		  }
		  builder.append("]");
		  return builder.toString();
	  }
	  
	  /**
	   * Вспомогательная функция для создания списка из массива.
	   * 
	   * @param elements	Массив элементов, из которых формируется список.
	   */
	  private void create(E[] elements) {
		  head = null;
		  for (int i = elements.length-1; i >= 0; i--) {
			  head = new Link<E>(elements[i], head);
		  }
	  }
	  
	  /**
	   * Тестовая функция, проверяющая правильность работы реализованных алгоритмов.
	   * Ожидаемый вывод:<br><code>
	   * [1, 3, 7, 9, 13]<br>
	   * [2, 4, 6, 8, 9, 12]<br>
	   * [2, 4, 6, 8, 9, 12]<br>
	   * [12, 9, 8, 6, 4, 2]<br>
	   * [1, 2, 3, 4, 6, 7, 8, 9, 9, 12, 13]<br>
	   * [1, 3, 7, 9, 13]<br>
	   * [2, 4, 6, 8, 9, 12]<br>
	   * [1, 2, 3, 4, 6, 7, 8, 9, 9, 12, 13]<br>
	   * []<br>
	   * []</code>
	   * 
	   * @param args	не используется
	   */
	  public static void main(String[] args) {
		  AList<Integer> list1 = new AList<>();
		  list1.create(new Integer[] {1,3,7,9,13});
		  AList<Integer> list2 = new AList<>();
		  list2.create(new Integer[] {2,4,6,8,9,12});
		  System.out.println(list1);
		  System.out.println(list2);
		  AList<Integer> list2Copy = list2.copy();
		  System.out.println(list2Copy);
		  list2Copy.reverse();
		  System.out.println(list2Copy);
		  System.out.println(mergeCopy(list1, list2));
		  System.out.println(list1);
		  System.out.println(list2);
		  System.out.println(merge(list1, list2));
		  System.out.println(list1);
		  System.out.println(list2);
	  }

}
