package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class PreDecrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		int preDecrementedValue = context.get(variableName) - 1;
		context.put(variableName, preDecrementedValue);

		return preDecrementedValue;
	}
}
