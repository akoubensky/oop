/**
 * Author: Alexandre Koubenski
 *
 * Абстрактный стек, описанный в виде интерфейса
 *
 */
public interface Stack<T> {
    /**
     * Описание классов прерываний, могущих возникнуть при работе со стеком.
     * Underflow - исчерпание стека - попытка доступа к элементам пустого стека
     * Overflow - переполнение стека - попытка втолкнуть элемент в полностью заполненный стек
     */
    @SuppressWarnings("serial")
    static class Underflow extends Exception {
        public Underflow() { super("Stack underflow"); }
    }

    @SuppressWarnings("serial")
    static class Overflow extends RuntimeException {
        public Overflow() { super("Stack overflow"); }
    };

    /**
     * Вталкивание элемента в стек
     * @param element   - вталкиваемый элемент
     */
    T push(T element);

    /**
     * Выталкивание элемента из стека
     * @return   - выталкиваемый элемент
     * @throws Underflow  - исчерпание стека - попытка вытолкнуть элемент из пустого стека
     */
    T  pop() throws Underflow;

    /**
     * Взятие элемента с вершины стека
     * @return  - верхний элемент стека
     * @throws Underflow  - исчерпание стека - попытка доступа к несуществующему элементу
     */
    T  peek() throws Underflow;

    /**
     * Проверка стека на пустоту
     * @return  - true, если стек пуст, false - в противном случае
     */
    boolean isEmpty();
}
