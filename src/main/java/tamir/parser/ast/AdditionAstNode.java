package tamir.parser.ast;

import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@EqualsAndHashCode(callSuper = true)
public class AdditionAstNode extends BinaryOperatorAstNode {

	public AdditionAstNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right) {
		super(left, right);
	}

	@Override
	public int interpret(CalculatorContext context) {
		return left.interpret(context) + right.interpret(context);
	}
}
