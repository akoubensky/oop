import java.util.Calendar;

/**
 * ���������� ���������� ��� ������ � ������.
 */
public class DateImpl1 implements Date {
  //-----------------------------------------------------------------------
  // ���������� ��������� ������: ������ ������� � ����������� ���� ������.
  //-----------------------------------------------------------------------

  // ���������� ������������� �������� ��� �������� ��� ����:
  private int year_;     // ���,
  private Month month_;  // �����,
  private int day_;      // ���� ������.

  // ����� ������� ����. ���������� ��� ����������� ��������.
  private static int[] monthLength = {
    31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
  };

  // ����� ��� ���������������� �������� � �������, 1 ������ 1970 ����.
  // �� ����� ��� �� ����� ����������� ��� ���� ����.
  private static DateImpl1 newEra = new DateImpl1(1970, Month.JANUARY, 1);
  
  //-----------------------------------------------------------------------
  // ������������.
  //-----------------------------------------------------------------------

  /**
   * ����������� ���� �� �������� ����, ������ � ��� ������.
   * @param year ���.
   * @param month �����.
   * @param day ����.
   */
  public DateImpl1(int year, Month month, int day) {
    year_ = year; month_ = month; day_ = day;
  }
  
  /**
   * ����������� ���� �� �������� ������ ����.
   * @param date ����.
   */
  public DateImpl1(Date date) {
    this(date.getYear(), date.getMonth(), date.getDay());
  }

  //-----------------------------------------------------------------------
  // ��������������� ����������� ������� ��� ���������� ���.
  //-----------------------------------------------------------------------

  /**
   * ��������, �������� �� ��� ����������.
   * @param year ���.
   * @return true, ���� ��� ����������, false � ��������� ������.
   */
  private static boolean leap(int year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
  }

  /**
   * ����� ���� � �������� ������. ����� ���������� �� ��������
   * �� ������� monthLength � ���������� �������.
   * @param year ���.
   * @param month �����.
   * @return ���������� ���� ����� ������ � ������ ����.
   */
  private static int daysInMonth(int year, Month month) {
    int days = monthLength[month.ordinal()];
    return days + (days == 28 && leap(year) ? 1 : 0);
  }

  /**
   * ��������� ���������� �������.
   * ��������� � ��������� ������ �������� �����.
   * @param start ��������� �����.
   * @param number �������.
   * @return ���������� ����� (��������, ������� ����).
   */
  private static Month add(Month start, int number) {
    return Month.values()[(start.ordinal() + number) % 12];
  }
  
  //-----------------------------------------------------------------------
  // �������� ������������� ������ ������ � ������������ ����������.
  //-----------------------------------------------------------------------

  /**
   * �������� "�����������" ����.
   * @return ���� �������.
   */
  public static Date now() {
    // ������������� ������������ ����������
    // ��� ���������� ����������� ����.
    Calendar cal = Calendar.getInstance();
    return new DateImpl1(
        cal.get(Calendar.YEAR), 
        Month.values()[cal.get(Calendar.MONTH)], 
        cal.get(Calendar.DATE));
  }
 
  /**
   * ��������� �� ��������� (�� ������ Object).
   * @param obj ������, � ������� ���������� ���������.
   * @return ��������� ���������.
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Date ? equals((Date)obj) : false);
  }
  
  /**
   * ��������� �� ��������� � �������� �����.
   * @param date ����, � ������� ���������� ���������.
   * @return ��������� ���������.
   */
  public boolean equals(Date date) {
    return year_ == date.getYear() &&
           month_.equals(date.getMonth()) &&
           day_ == date.getDay();
  }

  /**
   * ���������� hash-���� (�� ������ Object).
   * @return hash-��� ����, ���������� �������� �����.
   */
  @Override
  public int hashCode() {
    return year_ * 37 + month_.ordinal() * 137 + day_ * 19;
  }

  /**
   * ����� ���� � ���� ������.
   * @return ������, �������������� ����.
   */
  @Override
  public String toString() {
    return String.format("%02d %s %04d, %s", 
        day_, month_.toString(), year_, getWeekday());
  }
  
  /**
   * ���������� ��������� ��� (��������� Comparable&lt;Date&gt;).
   * @param date ����, � ������� ������������ ���������.
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
   * ���������� ���: ������� ���� �������� ����� �������� ����� � "����".
   * @param date �������� ���� (����� �������).
   * @return ����� ����, ��������� �� date �� this.
   */
  @Override
  public int daysFrom(Date date) {
    // ������� ���������� ���������� ���� ��������������.
    int diffYears = year_ - date.getYear();
    int diffDays = diffYears * 365 + (diffYears / 4) - 
        diffYears / 100 + diffYears / 400 +
        30 * (month_.ordinal() - date.getMonth().ordinal()) +
        (day_ - date.getDay());
    // ������ �������� ����� ����, ������� �������
    // �� ������ � �������� �� ����������� ���������� ����.
    Date date1 = new DateImpl1(date);
    date1.shift(diffDays);
    // ���� ������ �� ����� �����, �� ������������� ���������.
    while (date1.compareTo(this) < 0) {
      date1.shift(1); diffDays++;
    }
    while (date1.compareTo(this) > 0) {
      date1.shift(-1); diffDays--;
    }
    return diffDays;
  }

