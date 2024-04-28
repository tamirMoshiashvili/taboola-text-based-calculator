package tamir.calculator;

import tamir.exception.UnknownVariableException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CalculatorContext {

	private final Map<String, Integer> variableToValue;

	public CalculatorContext() {
		this.variableToValue = new HashMap<>();
	}

	public void put(String variable, int value) {
		variableToValue.put(variable, value);
	}

	public int get(String variable) {
		return Optional.ofNullable(variableToValue.get(variable))
				.orElseThrow(() -> new UnknownVariableException(variable));
	}

	public Map<String, Integer> getVariableToValue() {
		return new HashMap<>(variableToValue);
	}
}
