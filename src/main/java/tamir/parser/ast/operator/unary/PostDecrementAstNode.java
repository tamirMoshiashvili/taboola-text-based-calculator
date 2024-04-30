package tamir.parser.ast.operator.unary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
@EqualsAndHashCode
public class PostDecrementAstNode implements AbstractSyntaxTreeNode<Integer> {

	private final String variableName;

	@Override
	public Integer interpret(CalculatorContext context) {
		int postDecrementedValue = context.get(variableName);
		context.put(variableName, postDecrementedValue - 1);

		return postDecrementedValue;
	}
}
