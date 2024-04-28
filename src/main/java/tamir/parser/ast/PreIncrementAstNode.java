package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class PreIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		int preIncrementedValue = context.get(variableName) + 1;
		context.put(variableName, preIncrementedValue);

		return preIncrementedValue;
	}
}
