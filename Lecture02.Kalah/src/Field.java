/**
 * Поле для игры в калах состоит из 13 ячеек, расположенных по кольцу.
 * См., например, http://ru.wikipedia.org/wiki/%D0%9A%D0%B0%D0%BB%D0%B0%D1%85.
 * Данный класс определяет правила игры в калах.
 */
public class Field implements Cloneable {
  private static final int YOUR_KALAH = 13;
  private static final int MY_KALAH = 6;
  private static final int FIELDS_NUMBER = 14;
  
  // Массив ячеек игрового поля.
  private Cell[] cells = new Cell[FIELDS_NUMBER];

  /**
   * Конструктор создает начальную конфигурацию.
   */
  public Field() { 
    for (int i = 0; i < FIELDS_NUMBER; ++i) {
      cells[i] = new Cell();
    }
    cells[MY_KALAH].empty();
    cells[YOUR_KALAH].empty();
  }
  
  private int oppositeCell(int cell) { return 12 - cell; }
  private int oppositeKalah(int kalah) { return 19 - kalah; }
  private boolean sameSide(int cell1, int cell2) { 
    return cell1 / 7 == cell2 / 7;
  }
  
  @Override
  public Object clone() {
    try {
      Field newField = (Field) super.clone();
      newField.cells = new Cell[FIELDS_NUMBER];
      for (int i = 0; i < FIELDS_NUMBER; ++i) {
        newField.cells[i] = (Cell)cells[i].clone();
      }
      return newField;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  /**
   * Запрос количества камней в лунке с заданным номером.
   * @param index Номер лунки.
   * @return Число камней в лунке.
   */
  public int getValue(int index) { return cells[index].getValue(); } 
 
  /**
   * Ход из лунки с заданным номером.
   * @param start Начальный номер ячейки.
   * @return true, если последний камень попадает в свой калах, false в
   *     противном случае.
   */
  public boolean move(int start) {
    int index = start;  // Номер ячейки, куда падает очередной камень.
    int stones = cells[start].empty();  // Сколько осталось разложить камней.
    int miss = start < MY_KALAH ? YOUR_KALAH : MY_KALAH;  // Эта ячейка пропускается (чужой калах).
    
    // Раскладываем камни.
    while (stones-- != 0) {
      // Вычисляем номер очередной ячейки.
      if (++index == miss) index++;
      if (index == FIELDS_NUMBER) index = 0;
      // Кладем камень в ячейку.
      cells[index].increase();
    }
    
    // Проверяем, не попал ли последний камень в свой калах.
    if (index == MY_KALAH || index == YOUR_KALAH) return true;
    
    // Проверяем, не попал ли последний камень в свою пустую ячейку.
    if (cells[index].getValue() == 1 &&
        sameSide(start, index) && 
        cells[oppositeCell(index)].getValue() != 0) {
      int add = cells[index].empty() + cells[oppositeCell(index)].empty();
      cells[oppositeKalah(miss)].add(add);
    }
    
    return false;
  }

  /**
   * Функция проверяет, не надо ли заканчивать игру, и если надо, то
   * осуществляет финишную расладку камней.
   * @return True, если игра закончена, false в противном случае.
   */
  public boolean finish() {
    // Сумма числа камней на нижнем поле
    int sLow = 0;
    for (int i = 0; i < MY_KALAH; ++i) {
      sLow += cells[i].getValue();
    }
    // Сумма числа камней на верхнем поле.
    int sHigh = 0;
    for (int i = MY_KALAH + 1; i < YOUR_KALAH; ++i) {
      sHigh += cells[i].getValue();
    }
    
    if (sLow == 0) {
      // Складываем все камни из верхнего поля в калах.
      cells[YOUR_KALAH].add(sHigh);
      for (int i = MY_KALAH + 1; i < YOUR_KALAH; ++i) cells[i].empty();
      return true; 
    }
    if (sHigh == 0) { 
      // Складываем все камни из нижнего поля в калах.
      cells[MY_KALAH].add(sLow); 
      for (int i = 0; i < MY_KALAH; ++i) cells[i].empty();
      return true; 
    }
    
    return false;
  }
 
  /**
   * Вывод состояния поля в консоль.
   */
  public void print() {
    for (int i = YOUR_KALAH; i > MY_KALAH; i--) {
      System.out.format(" %2d ", cells[i].getValue());
    }
    System.out.println();
    System.out.print("    ");
    for (int i = 0; i < MY_KALAH + 1; i++) {
      System.out.format(" %2d ", cells[i].getValue());
    }
    System.out.println();
    System.out.println();
  }
}
