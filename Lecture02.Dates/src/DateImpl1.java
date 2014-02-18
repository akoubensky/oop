import java.util.Calendar;

/**
 * Реализация интерфейса для работы с датами.
 */
public class DateImpl1 implements Date {
  //-----------------------------------------------------------------------
  // Внутренние приватные данные: данные объекта и статические поля класса.
  //-----------------------------------------------------------------------

  // Внутреннее представление объектов дат содержит три поля:
  private int year_;     // год,
  private Month month_;  // месяц,
  private int day_;      // день месяца.

  // Длины месяцев года. Високосный год учитывается отдельно.
  private static int[] monthLength = {
    31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
  };

  // Новая эра программирования началась в четверг, 1 января 1970 года.
  // От этого дня мы будем отсчитывать все наши даты.
  private static DateImpl1 newEra = new DateImpl1(1970, Month.JANUARY, 1);
  
  //-----------------------------------------------------------------------
  // Конструкторы.
  //-----------------------------------------------------------------------

  /**
   * Конструктор даты по заданным году, месяцу и дню месяца.
   * @param year Год.
   * @param month Месяц.
   * @param day День.
   */
  public DateImpl1(int year, Month month, int day) {
    year_ = year; month_ = month; day_ = day;
  }
  
  /**
   * Конструктор даты по заданной другой дате.
   * @param date Дата.
   */
  public DateImpl1(Date date) {
    this(date.getYear(), date.getMonth(), date.getDay());
  }

  //-----------------------------------------------------------------------
  // Вспомогательные статические функции для арифметики дат.
  //-----------------------------------------------------------------------

  /**
   * Проверка, является ли год високосным.
   * @param year Год.
   * @return true, если год високосный, false в противном случае.
   */
  private static boolean leap(int year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
  }

  /**
   * Число дней в заданном месяце. Может отличаться от значения
   * из массива monthLength в високосном феврале.
   * @param year Год.
   * @param month Месяц.
   * @return Количество дней этого месяца в данном году.
   */
  private static int daysInMonth(int year, Month month) {
    int days = monthLength[month.ordinal()];
    return days + (days == 28 && leap(year) ? 1 : 0);
  }

  /**
   * Модульная арифметика месяцев.
   * Добавляет к заданному месяцу заданное число.
   * @param start Начальный месяц.
   * @param number Добавка.
   * @return Полученный месяц (возможно, другого года).
   */
  private static Month add(Month start, int number) {
    return Month.values()[(start.ordinal() + number) % 12];
  }
  
  //-----------------------------------------------------------------------
  // Основные общедоступные методы класса и реализуемого интерфейса.
  //-----------------------------------------------------------------------

  /**
   * Создание "сегодняшней" даты.
   * @return Дата сегодня.
   */
  public static Date now() {
    // Воспользуемся библиотечным календарем
    // для вычисления сегодняшней даты.
    Calendar cal = Calendar.getInstance();
    return new DateImpl1(
        cal.get(Calendar.YEAR), 
        Month.values()[cal.get(Calendar.MONTH)], 
        cal.get(Calendar.DATE));
  }
 
  /**
   * Сравнение на равенство (из класса Object).
   * @param obj Объект, с которым происходит сравнение.
   * @return Результат сравнения.
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Date ? equals((Date)obj) : false);
  }
  
  /**
   * Сравнение на равенство с заданной датой.
   * @param date Дата, с которой происходит сравнение.
   * @return Результат сравнения.
   */
  public boolean equals(Date date) {
    return year_ == date.getYear() &&
           month_.equals(date.getMonth()) &&
           day_ == date.getDay();
  }

  /**
   * Вычисление hash-кода (из класса Object).
   * @return hash-код даты, комбинация значений полей.
   */
  @Override
  public int hashCode() {
    return year_ * 37 + month_.ordinal() * 137 + day_ * 19;
  }

  /**
   * Вывод даты в виде строки.
   * @return Строка, представляющая дату.
   */
  @Override
  public String toString() {
    return String.format("%02d %s %04d, %s", 
        day_, month_.toString(), year_, getWeekday());
  }
  
  /**
   * Реализация сравнения дат (интерфейс Comparable&lt;Date&gt;).
   * @param date Дата, с которой производится сравнение.
   */
  @Override
  public int compareTo(Date date) {
    if (year_ != date.getYear()) {
      return year_ - date.getYear();
    }
    if (!month_.equals(date.getMonth())) {
      return month_.ordinal() - date.getMonth().ordinal();
    }
    return day_ - date.getDay();
  }

