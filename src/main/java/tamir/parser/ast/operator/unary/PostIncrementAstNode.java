package tamir.parser.ast.operator.unary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
@EqualsAndHashCode
public class PostIncrementAstNode implements AbstractSyntaxTreeNode<Integer> {

	private final String variableName;

	@Override
	public Integer interpret(CalculatorContext context) {
		int postIncrementedValue = context.get(variableName);
		context.put(variableName, postIncrementedValue + 1);

		return postIncrementedValue;
	}
}
