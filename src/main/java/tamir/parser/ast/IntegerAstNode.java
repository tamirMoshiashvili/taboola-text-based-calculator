package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class IntegerAstNode implements AbstractSyntaxTreeNode {

	private final int value;

	@Override
	public int interpret(CalculatorContext context) {
		return value;
	}
}
