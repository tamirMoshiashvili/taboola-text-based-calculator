package tamir.calculator;

import tamir.parser.assignment.AssignmentAstNode;

import java.util.Map;
import java.util.Scanner;

import static tamir.parser.AbstractSyntaxTreeParser.parseExpressionIntoAbstractSyntaxTree;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		System.out.println("Enter expressions line by line, enter blank line for exit");
		CalculatorContext context = new CalculatorContext();
		String line;
		while (!(line = scanner.nextLine()).isBlank()) {
			AssignmentAstNode abstractSyntaxTreeRootNode = parseExpressionIntoAbstractSyntaxTree(line);
			abstractSyntaxTreeRootNode.execute(context);
		}

		return context.getVariableToValue();
	}
}
