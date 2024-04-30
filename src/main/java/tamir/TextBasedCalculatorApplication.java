package tamir;

import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.joining;
import static tamir.calculator.TextBasedCalculator.runTextBasedCalculator;

class TextBasedCalculatorApplication {

	public static void main(String[] args) {
		printInstructions();
		Scanner scanner = new Scanner(System.in);
		Map<String, Integer> variableToValues = runTextBasedCalculator(scanner);
		printOutput(variableToValues);
	}

	private static void printInstructions() {
		System.out.println("--- Text Based Calculator Application ---");
		System.out.println("Enter expressions line by line, enter blank line for exit");
	}

	private static void printOutput(Map<String, Integer> variableToValues) {
		System.out.println("(" + getVariableToValueAsString(variableToValues) + ")");
	}

	private static String getVariableToValueAsString(Map<String, Integer> variableToValues) {
		return variableToValues.entrySet().stream()
				.map(variableAndValue -> variableAndValue.getKey() + "=" + variableAndValue.getValue())
				.collect(joining(","));
	}
}
