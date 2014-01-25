import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Стратегии для игры в калах.
 * Здесь представлена "нулевая" стратегия - просто диалог, в котором стратегию
 * определяют игроки, и стратегия вычисления лучшего хода при расчете на
 * заданную глубину.
 */
public class Game {
  
  static Scanner scanner = new Scanner(System.in);
  
  /**
   * Эта функция осуществляет диалог с двумя игроками, играющими в калах.
   */
  public static void dialog() {
    // Поле для игры в калах.
    Field field = new Field();
    field.print();
    
    // Очередь хода: 0 - нижний игрок, 1 - верхний игрок.
    int turn = 0;
    System.out.println("Играем в калах!");
    while(!field.finish()) {
      // Запрашиваем номер ячейки.
      System.out.format("%s игрок (введите номер от 1 до 6): ", turn == 0 ? "Нижний" : "Верхний");
      int start = 0;
      try {
        start = scanner.nextInt();
      } catch (InputMismatchException ex) {
        // Ошибка при вводе. Повторяем запрос.
      }
      // Проверяем правильность введенной информации.
      if (start >= 1 && start <= 6) {
        start = (start - 1) + 7 * turn;
        if (field.getValue(start) > 0) {
          if (!field.move(start)) {
            // Меняем очередь хода, если нужно.
            turn = 1 - turn;
          }
          // Печатаем позицию после сделанного хода.
          field.print();
        }
      }
    }
    // Печатаем заключительную позицию.
    field.print();
  }

  /**
   * Структура, содержащая номер (лучшего) хода и соответствующую
   * ему оценку позиции.
   */
  private static class Estimation {
    int move;        // Номер хода.
    int estimation;  // Оценка позиции.
  }

  /**
   * Функция вычисления лучшего хода из данной позиции.
   * @param field Начальная позиция.
   * @param turn Очередь хода (0 - ход человека, 1 - ход компьютера).
   * @param depth Глубина расчетов позиции.
   * @return Лучший ход и его оценка.
   */
  private static Estimation bestMove(Field field, int turn, int depth) {
    Estimation result = new Estimation();  // Сюда будем записывать результат.
  
    if (field.finish() || depth == 0) {
      // Расчет окончен. Ход не важен, а оценка равна разности числа камней в калахах.
      result.estimation = field.getValue(6 + 7*turn) - field.getValue(13 - 7*turn);
    } else {
      // Вычисляем лучший ход.
      int bestEstimation = Integer.MIN_VALUE;
      int bestMove = 0;
      // Перебираем все возможные ходы.
      for (int i = 7 * turn; i < 7 * turn + 6; ++i) {
        if (field.getValue(i) > 0) {
          // Копируем поле, чтобы на нем сделать ход.
          Field newField = (Field)field.clone();
          Estimation newEst;
          // Делаем ход.
          if (newField.move(i)) {
            // Нет перехода очереди хода.
            newEst = bestMove(newField, turn, depth-1);
          } else {
            // Очередь хода переходит к сопернику.
            newEst = bestMove(newField, 1-turn, depth-1);
            newEst.estimation = -newEst.estimation;
          }
          // Выбираем лучшую оценку.
          if (newEst.estimation > bestEstimation) {
            bestEstimation = newEst.estimation;
            bestMove = i;
          }
        }
      }
      // Запоминаем и выдаем лучшую оценку.
      result.move = bestMove;
      result.estimation = bestEstimation;
    }
    return result;
  }

  /**
   * Функция, осуществляющая игру в калах с соперником-человеком.
   * Ведет расчет лучшего хода на некоторую заданную глубину.
   */
  public static void strategy() {
    // Глубина расчета хода.
    final int DEPTH = 5;
    
    // Поле для игры в калах.
    Field field = new Field();
    field.print();
    
    System.out.println("Играем в калах!");
    while(!field.finish()) {
      // Запрашиваем номер ячейки.
      System.out.print("Введите номер от 1 до 6: ");
      int start = 0;
      try {
        start = scanner.nextInt();
      } catch (InputMismatchException ex) {
        // Ошибка при вводе. Повторяем запрос.
      }
      // Проверяем правильность введенной информации.
      if (start >= 1 && start <= 6) {
        if (field.getValue(--start) > 0) {
          // Человек делает ход.
          boolean humanMove = field.move(start);
          field.print();
          if (!humanMove) {
            // Теперь моя очередь хода.
            while (!field.finish()) {
              // Вычисляем лучший ход...
              Estimation est = bestMove(field, 1, DEPTH);
              // ...и делаем его.
              boolean myMove = field.move(est.move);
              System.out.format("Мой ход: %d\n", est.move - 6);
              field.print();
              // Передаем ход человеку?
              if (!myMove) break;
            }
          }
        }
      }
    }
    // Печатаем заключительную позицию.
    field.print();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    //dialog();
    strategy();
  }

}
