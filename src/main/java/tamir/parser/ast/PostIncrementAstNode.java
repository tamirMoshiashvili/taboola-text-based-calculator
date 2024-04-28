package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class PostIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		int postIncrementedValue = context.get(variableName);
		context.put(variableName, postIncrementedValue + 1);

		return postIncrementedValue;
	}
}
