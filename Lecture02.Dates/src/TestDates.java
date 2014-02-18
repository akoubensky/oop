import static org.junit.Assert.*;

import org.junit.Test;


public class TestDates {
	/**
	 * Проверяем правильность создания дат
	 */
	@Test
	public void testCreation() {
		Date dateJan1 = new DateImpl1(2014, Date.Month.JANUARY, 1);
		Date dateMar2 = new DateImpl1(2014, Date.Month.MARCH, 2);
		Date dateMar2Copy = new DateImpl1(dateMar2);
		
		assertEquals("Год выдается неправильно", 2014, dateJan1.getYear());
		assertEquals("Месяц выдается неправильно", Date.Month.JANUARY, dateJan1.getMonth());
		assertEquals("День выдается неправильно", 1, dateJan1.getDay());
		
		assertTrue("Копирование сделано неправильно",
				dateMar2Copy.getMonth() == Date.Month.MARCH &&
				dateMar2Copy.getDay() == 2);
	}
	
	/**
	 * Проверяем правильность операций
	 */
	@Test
	public void testOperations() {
		Date dateJan1 = new DateImpl1(2014, Date.Month.JANUARY, 1);
		Date dateMar2 = new DateImpl1();
		dateMar2.set(2014, Date.Month.MARCH, 2);

		assertTrue("Ошибка в вычислении дня недели",
				dateMar2.getWeekday() == Date.Weekday.SUNDAY);
		assertEquals("Ошибка в вычислении диапазона",
				dateMar2.daysFrom(dateJan1), 60);
		dateJan1.shift(60);
		assertEquals("Ошибка в вычислении сдвига",
				dateMar2, dateJan1);
		dateJan1.shift(0, -2, -1);
		assertEquals("Ошибка в вычислении другого сдвига",
				dateJan1, new DateImpl1(2014, Date.Month.JANUARY, 1));
	}
}
