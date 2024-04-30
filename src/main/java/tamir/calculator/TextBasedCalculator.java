package tamir.calculator;

import tamir.parser.assignment.AssignmentRootNode;

import java.util.Map;
import java.util.Scanner;

import static tamir.parser.AssignmentExpressionAbstractSyntaxTreeParser.parseAssignmentExpressionIntoAbstractSyntaxTree;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		CalculatorContext context = new CalculatorContext();
		String expression;
		while (!(expression = scanner.nextLine()).isBlank()) {
			processExpression(context, expression);
		}

		return context.getVariableToValue();
	}

	private static void processExpression(CalculatorContext context, String expression) {
		try {
			AssignmentRootNode assignmentRootNode = parseAssignmentExpressionIntoAbstractSyntaxTree(expression);
			assignmentRootNode.interpret(context);
		} catch (Exception e) {
			System.out.println("Failed processing expression: " + e.getMessage());
		}
	}
}
