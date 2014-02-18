import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class TestComplex {
	//====================== Тесты =========================

	/**
	 * Проверка сложения, умножения, вычитания, деления
	 */
	@Test
	public void test00() {
		Complex i1 = new Complex(1.5, 2.5).add(new Complex(0.8, 1.3));
		assertTrue("Сложение не работает",
				Math.abs(i1.re() - 2.3) < 0.000001 &&
				Math.abs(i1.im() - 3.8) < 0.000001);
		Complex i2 = Complex.i.mult(Complex.i);
		assertEquals("Квадрат мнимой единицы не есть -1", i2, new Complex(-1));
		Complex i3 = new Complex(1.5, 2.5).sub(new Complex(0.8, 1.3));
		assertTrue("Вычитание не работает",
				Math.abs(i3.re() - 0.7) < 0.000001 &&
				Math.abs(i3.im() - 1.2) < 0.000001);
		Complex i4 = new Complex(1.5, 2.4).div(new Complex(3, 0));
		assertTrue("Деление не работает",
				Math.abs(i4.re() - 0.5) < 0.000001 &&
				Math.abs(i4.im() - 0.8) < 0.000001);
	}

	/**
	 * Проверка вычисления модуля и аргумента
	 */
	@Test
	public void test01() {
		Complex c = new Complex(1, 1);
		assertTrue("Ошибка при вычислении модуля",
				Math.abs(c.mod() - Math.sqrt(2)) < 0.000001);
		assertTrue("Ошибка при вычислении аргумента",
				Math.abs(c.arg() - Math.PI/4) < 0.000001);
	}
}
