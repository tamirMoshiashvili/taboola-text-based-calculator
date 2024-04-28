package tamir.calculator;

import tamir.parser.assignment.AssignmentRootNode;

import java.util.Map;
import java.util.Scanner;

import static tamir.parser.AbstractSyntaxTreeParser.parseExpressionIntoAbstractSyntaxTree;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		CalculatorContext context = new CalculatorContext();
		String line;
		while (!(line = scanner.nextLine()).isBlank()) {
			AssignmentRootNode abstractSyntaxTreeRootNode = parseExpressionIntoAbstractSyntaxTree(line);
			abstractSyntaxTreeRootNode.execute(context);
		}

		return context.getVariableToValue();
	}
}
