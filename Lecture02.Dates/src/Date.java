/**
 * ��������� ��� ������ � ������ ���������.
 */
public interface Date extends Comparable<Date> {
  // ���������� ���� �� ���������� ��� ������� ���� ������� ��������� � ����������.
  final static Date programStartDate = DateImpl1.now();
  
  /**
   * ������ ���������.
   */
  enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, 
    JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
  }
  
  /**
   * ��� ������ ���������.
   */
  enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
  }
  
  /**
   * ���� ����.
   * @return ���� � ��������� �� 1 �� ����� ���� � ������ ������.
   */
  int getDay();
  
  /**
   * ����� ����.
   * @return ����� � ��������� �� Month.JANUARY �� Month.DECEMBER.
   */
  Month getMonth();
  
  /**
   * ��� ����.
   * @return ������ ��� �� �������������� ���������.
   */
  int getYear();
  
  /**
   * ���� ������ ����.
   * @return ���� ������ � ��������� �� Weekday.MONDAY �� Weekday.SUNDAY.
   */
  Weekday getWeekday();
  
  /**
   * ��������� �������� ����.
   * @param year ���.
   * @param month �����.
   * @param day ����.
   * @return
   */
  Date set(int year, Month month, int day);
  
  /**
   * ���������� ���������� ����, ��������� �� �������� ����
   * ��� ���������� �� �������� ����.
   * @param date
   * @return
   */
  int daysFrom(Date date);
  Date shift(int days);
  Date shift(int years, int months, int days);
}
