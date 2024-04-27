package tamir.calculator;

import tamir.parser.ast.AbstractSyntaxTreeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static tamir.parser.AbstractSyntaxTreeParser.parseExpressionIntoAbstractSyntaxTree;

public class TextBasedCalculator {

	public static Map<String, Integer> runTextBasedCalculator(Scanner scanner) {
		System.out.println("Enter expressions line by line, enter blank line for exit");
		Map<String, Integer> variableToValues = new HashMap<>();
		String line;
		while (!(line = scanner.nextLine()).isBlank()) {
			AbstractSyntaxTreeNode abstractSyntaxTreeRootNode = parseExpressionIntoAbstractSyntaxTree(line);
			abstractSyntaxTreeRootNode.interpret();
		}

		return variableToValues;
	}
}
