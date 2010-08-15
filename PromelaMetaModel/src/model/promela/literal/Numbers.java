package model.promela.literal;

import java.util.ArrayList;
import java.util.List;

import exceptions.RuleViolationException;

import model.promela.IConst;

public class Numbers extends IConst {
	private Integer numbers;

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public String toCode() throws RuleViolationException {
		if (numbers == null)
			throw new RuleViolationException("Numbers must not be null");
		return numbers.toString();
	}

	// ----------------------------------------------------------------
	/*
	 * at least once.
	 */
	public List<Number> numbers_ = new ArrayList<Number>();

	public void setNumbers(List<Number> numbers) {
		this.numbers_ = numbers;
	}

	public Numbers(List<Number> numbers) {
		super();
		this.numbers_ = numbers;
	}

	// append a number into numbers
	public void appendNumber(Number number) {
		numbers_.add(number);
	}

	public String toCode_() throws Exception {
		String ret = "";
		int size = numbers_.size();
		// must contain at least 1 number.
		if (size == 0)
			throw new Exception();
		for (int i = 0; i < size; i++) {
			ret = ret + numbers_.get(i).toCode();
		}
		return ret;
	}
}
