package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
@EqualsAndHashCode
public class PreDecrementAstNode implements AbstractSyntaxTreeNode<Integer> {

	private final String variableName;

	@Override
	public Integer interpret(CalculatorContext context) {
		int preDecrementedValue = context.get(variableName) - 1;
		context.put(variableName, preDecrementedValue);

		return preDecrementedValue;
	}
}
