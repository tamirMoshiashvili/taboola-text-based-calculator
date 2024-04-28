package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class PostDecrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		int postDecrementedValue = context.get(variableName);
		context.put(variableName, postDecrementedValue - 1);

		return postDecrementedValue;
	}
}