  /**
   * ������� �������: ���� ������.
   * @return ���� ������.
   */
  @Override
  public int getDay() {
    return day_;
  }

  /**
   * ������� �������: �����.
   * @return ����� ����.
   */
  @Override
  public Month getMonth() {
    return month_;
  }

  /**
   * ������� �������: ���� ������. ���� ���� ����������� �����������
   * �� ������� ����� �������� ����� � ������� ����� ��� ����������������.
   * @return ���� ������.
   */
  @Override
  public Weekday getWeekday() {
    int wdNumber = (daysFrom(newEra) + 3) % 7;
    return Weekday.values()[wdNumber]; 
  }

  /**
   * ������� �������: ���.
   * @return ��� ����.
   */
  @Override
  public int getYear() {
    return year_;
  }

  /**
   * ������� ��������� �������� ���� �� ��������.
   * @param year ���.
   * @param month �����.
   * @param day ����.
   */
  @Override
  public Date set(int year, Month month, int day) {
    year_ = year; month_ = month; day_ = day;
    return this;
  }

  /**
   * ����� ���� �� �������� ����� ���� ������ ��� �����.
   * @param days ����� ���� ������. ���� ������������, �� ����
   *     ���������� ������, ���� ������������ - �����.
   * @return ��������� ���� (this).
   */
  @Override
  public Date shift(int days) {
    return days >= 0 ? add(days) : sub(-days);
  }
  
  /**
   * ��������������� �������.
   * ����� ���� �� �������� ����� ���� ������.
   * @param days ����� ���� ������.
   * @return ��������� ���� (this).
   */
  private Date add(int days) {
    // �������� ����, ��������� ����� �� �������.
    while (day_ + days > daysInMonth(year_, month_)) {
      // ��������� � ������� ����� ���������� ������.
      days -= daysInMonth(year_, month_) - day_ + 1;
      day_ = 1;
      month_ = add(month_, 1);
      // ��������, ��������� � ���������� ����.
      if (month_.equals(Month.JANUARY)) {
        year_++;
      }
    }
    day_ += days;
    return this;
  }
  
  /**
   * ��������������� �������.
   * ����� ���� �� �������� ����� ���� �����.
   * @param days ����� ���� ������.
   * @return ��������� ���� (this).
   */
  private Date sub(int days) {
    // �������� ����, ��������� ����� �� �������.
    while (days >= day_) {
      // ��������� � ���������� ����� ����������� ������.
      days -= day_;
      month_ = add(month_, 11);
      day_ = daysInMonth(year_, month_);
      // ��������, ��������� � ����������� ����.
      if (month_.equals(Month.DECEMBER)) year_--;
    }
    day_ -= days;
    return this;
  }

  /**
   * ����� ���� �� �������� ����� ���, ������� � ����.
   * ��������������, ��� ��� ���� �� ���������� ������� ����� �������
   * ������ ��� ����, �� �������� ��������������� ��������� ���������.
   * @param years ����� ��� ������.
   * @param months ����� ������� ������.
   * @param days ����� ���� ������.
   * @return ��������� ���� (this).
   */
  @Override
  public Date shift(int years, int months, int days) {
    // �������� ���.
    year_ += years;
    // �������� ������������ ������� ������, ����� �� ������� ����� ���.
    if (months < -month_.ordinal() || months >= 12 - month_.ordinal()) {
      throw new IllegalArgumentException();
    }
    month_ = add(month_, (months + 12) % 12);
    // �������� ������������ ������� ���, ����� �� ������� ����� �����.
    if (days <= -day_ || days > daysInMonth(year_, month_) - day_) {
      throw new IllegalArgumentException();
    }
    day_ += days;
    return this;
  }

  /**
   * �������� �������, ����������� ��������� ��������
   * ��������������� ������� ��� ������ � ������.
   * @param args �� ������������.
   */
  public static void main(String[] args) {
    // �������.
    Date now = Date.programStartDate;
    System.out.format("������� %s\n", now);
    
    // ����� ��� - 1 ������ �������� ����.
    Date newYear = new DateImpl1(now.getYear() + 1, Month.JANUARY, 1);
    
    // ������� ���� �������� �� ������ ����?
    int days = newYear.daysFrom(now);
    System.out.format("�� ������ ���� �������� %d ����.\n", days);
  }

}
