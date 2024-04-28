package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
@EqualsAndHashCode
public class PreIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		int preIncrementedValue = context.get(variableName) + 1;
		context.put(variableName, preIncrementedValue);

		return preIncrementedValue;
	}
}
