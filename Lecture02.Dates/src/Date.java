/**
 * Интерфейс для работы с датами календаря.
 */
public interface Date extends Comparable<Date> {
  // Используем одну из реализаций для задания даты первого обращения к интерфейсу.
  final static Date programStartDate = DateImpl1.now();
  
  /**
   * Месяцы календаря.
   */
  enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, 
    JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
  }
  
  /**
   * Дни недели календаря.
   */
  enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
  }
  
  /**
   * День даты.
   * @return День в диапазоне от 1 до числа дней в данном месяце.
   */
  int getDay();
  
  /**
   * Месяц даты.
   * @return Месяц в диапазоне от Month.JANUARY до Month.DECEMBER.
   */
  Month getMonth();
  
  /**
   * Год даты.
   * @return Полный год по Григорианскому календарю.
   */
  int getYear();
  
  /**
   * День недели даты.
   * @return День недели в диапазоне от Weekday.MONDAY до Weekday.SUNDAY.
   */
  Weekday getWeekday();
  
  /**
   * Установка заданной даты.
   * @param year Год.
   * @param month Месяц.
   * @param day День.
   * @return
   */
  Date set(int year, Month month, int day);
  
  /**
   * Вычисление количества дней, прошедших от заданной даты
   * или оставшихся до заданной даты.
   * @param date
   * @return
   */
  int daysFrom(Date date);
  Date shift(int days);
  Date shift(int years, int months, int days);
}
