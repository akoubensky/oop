/**
 * Ячейка игры в калах.
 *
 */
public class Cell implements Cloneable {
  // Число камней в ячейке
  private int value = 6;
  
  @Override
  public Object clone() { 
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    } 
  }
  
  /**
   * Функция доступа: число камней в ячейке.
   * @return число камней в ячейке.
   */
  public int getValue() { return value; }
  
  /**
   * Опустошение ячейки.
   * @return число камней, которое было в ячейке.
   */
  public int empty() { int v = value; value = 0; return v; }
  
  /**
   * Положить камень в ячейку.
   */
  public int increase() { return ++value; }
  
  /**
   * Положить несколько камней в ячейку.
   * @param delta Число камней, которое кладется в ячейку.
   */
  public int add(int delta) { return value += delta; }
}
