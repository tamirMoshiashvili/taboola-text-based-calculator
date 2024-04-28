package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
@EqualsAndHashCode
public class IntegerAstNode implements AbstractSyntaxTreeNode {

	private final int value;

	@Override
	public int interpret(CalculatorContext context) {
		return value;
	}
}