  /**
   * Арифметика дат: сколько дней проходит между заданной датой и "этой".
   * @param date Исходная дата (точка отсчета).
   * @return Число дней, прошедших от date до this.
   */
  @Override
  public int daysFrom(Date date) {
    // Сначала подсчитаем количество дней приблизительно.
    int diffYears = year_ - date.getYear();
    int diffDays = diffYears * 365 + (diffYears / 4) - 
        diffYears / 100 + diffYears / 400 +
        30 * (month_.ordinal() - date.getMonth().ordinal()) +
        (day_ - date.getDay());
    // Теперь создадим новую дату, которая отстоит
    // от данной в точности на вычисленное количество дней.
    Date date1 = new DateImpl1(date);
    date1.shift(diffDays);
    // Если попали не очень точно, то скорректируем результат.
    while (date1.compareTo(this) < 0) {
      date1.shift(1); diffDays++;
    }
    while (date1.compareTo(this) > 0) {
      date1.shift(-1); diffDays--;
    }
    return diffDays;
  }

  /**
   * Функция доступа: день месяца.
   * @return День месяца.
   */
  @Override
  public int getDay() {
    return day_;
  }

  /**
   * Функция доступа: месяц.
   * @return Месяц даты.
   */
  @Override
  public Month getMonth() {
    return month_;
  }

  /**
   * Функция доступа: день недели. Этот день вычисляется динамически
   * по разнице между заданной датой и началом новой эры программирования.
   * @return День недели.
   */
  @Override
  public Weekday getWeekday() {
    int wdNumber = (daysFrom(newEra) + 3) % 7;
    return Weekday.values()[wdNumber]; 
  }

  /**
   * Функция доступа: год.
   * @return Год даты.
   */
  @Override
  public int getYear() {
    return year_;
  }

  /**
   * Функция изменения значения даты на заданную.
   * @param year Год.
   * @param month Месяц.
   * @param day День.
   */
  @Override
  public Date set(int year, Month month, int day) {
    year_ = year; month_ = month; day_ = day;
    return this;
  }

  /**
   * Сдвиг даты на заданное число дней вперед или назад.
   * @param days Число дней сдвига. Если положительно, то дата
   *     сдвигается вперед, если отрицательно - назад.
   * @return сдвинутая дата (this).
   */
  @Override
  public Date shift(int days) {
    return days >= 0 ? add(days) : sub(-days);
  }
  
  /**
   * Вспомогательная функция.
   * Сдвиг даты на заданное число дней вперед.
   * @param days Число дней сдвига.
   * @return сдвинутая дата (this).
   */
  private Date add(int days) {
    // Сдвигаем дату, аккуратно шагая по месяцам.
    while (day_ + days > daysInMonth(year_, month_)) {
      // Переходим к первому числу следующего месяца.
      days -= daysInMonth(year_, month_) - day_ + 1;
      day_ = 1;
      month_ = add(month_, 1);
      // Возможно, переходим к следующему году.
      if (month_.equals(Month.JANUARY)) {
        year_++;
      }
    }
    day_ += days;
    return this;
  }
  
  /**
   * Вспомогательная функция.
   * Сдвиг даты на заданное число дней назад.
   * @param days Число дней сдвига.
   * @return сдвинутая дата (this).
   */
  private Date sub(int days) {
    // Сдвигаем дату, аккуратно шагая по месяцам.
    while (days >= day_) {
      // Переходим к последнему числу предыдущего месяца.
      days -= day_;
      month_ = add(month_, 11);
      day_ = daysInMonth(year_, month_);
      // Возможно, переходим к предыдущему году.
      if (month_.equals(Month.DECEMBER)) year_--;
    }
    day_ -= days;
    return this;
  }

  /**
   * Сдвиг даты на заданное число лет, месяцев и дней.
   * Предполагается, что при этом не происходит переход через границы
   * месяца или года, не заданные соответствующим значением аргумента.
   * @param years Число лет сдвига.
   * @param months Число месяцев сдвига.
   * @param days Число дней сдвига.
   * @return сдвинутая дата (this).
   */
  @Override
  public Date shift(int years, int months, int days) {
    // Сдвигаем год.
    year_ += years;
    // Проверим правильность запроса месяца, чтобы не перейти через год.
    if (months < -month_.ordinal() || months >= 12 - month_.ordinal()) {
      throw new IllegalArgumentException();
    }
    month_ = add(month_, (months + 12) % 12);
    // Проверим правильность запроса дня, чтобы не перейти через месяц.
    if (days <= -day_ || days > daysInMonth(year_, month_) - day_) {
      throw new IllegalArgumentException();
    }
    day_ += days;
    return this;
  }

  /**
   * Тестовая функция, проверяющая несколько наиболее
   * употребительных функций для работы с датами.
   * @param args Не используется.
   */
  public static void main(String[] args) {
    // Сегодня.
    Date now = Date.programStartDate;
    System.out.format("Сегодня %s\n", now);
    
    // Новый год - 1 января будущего года.
    Date newYear = new DateImpl1(now.getYear() + 1, Month.JANUARY, 1);
    
    // Сколько дней осталось до Нового года?
    int days = newYear.daysFrom(now);
    System.out.format("До нового года осталось %d дней.\n", days);
  }

}
