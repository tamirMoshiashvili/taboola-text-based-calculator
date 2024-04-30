package tamir.parser.ast.operator.unary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
@EqualsAndHashCode
public class PreIncrementAstNode implements AbstractSyntaxTreeNode<Integer> {

	private final String variableName;

	@Override
	public Integer interpret(CalculatorContext context) {
		int preIncrementedValue = context.get(variableName) + 1;
		context.put(variableName, preIncrementedValue);

		return preIncrementedValue;
	}
}
