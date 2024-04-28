package tamir;

import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.joining;
import static tamir.calculator.TextBasedCalculator.runTextBasedCalculator;

public class TextBasedCalculatorApplication {

	public static void main(String[] args) {
		System.out.println("--- Text Based Calculator Application ---");
		System.out.println("Enter expressions line by line, enter blank line for exit");
		Scanner scanner = new Scanner(System.in);
		Map<String, Integer> variableToValues = runTextBasedCalculator(scanner);
		System.out.println("(" + getVariableToValue(variableToValues) + ")");
	}

	private static String getVariableToValue(Map<String, Integer> variableToValues) {
		return variableToValues.entrySet().stream()
				.map(variableAndValue -> variableAndValue.getKey() + "=" + variableAndValue.getValue())
				.collect(joining(","));
	}
}
