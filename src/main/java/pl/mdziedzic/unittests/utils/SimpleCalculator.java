package pl.mdziedzic.unittests.utils;

public class SimpleCalculator {

	private int result;

	public SimpleCalculator() {
		result = 0;
	}

	public SimpleCalculator(int i) {
		result = i;
	}

	public void add(int i) {
		result += i;
	}

	public void subtract(int i) {
		result -= i;
	}

	public int getResult() {
		return result;
	}

}
