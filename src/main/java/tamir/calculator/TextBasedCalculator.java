package tamir.calculator;

import tamir.token.ParsedAssignmentExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static tamir.token.AssignmentExpressionTokenizer.parseAssignmentExpressionIntoTokens;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		System.out.println("Enter expressions line by line, enter blank line for exit");
		Map<String, Integer> variableToValues = new HashMap<>();
		String line;
		while (!(line = scanner.nextLine()).isBlank()) {
			ParsedAssignmentExpression parsedAssignmentExpression = parseAssignmentExpressionIntoTokens(line);
		}

		return variableToValues;
	}
}
